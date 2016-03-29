package in.ac.iitd.spycoders.CMS;

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
            final ind_comp temp=new ind_comp();
            temp.id=j_obj.optInt("id");
            temp.Title=j_obj.optString("Title");
            temp.Reg_Date=j_obj.optString("Reg_Date");
            ind_comp_layout temp_layout=new ind_comp_layout(getActivity().getBaseContext(),temp);
            ll.addView(temp_layout.ll, llp);
        }

    }

    public void create_list2(JSONObject response)
    {

        JSONArray j_array=response.optJSONArray("complaints");
        int l=j_array.length();
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<l;i++)
        {
            JSONObject j_obj=j_array.optJSONObject(i);
            final grp_comp temp=new grp_comp();
            temp.id=j_obj.optInt("id");
            temp.Title=j_obj.optString("Title");
            temp.Reg_Date=j_obj.optString("Reg_Date");
            temp.Cur_Admin_ID=j_obj.optInt("Cur_Admin_ID");
            grp_comp_layout temp_layout=new grp_comp_layout(getActivity().getBaseContext(),temp,s.equals("Institute"));
            ll.addView(temp_layout.ll, llp);
        }

    }

}
