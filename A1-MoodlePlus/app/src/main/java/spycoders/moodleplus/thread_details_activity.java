package spycoders.moodleplus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;

public class thread_details_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_details);

        LinearLayout ll1=(LinearLayout) findViewById(R.id.ll_thread_details);
        TextView thread_id,ID_of_poster,created_at,updated_at,title,descr;

        //Alternatively we can use arrays
        thread_id=new TextView(thread_details_activity.this);
        ID_of_poster=new TextView(thread_details_activity.this);
        created_at=new TextView(thread_details_activity.this);
        updated_at=new TextView(thread_details_activity.this);
        title=new TextView(thread_details_activity.this);
        descr=new TextView(thread_details_activity.this);

        thread_id.setText("Thread ID: "+Login_activity.current_thread.id);
        ID_of_poster.setText("ID of Poster: "+Login_activity.current_thread.user_id);
        created_at.setText("Created At: "+Login_activity.current_thread.created_at);
        updated_at.setText("Updated At: "+Login_activity.current_thread.updated_at);
        title.setText("Title: " + Login_activity.current_thread.title);
        descr.setText("Description: \n" + Login_activity.current_thread.description);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ll1.addView(thread_id,params);
        ll1.addView(ID_of_poster,params);
        ll1.addView(created_at,params);
        ll1.addView(title,params);
        ll1.addView(descr,params);

        final LinearLayout ll2=(LinearLayout) findViewById(R.id.ll_comment_details);
        String api="http://"+Login_activity.ip+":"+Login_activity.port+"/threads/thread.json/"+Login_activity.current_thread.id;
        JsonObjectRequest jsObjRequest_comments = new JsonObjectRequest
                (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            Queue<comment> comment1 = create_comment_queue(response);

                            LinearLayout.LayoutParams llp =
                                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                            while (comment1.num_elements > 0) {

                                //Queue Redundancy case to be solved later
                                final comment temp=comment1.dequeue();

                                Login_activity.current_thread.comments.insert(temp);

                                comment_layout cl = new comment_layout(thread_details_activity.this,
                                        temp);


                                ll2.addView(cl.ll, llp);

                            }
                            Button btnPost=new Button(thread_details_activity.this);
                            btnPost.setText("Add Comment");
                            btnPost.setTextColor(Color.parseColor("#FFFFFF"));
                            btnPost.setBackgroundColor(Color.parseColor("#616161"));

                            btnPost.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    //Help from
                                    //http://javatechig.com/android/android-input-dialog-example
                                    LayoutInflater inflater = LayoutInflater.from(thread_details_activity.this);
                                    View dialogView = inflater.inflate(R.layout.post_dialog_layout, null);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(thread_details_activity.this);
                                    builder.setView(dialogView);
                                    builder.setTitle("Post a comment to thread " + Login_activity.current_thread.id);

                                    final EditText txtComment = (EditText) dialogView.findViewById(R.id.txtPostDescription);
                                    final EditText txtTitle = (EditText) dialogView.findViewById(R.id.txtPostTitle);

                                    txtComment.setHint("Type your comment here..");
                                    txtTitle.setVisibility(View.INVISIBLE);
                                    builder.setPositiveButton("Post", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            try {
                                                String comment_text = URLEncoder.encode(txtComment.getText().toString(), "utf-8");
                                                String api = "http://"+Login_activity.ip+":"+Login_activity.port+"/threads/post_comment.json?" +
                                                        "thread_id=" + Login_activity.current_thread.id +
                                                        "&description=" + comment_text;
                                                //Source for above :
                                                // http://stackoverflow.com/questions/573184/java-convert-string-to-valid-uri-object

                                                JsonObjectRequest jsObjRequest_comment = new JsonObjectRequest
                                                        (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                                                            @Override
                                                            public void onResponse(JSONObject response) {
                                                                Toast toast = Toast.makeText(getApplicationContext(),
                                                                        "Comment Added!", Toast.LENGTH_SHORT);
                                                                toast.show();
                                                                Intent intent = new Intent(getBaseContext(), thread_details_activity.class);
                                                                finish();
                                                                startActivity(intent);
                                                            }
                                                        }, new Response.ErrorListener() {

                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                // TODO Auto-generated method stub

                                                                Toast toast = Toast.makeText(getApplicationContext(),
                                                                        "Network Error!", Toast.LENGTH_SHORT);
                                                                toast.show();

                                                            }
                                                        });
                                                Login_activity.queue.add(jsObjRequest_comment);
                                            }catch(Exception e){System.out.println("Failed");}

                                        }

                                    })
                                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            });

                                    final AlertDialog input_dialog = builder.create();
                                    input_dialog.show();

                                    input_dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                                    txtComment.addTextChangedListener(new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

                                        }

                                        @Override
                                        public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {

                                            if (txtComment.getText().toString().length() == 0) {
                                                input_dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                            } else {
                                                input_dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                            }
                                        }

                                        @Override
                                        public void afterTextChanged(final Editable s) {

                                        }
                                    });

                                }
                            });
                            
                            llp.gravity= Gravity.LEFT;
                            ll2.addView(btnPost,llp);
                        }catch(Exception e){}
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub



                    }
                });
        Login_activity.queue.add(jsObjRequest_comments);


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
