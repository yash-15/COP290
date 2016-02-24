package spycoders.moodleplus;

/**
 * Created by Prabhu on 2/23/2016.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FragmentTab extends Fragment {
    static TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        String s = this.getTag();

        if(s.equals("About")) {
            v = inflater.inflate(R.layout.about_layout, container, false);
            TextView tv=(TextView) v.findViewById(R.id.txtViewAbout12);
            tv.setText(Login_activity.current_course.code);

            tv=(TextView) v.findViewById(R.id.txtViewAbout22);
            tv.setText(Login_activity.current_course.name);

            tv=(TextView) v.findViewById(R.id.txtViewAbout32);
            tv.setText(Login_activity.current_course.ltp);

            tv=(TextView) v.findViewById(R.id.txtViewAbout42);
            tv.setText(String.valueOf(Login_activity.current_course.credits));

            tv=(TextView) v.findViewById(R.id.txtViewAbout61);
            tv.setText(Login_activity.current_course.descr);
        }

        else if (s.equals("Assignments")) {
            v = inflater.inflate(R.layout.assignments_layout, container, false);
            final LinearLayout ll=(LinearLayout) v.findViewById(R.id.ll_ass);
            String api_ass="http://"+Login_activity.ip+":"+Login_activity.port+"/courses/course.json/"+Login_activity.current_course.code+"/assignments";

            JsonObjectRequest jsObjRequest_ass = new JsonObjectRequest
                    (Request.Method.GET, api_ass, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                            Queue<assignment> ass_queue=create_ass_queue(response);
                            try{
                                while(ass_queue.num_elements>0){

                                    Ass_layout al= new Ass_layout(getActivity().getApplicationContext(),ass_queue.dequeue());
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                                            (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                                    ll.addView(al.ll,lp);
                                }
                            }
                            catch(Exception e){}

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Login_activity.queue.add(jsObjRequest_ass);
        } else if (s.equals("Grades")) {
            v = inflater.inflate(R.layout.grades_layout, container, false);
            //We can use the similar thing as of allGrades; only change in api
            final LinearLayout ll=(LinearLayout) v.findViewById(R.id.ll_grades);

            String api="http://"+Login_activity.ip+":"+Login_activity.port+"/courses/course.json/"+Login_activity.current_course.code+"/grades";
            JsonObjectRequest jsObjRequest_grades = new JsonObjectRequest
                    (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            AllGrades_activity aga=new AllGrades_activity();
                            try {
                                Queue<allgrades> grades1 = aga.create_allgrades_queue(response,false,Login_activity.current_course.code);
                                while (grades1.num_elements > 0) {

                                    AllGrades_layout gl = new AllGrades_layout(getActivity().getApplicationContext(),
                                            grades1.dequeue());
                                    LinearLayout.LayoutParams llp =
                                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                    ll.addView(gl.ll,llp);
                                }
                            }catch(Exception e){}
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub



                        }
                    });
            Login_activity.queue.add(jsObjRequest_grades);

        } else {
            v = inflater.inflate(R.layout.threads_layout, container, false);

            final LinearLayout ll=(LinearLayout) v.findViewById(R.id.ll_threads);

            String api="http://"+Login_activity.ip+":"+Login_activity.port+"/courses/course.json/"+Login_activity.current_course.code+"/threads";
            JsonObjectRequest jsObjRequest_threads = new JsonObjectRequest
                    (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {


                            try {

                                Queue<thread> thread1 = create_thread_queue(response);
                                LinearLayout.LayoutParams llp =
                                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                                while (thread1.num_elements > 0) {

                                    //Queue Redundancy case to be solved later
                                    final thread temp=thread1.dequeue();

                                    Login_activity.threadList.insert(temp);

                                    thread_layout tl = new thread_layout(getActivity().getApplicationContext(),
                                            temp);


                                    ll.addView(tl.ll, llp);

                                }
                                Button btnPostThread =new Button(getActivity().getApplicationContext());
                                btnPostThread.setText("Post New Thread ...");
                                btnPostThread.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        LayoutInflater inflater = LayoutInflater.from(getActivity());
                                        View dialogView = inflater.inflate(R.layout.post_dialog_layout, null);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setView(dialogView);
                                        builder.setTitle("Post a new thread to " + Login_activity.current_course.code);

                                        final EditText txtDescr = (EditText) dialogView.findViewById(R.id.txtPostDescription);
                                        final EditText txtTitle = (EditText) dialogView.findViewById(R.id.txtPostTitle);

                                        txtTitle.setHint("TITLE");
                                        txtDescr.setHint("DESCRIPTION");
                                        txtTitle.setVisibility(View.VISIBLE);
                                        builder.setPositiveButton("Post thread", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                try {
                                                    String title_text = URLEncoder.encode(txtTitle.getText().toString(), "utf-8");
                                                    String descr_text = URLEncoder.encode(txtDescr.getText().toString(), "utf-8");
                                                    String api = "http://" + Login_activity.ip + ":"+Login_activity.port+"/threads/new.json?" +
                                                            "title=" + title_text +
                                                            "&description=" + descr_text +
                                                            "&course_code=" + Login_activity.current_course.code;

                                                    //Source for above
                                                    // http://stackoverflow.com/questions/573184/java-convert-string-to-valid-uri-object

                                                    JsonObjectRequest jsObjRequest_postThread = new JsonObjectRequest
                                                            (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                                                                @Override
                                                                public void onResponse(JSONObject response) {
                                                                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                                                            "Thread Added!", Toast.LENGTH_SHORT);
                                                                    toast.show();
                                                                    Intent intent = new Intent(getActivity().getBaseContext(), Course_Page_activity.class);
                                                                    intent.putExtra("THREADS","true");
                                                                    //Code to be added to select threads tab
                                                                    getActivity().finish();
                                                                    startActivity(intent);
                                                                }
                                                            }, new Response.ErrorListener() {

                                                                @Override
                                                                public void onErrorResponse(VolleyError error) {
                                                                    // TODO Auto-generated method stub

                                                                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                                                            "Network Error!", Toast.LENGTH_SHORT);
                                                                    toast.show();

                                                                }
                                                            });
                                                    Login_activity.queue.add(jsObjRequest_postThread);
                                                } catch (Exception e) {
                                                    System.out.println("Failed");
                                                }

                                            }

                                        })
                                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                        final AlertDialog input_dialog = builder.create();
                                        input_dialog.show();

                                        txtTitle.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

                                            }

                                            @Override
                                            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                                                if (txtTitle.getText().toString().length() == 0 ||
                                                        txtDescr.getText().toString().length() == 0) {
                                                    input_dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                                                } else {
                                                    input_dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                                                }
                                            }

                                            @Override
                                            public void afterTextChanged(final Editable s) {

                                            }
                                        });
                                        txtDescr.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {

                                            }

                                            @Override
                                            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                                                if (txtTitle.getText().toString().length() == 0 ||
                                                        txtDescr.getText().toString().length() == 0) {
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
                                llp =
                                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                llp.gravity= Gravity.RIGHT;
                                ll.addView(btnPostThread,llp);
                            }catch(Exception e){}
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub



                        }
                    });
            Login_activity.queue.add(jsObjRequest_threads);

        }

        return v;
    }

    public Queue<assignment> create_ass_queue(JSONObject data)
    {
        Queue<assignment> ans=new Queue<assignment>();
        JSONArray t_json_array = data.optJSONArray("assignments");
        for(int i=0;i<t_json_array.length();i++)
        {
            JSONObject t_json=t_json_array.optJSONObject(i);
            assignment temp=new assignment();
            temp.late_all=t_json.optInt("late_days_allowed");
            temp.deadline=t_json.optString("deadline");
            temp.time=t_json.optString("created_at");
            temp.course_id=t_json.optInt("registered_course_id");
            temp.id=t_json.optInt("id");
            temp.name=t_json.optString("name");
            temp.type=t_json.optInt("type_");
            temp.descr=t_json.optString("description");
            ans.enqueue(temp);
        }
        return ans;
    }

    public Queue<thread> create_thread_queue(JSONObject data)
    {
        Queue<thread> ans=new Queue<thread>();
        JSONArray t_json_array = data.optJSONArray("course_threads");
        if (Course_Page_activity.setCourseData)
        {
            JSONObject t_course=data.optJSONObject("course");
            Login_activity.current_course.code=t_course.optString("code");
            Login_activity.current_course.name=t_course.optString("name");
            Login_activity.current_course.descr=t_course.optString("description");
            Login_activity.current_course.credits=t_course.optInt("credits");
            Login_activity.current_course.ltp=t_course.optString("l_t_p");
            Course_Page_activity.setCourseData=false;
        }
        for(int i=0;i<t_json_array.length();i++)
        {
            JSONObject t_json=t_json_array.optJSONObject(i);
            thread temp=new thread();

            temp.id=t_json.optInt("id");
            temp.created_at=t_json.optString("created_at");
            temp.updated_at=t_json.optString("updated_at");
            temp.title=t_json.optString("title");
            temp.description=t_json.optString("description");
            temp.user_id=t_json.optInt("user_id");
            temp.course_id=t_json.optInt("registered_course_id");

            ans.enqueue(temp);
        }
        return ans;
    }
}
