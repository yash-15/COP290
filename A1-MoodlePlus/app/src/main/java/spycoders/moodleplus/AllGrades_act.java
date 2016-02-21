package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class AllGrades_act extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_grades);

        final LinearLayout ll=(LinearLayout) findViewById(R.id.ll_myGrades);

        String api="http://192.168.254.1:8000/default/grades.json";
        JsonObjectRequest jsObjRequest_allgrades = new JsonObjectRequest
                (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Queue<allgrades> allgrades1 = create_allgrades_queue(response);

                            while (allgrades1.num_elements > 0) {

                                AllGrades_layout agl = new AllGrades_layout(AllGrades_act.this, allgrades1.dequeue());
                                LinearLayout.LayoutParams llp =
                                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                ll.addView(agl.ll,llp);
                            }
                        }catch(Exception e){}//System.out.println("Exception");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub



                    }
                });
        Login_act.queue.add(jsObjRequest_allgrades);

    }

    public Queue<allgrades> create_allgrades_queue(JSONObject data)
    {
        Queue<allgrades> ans = new Queue<allgrades>();
        try {
            JSONArray j_array1 = data.getJSONArray("courses");
            JSONArray j_array2=data.getJSONArray("grades");
            int l = j_array1.length();
            for (int i = 0; i < l; i++)
            {
                allgrades temp = new allgrades();
                temp.sl=i+1;
                temp.courseCode=j_array1.getJSONObject(i).getString("code");
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
