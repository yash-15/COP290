package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class selectProfile extends AppCompatActivity {

    Toast toastObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        login.queue= Volley.newRequestQueue(this);

        Button individual = (Button) findViewById(R.id.profile_button_indivual);
        Button admin = (Button) findViewById(R.id.profile_button_admin);
        Button solver = (Button) findViewById(R.id.profile_button_solver);

        toastObj = new Toast(getApplicationContext());
        toastObj.setDuration(Toast.LENGTH_SHORT);

        setInitialVisibility();
                admin.setVisibility(View.VISIBLE);

        individual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), category_2.class);
                startActivity(intent);
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), category_2.class);
                startActivity(intent);


            }
        });
        solver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), category_2.class);
                startActivity(intent);


            }
        });

    }

    private void setInitialVisibility() {

        SharedPreferences loginInfo = getSharedPreferences("loginCredentials",MODE_PRIVATE);
        String ip=loginInfo.getString("IP","0.0.0.0");
        String port=loginInfo.getString("Port","8000");

        String url="http://"+ip+":"+port+"/admins/list.json";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject response_adminInfo=response.optJSONObject("post");


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        popToast("Network Error!");
                    }
                }

        );
        login.queue.add(jsObjRequest);
    }

    private void popToast(String message) {
        toastObj.setText(message);
        toastObj.show();
    }
}