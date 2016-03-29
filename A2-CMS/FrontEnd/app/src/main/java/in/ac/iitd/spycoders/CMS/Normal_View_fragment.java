package in.ac.iitd.spycoders.CMS;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

/**
 * Created by Prabhu on 3/28/2016.
 */
public class Normal_View_fragment extends Fragment {
    static TextView tv;
    static View v;
    static LinearLayout ll;
    static String s;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //v = inflater.inflate(R.layout.view_ind_comp_layout, container, false);
//TODO: Much repetitions are there, can be shortened to just one.
        s = this.getTag();

        if(s.equals("Individual")) {
            v = inflater.inflate(R.layout.list_comp_layout, container, false);
            ll=(LinearLayout) v.findViewById(R.id.ll_list_comp);
            String api_ind="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                    "/complaints/normview.json/ind";

            JsonObjectRequest jsObjRequest_ind = new JsonObjectRequest
                    (Request.Method.GET, api_ind, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());

                            create_list1(response);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Login_activity.queue.add(jsObjRequest_ind);
        }

        else if (s.equals("Locality") || (s.equals("Institute"))) {
            v = inflater.inflate(R.layout.list_comp_layout, container, false);
            ll=(LinearLayout) v.findViewById(R.id.ll_list_comp);
            String api_grp="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                    "/complaints/normview.json/"+(s.equals("Locality")?"hstl":"insti");

            JsonObjectRequest jsObjRequest_grp = new JsonObjectRequest
                    (Request.Method.GET, api_grp, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());

                            create_list2(response);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Login_activity.queue.add(jsObjRequest_grp);
        }
        else if (s.equals("Approved"))
        {
            v = inflater.inflate(R.layout.list_comp_layout, container, false);
            ll=(LinearLayout) v.findViewById(R.id.ll_list_comp);
            String api_app="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                    "/complaints/adminview.json/"+ Login_activity.logged_admin.id+"/admin_ind";

            JsonObjectRequest jsObjRequest_app = new JsonObjectRequest
                    (Request.Method.GET, api_app, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());

                            create_list1(response);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Login_activity.queue.add(jsObjRequest_app);

        }
        else if (s.equals("Pending") ||s.equals("Unread"))
        {
            v = inflater.inflate(R.layout.list_comp_layout, container, false);
            ll=(LinearLayout) v.findViewById(R.id.ll_list_comp);
            String api_pend="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                    "/complaints/adminview.json/"+ Login_activity.logged_admin.id+"/pending";

            JsonObjectRequest jsObjRequest_pend = new JsonObjectRequest
                    (Request.Method.GET, api_pend, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());

                            create_list2(response);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Login_activity.queue.add(jsObjRequest_pend);

        }
        else if (s.equals("Logs"))
        {
            v = inflater.inflate(R.layout.list_comp_layout, container, false);
            ll=(LinearLayout) v.findViewById(R.id.ll_list_comp);
            String api_logs="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                    "/complaints/adminview.json/"+ Login_activity.logged_admin.id+"/logs";

            JsonObjectRequest jsObjRequest_logs = new JsonObjectRequest
                    (Request.Method.GET, api_logs, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());

                            create_list3(response);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Login_activity.queue.add(jsObjRequest_logs);
        }
        else if (s.equals("S_Individual")||s.equals("Group")||s.equals("Resolved"))
        {
            v = inflater.inflate(R.layout.list_comp_layout, container, false);
            ll=(LinearLayout) v.findViewById(R.id.ll_list_comp);
            String api_solver="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                    "/complaints/solverview.json/"+ Login_activity.logged_solver.id;

            JsonObjectRequest jsObjRequest_pend = new JsonObjectRequest
                    (Request.Method.GET, api_solver, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                        create_list1(response);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Toast toast=Toast.makeText(getActivity().getApplicationContext(),"Network Error!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
            Login_activity.queue.add(jsObjRequest_pend);
        }
        else {
            v = inflater.inflate(R.layout.view_ind_comp_layout, container, false);
        }

        return v;
    }
    public void create_list1(JSONObject response)
    {

        JSONArray j_array=response.optJSONArray("complaints");
        int l=j_array.length();
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<l;i++)
        {
            JSONObject j_obj=j_array.optJSONObject(i);
            if (Login_activity.logged_mode==Login_activity.mode.solver &&
                    (
                            (s.equals("S_Individual")&& (!(j_obj.isNull("Admin_ID"))||
                                    (j_obj.optInt("Status")==5)))||
                    (s.equals("Group")&&(j_obj.isNull("Admin_ID")||
                            (j_obj.optInt("Status")==5)))||
                                    ((s.equals("Resolved"))&&(j_obj.optInt("Status")!=5))
                    )
                    ){

            }
            else{
                final ind_comp temp = new ind_comp();
                temp.id = j_obj.optInt("id");
                temp.Title = j_obj.optString("Title");
                temp.Reg_Date = j_obj.optString("Reg_Date");
                temp.Status = j_obj.optInt("Status");
                ind_comp_layout temp_layout = new ind_comp_layout(getActivity().getBaseContext(), temp);
                ll.addView(temp_layout.ll, llp);
            }
        }

    }

    public void create_list2(JSONObject response) {

        JSONArray j_array = response.optJSONArray("complaints");
        int l = j_array.length();
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < l; i++) {
            JSONObject j_obj = j_array.optJSONObject(i);
            if ((Login_activity.logged_mode == Login_activity.mode.admin) &&
                    ((j_obj.optInt("Status") == 2 && s.equals("Pending")) ||
                            (j_obj.optInt("Status") != 2 && s.equals("Unread")))) {

            } else {
                final grp_comp temp = new grp_comp();
                temp.id = j_obj.optInt("id");
                temp.Title = j_obj.optString("Title");
                temp.Reg_Date = j_obj.optString("Reg_Date");
                temp.Cur_Admin_ID = j_obj.optInt("Cur_Admin_ID");
                temp.Status = j_obj.optInt("Status");
                grp_comp_layout temp_layout = new grp_comp_layout(getActivity().getBaseContext(), temp, j_obj.optInt("loality") == 17);
                ll.addView(temp_layout.ll, llp);
            }
        }
    }
    public void create_list3(JSONObject response)
    {

        JSONArray j_array=response.optJSONArray("complaints");
        int l=j_array.length();
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<l;i++)
        {
            JSONObject j_obj=j_array.optJSONObject(i);
            final log temp = new log();
            temp.Complaint_ID = j_obj.optInt("Complaint_ID");
            temp.action_taken = j_obj.optString("action_taken");
            temp.mod_date = j_obj.optString("mod_date");
            temp.Admin1 = j_obj.optInt("Admin1");
            temp.Admin2 = j_obj.isNull("Admin2")?-1:j_obj.optInt("Admin2");
            log_layout temp_layout = new log_layout(getActivity().getBaseContext(), temp);
            ll.addView(temp_layout.ll, llp);

        }

    }



}
