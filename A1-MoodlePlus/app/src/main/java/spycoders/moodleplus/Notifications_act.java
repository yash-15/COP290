package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class Notifications_act extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        final LinearLayout ll=(LinearLayout) findViewById(R.id.ll_notif);

        String api = "http://192.168.254.1:8000/default/notifications.json";
        JsonObjectRequest jsObjRequest_not = new JsonObjectRequest
                (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Queue<notif> notifs = create_Notif_Queue(response);
                            while (notifs.num_elements > 0) {

                                Notifications_layout nf = new Notifications_layout(Notifications_act.this, notifs.dequeue());
                                LinearLayout.LayoutParams llp =
                                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                ll.addView(nf.ll,llp);
                            }
                        }catch(Exception e){}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub



                    }
                });
        Login_act.queue.add(jsObjRequest_not);


    }


   public Queue<notif> create_Notif_Queue(JSONObject data) {
        Queue<notif> ans = new Queue<notif>();
        try {
            JSONArray j_array = data.getJSONArray("notifications");
            int l = j_array.length();
            for (int i = 0; i < l; i++)
            {
                JSONObject t_json = j_array.getJSONObject(i);
                notif temp = new notif();
                temp.seen = (t_json.getInt("is_seen") == 1);
                temp.time_created = t_json.getString("created_at");
                String descr = t_json.getString("description");
                boolean stop = false;
                int count = 1, start_i = 0, stop_i = 0;

                while (!stop) {
                    if (descr.charAt(count) == '>') {
                        start_i = count + 1;
                    } else if (descr.charAt(count) == '<') {
                        stop_i = count;
                        stop = true;
                    }
                    count++;
                }
                temp.postedBy = descr.substring(start_i, stop_i);
                //assuming all course codes are of length 6
                //and the descr always ends in the format coursecode</a>.
                temp.CourseCode = descr.substring(descr.length() - 11, descr.length() - 5);
                //System.out.println("enqueueing");
                ans.enqueue(temp);

            }
        } catch (Exception e) {
        }


        return ans;
    }
}