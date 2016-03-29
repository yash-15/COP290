package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class View_Grp_Comp_activity extends AppCompatActivity {

    static boolean insti_level=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_grp_comp_layout);

        String insti="false";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                insti= "false";
            } else {
                insti= extras.getString("INSTI");
                if (insti.equals("true"))
                {

                    insti_level=true;
                }
            }
        }

        final TextView tv_grp_comp_id=(TextView) findViewById(R.id.tv_grp_comp_id);
        final TextView tv_grp_comp_type=(TextView) findViewById(R.id.tv_grp_comp_type);
        final TextView tv_grp_comp_locality=(TextView) findViewById(R.id.tv_grp_comp_locality);
        final TextView tv_grp_comp_user_id=(TextView) findViewById(R.id.tv_grp_comp_user_id);
        final TextView tv_grp_comp_reg_date=(TextView) findViewById(R.id.tv_grp_comp_reg_date);
        final TextView tv_grp_comp_title=(TextView) findViewById(R.id.tv_grp_comp_title);
        final TextView tv_grp_comp_descr=(TextView) findViewById(R.id.tv_grp_comp_descr);
        final TextView tv_grp_comp_init_admin=(TextView) findViewById(R.id.tv_grp_comp_init_admin);
        final TextView tv_grp_comp_cur_admin=(TextView) findViewById(R.id.tv_grp_comp_cur_admin);
        final TextView tv_grp_comp_status=(TextView) findViewById(R.id.tv_grp_comp_status);
        final TextView tv_grp_comp_resolve_date=(TextView) findViewById(R.id.tv_grp_comp_resolve_date);
        final TextView tv_grp_comp_poll_status=(TextView) findViewById(R.id.tv_grp_comp_poll_status);

        final Button btn_grp_comp_approve = (Button) findViewById(R.id.btn_grp_comp_approve);
        final Button btn_grp_comp_discard = (Button) findViewById(R.id.btn_grp_comp_discard);
        final Button btn_grp_comp_forward = (Button) findViewById(R.id.btn_grp_comp_forward);
        final Button btn_grp_comp_poll = (Button) findViewById(R.id.btn_grp_comp_poll);


        final Toast toast=Toast.makeText(this,"",Toast.LENGTH_SHORT);

        String api_grp="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                "/complaints/normview.json/"+(insti_level?"insti/":"hstl/")+Login_activity.current_grp.id;

        JsonObjectRequest jsObjRequest_grp = new JsonObjectRequest
                (Request.Method.GET, api_grp, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());

                        JSONObject response1=response.optJSONObject("complaints");
                        Login_activity.current_grp.id=response1.optInt("id");
                        Login_activity.current_grp.Status=response1.optInt("Status");
                        Login_activity.current_grp.User_ID=response1.optInt("User_ID");
                        Login_activity.current_grp.Description=response1.optString("Description");
                        Login_activity.current_grp.Title=response1.optString("Title");
                        Login_activity.current_grp.locality=response1.optInt("locality");
                        Login_activity.current_grp.Cur_Admin_ID=response1.optInt("Cur_Admin_ID");
                        Login_activity.current_grp.Poll_Results=response1.isNull("Poll_Results")?
                                -1:response1.optInt("Poll_results");
                        Login_activity.current_grp.Reg_Date=response1.optString("Reg_Date");
                        Login_activity.current_grp.Initial_Admin_ID=response1.optInt("Initial_Admin_ID");
                        Login_activity.current_grp.Approved_Discarded_Date=response1.isNull("Approved_Discarded_Date")?
                                "":response1.optString("Approved_Discarded_Date");

                        Login_activity.current_grp.Reg_Date=response1.optString("Reg_Date");



                        tv_grp_comp_id.setText(String.valueOf(Login_activity.current_grp.id));
                        String temp;
                        switch(Login_activity.current_grp.Status)
                        {
                            case 2:temp="Unread";break;
                            case 3:temp="Polls On";break;
                            case 4:temp="Discarded";break;
                            case 6:temp="Waiting Approval";break;
                            case 7:temp="Approved";break;
                            default:temp="";
                        }
                        tv_grp_comp_status.setText(temp);
                        tv_grp_comp_user_id.setText(String.valueOf(Login_activity.current_grp.User_ID));
                        tv_grp_comp_descr.setText(Login_activity.current_grp.Description);
                        tv_grp_comp_title.setText(Login_activity.current_grp.Title);
                        if (Login_activity.current_grp.locality==17)
                        {
                            tv_grp_comp_locality.setVisibility(View.INVISIBLE);
                            tv_grp_comp_type.setText("Institute");
                        }
                        else
                        {
                            tv_grp_comp_locality.setVisibility(View.VISIBLE);
                            tv_grp_comp_locality.setText(String.valueOf(Login_activity.current_grp.locality));
                            tv_grp_comp_type.setText("Local");
                        }
                        tv_grp_comp_cur_admin.setText(String.valueOf(Login_activity.current_grp.Cur_Admin_ID));
                        //Assuming a normal user has accessed
                        if (Login_activity.current_grp.Poll_Results==-1)
                        {
                            temp="No Polls";
                            btn_grp_comp_poll.setVisibility(View.INVISIBLE);
                        }
                        else if (Login_activity.current_grp.Status==3)
                        {
                            temp="Ongoing Polls";
                            btn_grp_comp_poll.setVisibility(View.VISIBLE);
                            btn_grp_comp_poll.setText("VOTE");
                        }
                        else
                        {
                            temp="Polls Over";
                            btn_grp_comp_poll.setVisibility(View.INVISIBLE);
                        }
                        tv_grp_comp_poll_status.setText(temp);
                        tv_grp_comp_reg_date.setText(Login_activity.current_grp.Reg_Date);
                        tv_grp_comp_init_admin.setText(String.valueOf(Login_activity.current_grp.Initial_Admin_ID));
                        tv_grp_comp_resolve_date.setText(Login_activity.current_grp.Approved_Discarded_Date);

                        //Assuming a normal user has accessed
                        btn_grp_comp_approve.setVisibility(View.INVISIBLE);
                        btn_grp_comp_discard.setVisibility(View.INVISIBLE);
                        btn_grp_comp_forward.setVisibility(View.INVISIBLE);

                        /*
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
*/



                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        toast.setText("Network Error!");
                        toast.show();
                    }
                });
        Login_activity.queue.add(jsObjRequest_grp);


    }
}
