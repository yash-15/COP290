package spycoders.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyCourses_activity extends Base_activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout base=(LinearLayout) findViewById(R.id.ll_base);
        View v=getLayoutInflater().inflate(R.layout.activity_my_courses, base, false);
        base.addView(v);

        final LinearLayout ll= (LinearLayout) findViewById(R.id.ll_myCourses);



        String api="http://"+Login_activity.ip+":"+Login_activity.port+"/courses/list.json";
        JsonObjectRequest jsObjRequest_myCourses = new JsonObjectRequest
                (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Queue<course> myCourses = create_course_Queue(response);
                            TextView txtView_header=new TextView(MyCourses_activity.this);
                            txtView_header.setTextSize(15);

                            txtView_header.setText("Showing Courses for "+(response.getInt("current_sem")==1?"1st":"2nd")
                                    + " semester, "+(response.getInt("current_year")));
                            LinearLayout.LayoutParams llp =
                                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            ll.addView(txtView_header,llp);
                            Login_activity.courseList.clear();
                            while (myCourses.num_elements > 0) {

                                Button btn_course=new Button(MyCourses_activity.this);
                                final course temp=myCourses.dequeue();
                                //The queue used is redundant and only the linked list could have sufficed.
                                //To be edited afterwards
                                Login_activity.courseList.insert(temp);
                                btn_course.setText(temp.code.toUpperCase() + ":" + temp.name);
                                btn_course.setBackgroundResource(R.drawable.btn);
                                btn_course.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Toast toast;
                                        toast = Toast.makeText(MyCourses_activity.this, "You clicked course: " +
                                                temp.code.substring(0, 6), Toast.LENGTH_SHORT);
                                        toast.show();
                                        Intent intent = new Intent(MyCourses_activity.this, Course_Page_activity.class);
                                        try {
                                            Login_activity.current_course = Login_activity.courseList.find(temp.code.substring(0, 6));
                                        } catch (Exception e) {
                                        }
                                        //intent.putExtra("course_code",temp.code.substring(0, 6));
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                                llp.setMargins(0,20,0,0);
                                ll.addView(btn_course,llp);
                            }
                        }catch(Exception e){}//System.out.println("Exception");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub



                    }
                });
        Login_activity.queue.add(jsObjRequest_myCourses);

    }
    public Queue<course> create_course_Queue(JSONObject data) {
        Queue<course> ans = new Queue<course>();
        try {
            JSONArray j_array = data.getJSONArray("courses");
            int l = j_array.length();
            for (int i = 0; i < l; i++)
            {
                JSONObject t_json = j_array.getJSONObject(i);
                course temp = new course();
                temp.code=t_json.getString("code");
                temp.name=t_json.getString("name");
                temp.descr=t_json.getString("description");
                temp.credits=t_json.getInt("credits");
                temp.ltp=t_json.getString("l_t_p");
                ans.enqueue(temp);

            }
        } catch (Exception e) {
        }


        return ans;
    }
}

