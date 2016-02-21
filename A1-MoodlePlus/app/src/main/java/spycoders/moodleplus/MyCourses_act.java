package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
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

import java.util.Vector;

public class MyCourses_act extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        final LinearLayout ll= (LinearLayout) findViewById(R.id.ll_myCourses);



        String api="http://192.168.254.1:8000/courses/list.json";
        JsonObjectRequest jsObjRequest_myCourses = new JsonObjectRequest
                (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Queue<course> myCourses = create_course_Queue(response);
                             TextView txtView_header=new TextView(MyCourses_act.this);
                            txtView_header.setText("Showing Courses for "+(response.getInt("current_sem")==1?"1st":"2nd")
                                    + " semester, "+(response.getInt("current_year")));
                            LinearLayout.LayoutParams llp =
                                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            ll.addView(txtView_header,llp);
                            while (myCourses.num_elements > 0) {

                                Button btn_course=new Button(MyCourses_act.this);
                                final course temp=myCourses.dequeue();
                                btn_course.setText(temp.code.toUpperCase() + ":" + temp.name);
                                btn_course.setOnClickListener(new View.OnClickListener(){
                                    public void onClick(View v){
                                        Toast toast;
                                        toast=Toast.makeText(MyCourses_act.this, "You clicked course: " +
                                                temp.code.substring(0, 6), Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });

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
        Login_act.queue.add(jsObjRequest_myCourses);

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
