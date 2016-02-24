package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class AllGrades_activity extends Base_activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout base=(LinearLayout) findViewById(R.id.ll_base);
        View v=getLayoutInflater().inflate(R.layout.activity_all_grades, base, false);
        base.addView(v);


        final LinearLayout ll=(LinearLayout) findViewById(R.id.ll_myGrades);

        String api="http://"+Login_activity.ip+":"+Login_activity.port+"/default/grades.json";
        JsonObjectRequest jsObjRequest_allgrades = new JsonObjectRequest
                (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            Queue<allgrades> allgrades1 = create_allgrades_queue(response,true,"");

                            while (allgrades1.num_elements > 0) {

                                AllGrades_layout agl = new AllGrades_layout(AllGrades_activity.this,allgrades1.dequeue());

                                LinearLayout.LayoutParams llp =
                                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                ll.addView(agl.ll, llp);

                            }
                        }catch(Exception e){}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub



                    }
                });
        Login_activity.queue.add(jsObjRequest_allgrades);

    }

    //all boolean says whether grades for a single course or not,
    //This is so that this function can be used in FragmentTab for grades of a particular course
    //In case only one course it is passed as course_code
    //For all Grades this can be passed as ""
    public Queue<allgrades> create_allgrades_queue(JSONObject data,boolean all,String course_code)
    {
        Queue<allgrades> ans = new Queue<allgrades>();
        try {
            JSONArray j_array1=new JSONArray();
            if (all)
            {j_array1= data.getJSONArray("courses");}
            JSONArray j_array2=data.getJSONArray("grades");
            int l = j_array2.length();
            for (int i = 0; i < l; i++)
            {
                allgrades temp = new allgrades();
                temp.sl=i+1;
                if(all)
                {temp.courseCode=j_array1.getJSONObject(i).getString("code");}
                else
                {
                    temp.courseCode=course_code;
                }
                JSONObject j_obj_temp=j_array2.getJSONObject(i);
                temp.descr=j_obj_temp.getString("name");
                temp.score=j_obj_temp.getInt("score");
                temp.outOf=j_obj_temp.getInt("out_of");
                temp.weight=j_obj_temp.getInt("weightage");
                ans.enqueue(temp);
            }
        } catch (Exception e) {}
        return ans;
    }
}
