package spycoders.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;

public class Login_act extends AppCompatActivity {

    Button btn_Login,btn_Reset,btn_FP;
    EditText txt_UserName,txt_Pswd;

    Toast toast;
    public static RequestQueue queue;
    String url;

    CookieManager manager;
    boolean login_success=false;

    static User logged_user;

    static L_List_course courseList;

    static course current_course;

    static L_List_thread threadList;

    static thread current_thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        queue= Volley.newRequestQueue(this);

        txt_UserName =(EditText) findViewById(R.id.txtUserName);
        txt_Pswd = (EditText) findViewById(R.id.txtPswd);

        btn_Login=(Button) findViewById(R.id.btnLogin);
        btn_Login.setOnClickListener(onBut_Login);

        btn_Reset=(Button) findViewById(R.id.btnReset);
        btn_Reset.setOnClickListener(onBut_Reset);

        btn_FP=(Button) findViewById(R.id.btnFP);
        btn_FP.setOnClickListener(onBut_FP);

        logged_user=new User();   // As this is run on logout as well so we can
                                 // safely say that the data gets reset on logout
                                // Anyways it is set again on login

        courseList=new L_List_course();

        current_course=new course();

        threadList=new L_List_thread();

        current_thread=new thread();

        toast = Toast.makeText(getApplicationContext(), "Moodle Plus", Toast.LENGTH_SHORT);
        toast.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Optionally, you can just use the default CookieManager
        manager = new CookieManager();
        CookieHandler.setDefault(manager);


    }

private View.OnClickListener onBut_Login = new View.OnClickListener(){
        public void onClick(View v){
            url="http://192.168.254.1:8000/default/login.json?userid="
                    + txt_UserName.getText().toString()
                    + "&password="+txt_Pswd.getText().toString();
            // Request a string response from the provided URL.

            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            if (response.optBoolean("success"))
                            {
                                JSONObject response_user=response.optJSONObject("user");

                                //Set the logged user info

                                logged_user.firstName=response_user.optString("first_name");
                                logged_user.lastName=response_user.optString("last_name");
                                logged_user.entryNo=response_user.optString("entry_no");
                                logged_user.email=response_user.optString("email");
                                logged_user.username=response_user.optString("username");
                                logged_user.password=response_user.optString("password");
                                logged_user.User_ID=response_user.optInt("id");
                                logged_user.User_type=response_user.optInt("type_");

                                Intent intent= new Intent(Login_act.this,HomeScreen_act.class);
                                finish();
                                startActivity(intent);
                                toast.setText("Login Successful!");
                                toast.show();
                            }
                            else
                            {
                                toast.setText("Login Failed!");
                                toast.show();
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

            // Add the request to the RequestQueue.

            queue.add(jsObjRequest);



        }
    };

    private View.OnClickListener onBut_FP =new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener onBut_Reset=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };


    public void populate_course_list()
    {

    }

}
