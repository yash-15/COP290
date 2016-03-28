package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Mode_Choose_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode__choose_layout);

        Button btnNormal=(Button)findViewById(R.id.btnNormal);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Login_activity.logged_mode= Login_activity.mode.normal;
                Toast toast;
                toast = Toast.makeText(Mode_Choose_activity.this, "Signing in as: Normal User"
                        , Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(Mode_Choose_activity.this, content.class);
                startActivity(intent);
            }
        });
        String url1="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                "/admins/list.json";
        JsonObjectRequest jsObjRequest1 = new JsonObjectRequest
                (Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        create_list1(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });




        String url2="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+
                "/solvers/list.json";
        JsonObjectRequest jsObjRequest2 = new JsonObjectRequest
                (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        create_list2(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Add the request to the RequestQueue.
        Login_activity.queue.add(jsObjRequest1);
        Login_activity.queue.add(jsObjRequest2);

    }
    public void create_list1(JSONObject response)
    {
        JSONArray j_array=response.optJSONArray("posts");
        int l=j_array.length();
        final LinearLayout ll= (LinearLayout) findViewById(R.id.ll_mode_admin_choose);
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<l;i++)
        {
            JSONObject j_obj=j_array.optJSONObject(i);
            final Admin temp=new Admin();
            temp.id=j_obj.optInt("id");
            temp.locality=j_obj.optInt("locality");
            temp.cor_user=j_obj.optInt("cor_user");
            temp.parent=j_obj.isNull("parent")?-1:j_obj.optInt("parent");
            temp.post=j_obj.optString("post");
            temp.description=j_obj.optString("description");
            temp.isLeaf=j_obj.optBoolean("isLeaf");
            Button btnAdmin=new Button(Mode_Choose_activity.this);
            btnAdmin.setText(temp.post);
            btnAdmin.setBackgroundResource(R.drawable.btn);
            btnAdmin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Login_activity.logged_mode= Login_activity.mode.admin;
                    Toast toast;
                    toast = Toast.makeText(Mode_Choose_activity.this, "Signing in as: " +
                            temp.post, Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(Mode_Choose_activity.this, content.class);
                    Login_activity.logged_admin = temp;
                    startActivity(intent);
                }
            });
        ll.addView(btnAdmin, llp);
        }

    }
    public void create_list2(JSONObject response)
    {
        System.out.println(response.toString());
        JSONArray j_array=response.optJSONArray("posts");
        int l=j_array.length();
        final LinearLayout ll= (LinearLayout) findViewById(R.id.ll_mode_solver_choose);
        LinearLayout.LayoutParams llp =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<l;i++)
        {
            JSONObject j_obj=j_array.optJSONObject(i);
            final Solver temp=new Solver();
            temp.id=j_obj.optInt("id");
            temp.cor_user=j_obj.optInt("cor_user");
            temp.Description=j_obj.optString("Description");
            temp.Department=j_obj.optString("Department");
            Button btnSolver=new Button(Mode_Choose_activity.this);
            btnSolver.setText(temp.Department);
            btnSolver.setBackgroundResource(R.drawable.btn);
            btnSolver.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Login_activity.logged_mode= Login_activity.mode.solver;
                    Toast toast;
                    toast = Toast.makeText(Mode_Choose_activity.this, "Signing in as: " +
                            "Solver Head of " + temp.Department, Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(Mode_Choose_activity.this, content.class);
                    Login_activity.logged_solver = temp;
                    startActivity(intent);
                }
            });
            ll.addView(btnSolver, llp);
        }

    }
}

