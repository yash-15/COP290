package spycoders.moodleplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONObject;

public class HomeScreen_act extends AppCompatActivity {

    Button btn_notifications,btn_myCourses,btn_allGrades,btn_myProfile,btn_password
            ,btn_logout,btn_help;

    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        String url="http://192.168.254.1:8000/default/notifications.json";
        btn_notifications=(Button)findViewById(R.id.btnNotifications);
        btn_myCourses=(Button) findViewById(R.id.btnMyCourses);
        btn_allGrades=(Button) findViewById(R.id.btnAllGrades);
        btn_myProfile=(Button)findViewById(R.id.btnMyProfile);
        btn_password=(Button)findViewById(R.id.btnPassword);
        btn_logout=(Button)findViewById(R.id.btnLogout);
        btn_help=(Button) findViewById(R.id.btnHelp);

        btn_notifications.setOnClickListener(onBut);
        btn_myCourses.setOnClickListener(onBut);
        btn_allGrades.setOnClickListener(onBut);
        btn_myProfile.setOnClickListener(onBut);
        btn_password.setOnClickListener(onBut);
        btn_logout.setOnClickListener(onBut);
        btn_help.setOnClickListener(onBut);

        toast=Toast.makeText(HomeScreen_act.this,"Moodle Home Page",Toast.LENGTH_SHORT);
        toast.show();
    }


    private View.OnClickListener onBut = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(HomeScreen_act.this,HomeScreen_act.class);
            boolean change=true;
            switch(v.getId())
            {

                case R.id.btnNotifications:
                    intent= new Intent(HomeScreen_act.this,Notifications_act.class);
                    break;
                case R.id.btnMyCourses:
                    intent= new Intent(HomeScreen_act.this,MyCourses_act.class);
                    break;
                case R.id.btnAllGrades:
                    intent= new Intent(HomeScreen_act.this,AllGrades_act.class);
                    break;
                case R.id.btnLogout:
                    change=false;
                    String api="http://192.168.254.1:8000/default/logout.json";

                    JsonObjectRequest jsObjRequest_logout = new JsonObjectRequest
                            (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println(response.toString());
                                    toast.setText("Logged Out Successfully!!");
                                    toast.show();
                                    /*
                                    *There are difficulties in using the above defined intent
                                    *Why I have written it in so inefficient way?
                                    *2 reasons:
                                    * We can't change a variable(here intent) that is not final within a class
                                    * I can't make intent final because it's value needs to be changed depending on the context
                                    * Secondly, On adding to a queue, Surprisingly the lines of code following it seem
                                    * to be executed prior to the execution of the request
                                    * May be this is a feature of volley
                                    */
                                    Intent intent_logout=new Intent(HomeScreen_act.this,Login_act.class);
                                    finish();
                                    startActivity(intent_logout);
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO Auto-generated method stub
                                    toast.setText("Can't logout: Check your network connection!");
                                    toast.show();
                                }
                            });
                    Login_act.queue.add(jsObjRequest_logout);
                    break;
                default:change=false;
            }
            if (change)
            {
                finish();
                startActivity(intent);
            }
        }
    };

}
