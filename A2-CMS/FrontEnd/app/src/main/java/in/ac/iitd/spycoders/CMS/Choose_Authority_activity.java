package in.ac.iitd.spycoders.CMS;

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

public class Choose_Authority_activity extends Choose_Category_activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_authority_layout);
        String temp,api_temp;
        final Toast toast=Toast.makeText(this, "", Toast.LENGTH_SHORT);

        TextView tvPrompt = (TextView) findViewById(R.id.txtPrompt);
        switch(Login_activity.lodge_var1)
        {

            case 1:temp="Choose the leaf-level Administrator from your locality";
                    api_temp="/admins/local_addressable.json";
                                break;
            case 2:temp="Choose the leaf-level Institute Administrator";
                api_temp="/admins/insti_addressable.json";
                break;
            default:temp="Choose the Solving Department!";
                api_temp="/solvers/addressable.json";
        }
        tvPrompt.setText(temp);
        String api_grp="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                api_temp;

        JsonObjectRequest jsObjRequest_auth = new JsonObjectRequest
                (Request.Method.GET, api_grp, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        if (Login_activity.lodge_var1==0)
                        {create_list1(response);}
                        else
                        {
                            create_list2(response);
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        toast.setText("Network Error!");
                        toast.show();
                    }
                });
        Login_activity.queue.add(jsObjRequest_auth);

    }

    public void create_list1(JSONObject response) {
        JSONArray j_array = response.optJSONArray("solvers");
        int l = j_array.length();
        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll_choose_auth);
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < l; i++) {
            final JSONObject j_obj = j_array.optJSONObject(i);

            final Button btnSolver = new Button(Choose_Authority_activity.this);
            btnSolver.setText(j_obj.optString("Department"));
            btnSolver.setBackgroundResource(R.drawable.btn);
            final Toast toast=Toast.makeText(this, "You have chosen " + j_obj.optString("Department"), Toast.LENGTH_SHORT);
            btnSolver.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    toast.show();
                    if (Login_activity.logged_mode==Login_activity.mode.normal) {
                        Intent intent = new Intent(Choose_Authority_activity.this, Complaint_Form_activity.class);
                        Login_activity.lodge_var2 = j_obj.optInt("id");
                        startActivity(intent);
                    }
                    else
                    {
                        String api_approve="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                                "/complaints/approve.json/"+Login_activity.logged_admin.id+"/"+
                                Login_activity.current_grp.id+"/"+j_obj.optInt("id");

                        JsonObjectRequest jsObjRequest_approve = new JsonObjectRequest
                                (Request.Method.GET, api_approve, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                toast.setText("Complaint Approved!");
                                finish();
                                //Normal_View_activity.mTabHost.setCurrentTab(0);
                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO Auto-generated method stub
                                        toast.setText("Network Error!");
                                        toast.show();
                                    }
                                });
                        Login_activity.queue.add(jsObjRequest_approve);
                    }
                }
            });
            ll.addView(btnSolver, llp);
        }
    }

    public void create_list2(JSONObject response)
    {
        JSONArray j_array = response.optJSONArray("admins");
        int l = j_array.length();
        final LinearLayout ll = (LinearLayout) findViewById(R.id.ll_choose_auth);
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < l; i++) {
            final JSONObject j_obj = j_array.optJSONObject(i);

            final Button btnAdmin = new Button(Choose_Authority_activity.this);
            btnAdmin.setText(j_obj.optString("post"));
            btnAdmin.setBackgroundResource(R.drawable.btn);
            final Toast toast=Toast.makeText(this, "You have chosen " + j_obj.optString("post"), Toast.LENGTH_SHORT);
            btnAdmin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {


                    toast.show();
                    Intent intent = new Intent(Choose_Authority_activity.this, Complaint_Form_activity.class);
                    Login_activity.lodge_var2 = j_obj.optInt("id");
                    startActivity(intent);
                }
            });
            ll.addView(btnAdmin, llp);
        }
    }
}
