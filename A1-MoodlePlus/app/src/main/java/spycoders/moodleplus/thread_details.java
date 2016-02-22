package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class thread_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_details);

        LinearLayout ll1=(LinearLayout) findViewById(R.id.ll_thread_details);
        TextView thread_id,ID_of_poster,created_at,updated_at,title,descr;

        //Alternatively we can use arrays
        thread_id=new TextView(thread_details.this);
        ID_of_poster=new TextView(thread_details.this);
        created_at=new TextView(thread_details.this);
        updated_at=new TextView(thread_details.this);
        title=new TextView(thread_details.this);
        descr=new TextView(thread_details.this);

        thread_id.setText("Thread ID: "+Login_act.current_thread.id);
        ID_of_poster.setText("ID of Poster: "+Login_act.current_thread.user_id);
        created_at.setText("Created At: "+Login_act.current_thread.created_at);
        updated_at.setText("Updated At: "+Login_act.current_thread.updated_at);
        title.setText("Title: " + Login_act.current_thread.title);
        descr.setText("Description: \n" + Login_act.current_thread.description);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ll1.addView(thread_id,params);
        ll1.addView(ID_of_poster,params);
        ll1.addView(created_at,params);
        ll1.addView(title,params);
        ll1.addView(descr,params);

        final LinearLayout ll2=(LinearLayout) findViewById(R.id.ll_comment_details);
        String api="http://192.168.254.1:8000/threads/thread.json/"+Login_act.current_thread.id;
        JsonObjectRequest jsObjRequest_comments = new JsonObjectRequest
                (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            Queue<comment> comment1 = create_comment_queue(response);

                            System.out.println(response.toString());
                            System.out.println("Queue length: "+comment1.num_elements);
                            while (comment1.num_elements > 0) {

                                //Queue Redundancy case to be solved later
                                final comment temp=comment1.dequeue();

                                Login_act.current_thread.comments.insert(temp);

                                comment_layout cl = new comment_layout(thread_details.this,
                                        temp);

                                LinearLayout.LayoutParams llp =
                                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                ll2.addView(cl.ll, llp);

                            }
                        }catch(Exception e){}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub



                    }
                });
        Login_act.queue.add(jsObjRequest_comments);


    }

    public Queue<comment> create_comment_queue(JSONObject data)
    {
        Queue<comment> ans=new Queue<>();
        JSONArray t_json_array=data.optJSONArray("comments");
        for(int i=0;i<t_json_array.length();i++)
        {
            JSONObject t_json=t_json_array.optJSONObject(i);
            comment temp=new comment();
            temp.id=t_json.optInt("id");
            temp.thread_id=t_json.optInt("thread_id");
            temp.user_id=t_json.optInt("user_id");
            temp.created_at=t_json.optString("created_at");
            temp.description=t_json.optString("description");
            temp.commentator=data.optJSONArray("comment_users").optJSONObject(i).optString("first_name")
                            + data.optJSONArray("comment_users").optJSONObject(i).optString("last_name");
            ans.enqueue(temp);
        }
        return ans;
    }
}
