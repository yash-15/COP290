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

public class MainActivity extends AppCompatActivity {

    Button btn_Login,btn_Reset,btn_FP;
    EditText txt_UserName,txt_Pswd;

    Toast toast;
    public RequestQueue queue;
    String url;


    boolean login_success=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        queue= Volley.newRequestQueue(this);

        txt_UserName =(EditText) findViewById(R.id.txtUserName);
        txt_Pswd = (EditText) findViewById(R.id.txtPswd);

        btn_Login=(Button) findViewById(R.id.btnLogin);
        btn_Login.setOnClickListener(onBut_Login);

        btn_Reset=(Button) findViewById(R.id.btnReset);
        btn_Reset.setOnClickListener(onBut_Reset);

        btn_FP=(Button) findViewById(R.id.btnFP);
        btn_FP.setOnClickListener(onBut_FP);

        toast = Toast.makeText(getApplicationContext(), "Moodle Plus", Toast.LENGTH_SHORT);
        toast.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
                                Intent intent= new Intent(MainActivity.this,Courses.class);
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


}
