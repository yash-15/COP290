package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class View_Ind_Comp_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_ind_comp_layout);

        final TextView tv_ind_comp_id=(TextView) findViewById(R.id.tv_ind_comp_id);
        final TextView tv_ind_comp_user_id=(TextView) findViewById(R.id.tv_ind_comp_user_id);
        final TextView tv_ind_comp_reg_date=(TextView) findViewById(R.id.tv_ind_comp_reg_date);
        final TextView tv_ind_comp_title=(TextView) findViewById(R.id.tv_ind_comp_title);
        final TextView tv_ind_comp_descr=(TextView) findViewById(R.id.tv_ind_comp_descr);
        final TextView tv_ind_comp_solver=(TextView) findViewById(R.id.tv_ind_comp_solver);
        final TextView tv_ind_comp_status=(TextView) findViewById(R.id.tv_ind_comp_status);
        final TextView tv_ind_comp_resolve_date=(TextView) findViewById(R.id.tv_ind_comp_resolve_date);

        final Button  btn_ind_comp_resolve=(Button) findViewById(R.id.btn_ind_comp_resolve);

        final Toast toast=Toast.makeText(this,"",Toast.LENGTH_SHORT);

        String api_ind="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                "/complaints/normview.json/ind/"+Login_activity.current_ind.id;

        JsonObjectRequest jsObjRequest_ind = new JsonObjectRequest
                (Request.Method.GET, api_ind, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                        JSONObject response1=response.optJSONObject("complaints");
                        Login_activity.current_ind.id=response1.optInt("id");
                        Login_activity.current_ind.Status=response1.optInt("Status");
                        Login_activity.current_ind.User_ID=response1.optInt("User_ID");
                        Login_activity.current_ind.Description=response1.optString("Description");
                        Login_activity.current_ind.Title=response1.optString("Title");
                        Login_activity.current_ind.Resolved_Date=response1.isNull("Resolved_Date")?
                                "":response1.optString("Resolved_Date");
                        //Login_activity.current_ind.Admin_ID=response1.isNull("Admin_ID")?
                          //      -1:response1.optInt("AdminID");
                        Login_activity.current_ind.Reg_Date=response1.optString("Reg_Date");
                        //Login_activity.current_ind.Cor_Grp_Comp=response1.isNull("Cor_Grp_Comp")?
                          //      -1:response1.optInt("Cor_Grp_Comp");
                        Login_activity.current_ind.Solver_ID=response1.optInt("Solver_ID");


                        tv_ind_comp_id.setText(String.valueOf(Login_activity.current_ind.id));
                        tv_ind_comp_status.setText(Login_activity.current_ind.Status==5?"Resolved":"Unresolved");
                        tv_ind_comp_user_id.setText(String.valueOf(Login_activity.current_ind.User_ID));
                        tv_ind_comp_descr.setText(Login_activity.current_ind.Description);
                        tv_ind_comp_title.setText(Login_activity.current_ind.Title);
                        tv_ind_comp_resolve_date.setText(Login_activity.current_ind.Resolved_Date);
                        tv_ind_comp_reg_date.setText(Login_activity.current_ind.Reg_Date);
                        tv_ind_comp_solver.setText(String.valueOf(Login_activity.current_ind.Solver_ID));

                        btn_ind_comp_resolve.setVisibility(Login_activity.current_ind.Status == 5 ? View.INVISIBLE : View.VISIBLE);
                        btn_ind_comp_resolve.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String api_resolve="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                                        "/complaints/resolve_ind.json/"+Login_activity.current_ind.id;
                                JsonObjectRequest jsObjRequest_resolve = new JsonObjectRequest
                                        (Request.Method.GET, api_resolve, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                System.out.println(response.toString());
                                                toast.setText("Resolved Successfully!!");
                                                toast.show();
                                                Intent intent_refresh=new Intent(getBaseContext(),View_Ind_Comp_activity.class);
                                                finish();
                                                startActivity(intent_refresh);
                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // TODO Auto-generated method stub
                                                toast.setText("Network Error!");
                                                toast.show();
                                            }
                                        });
                                Login_activity.queue.add(jsObjRequest_resolve);

                            }
                        });




                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        toast.setText("Network Error!");
                        toast.show();
                    }
                });
        Login_activity.queue.add(jsObjRequest_ind);


    }
}
