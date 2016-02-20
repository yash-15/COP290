package spycoders.moodleplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Notifications extends AppCompatActivity {

    TextView tv;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        tv=(TextView) findViewById(R.id.textView);
        button=(Button) findViewById(R.id.button);
        button.setOnClickListener(but);


    }

    private View.OnClickListener but =new View.OnClickListener(){

        public void onClick(View v)
        {
            String url="http://192.168.254.1:8000/default/notifications.json";
            JsonObjectRequest jsObjRequest_not = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            tv.setText(response.toString());
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            tv.setText("Network Error!");


                        }
                    });
            LoginActivity.queue.add(jsObjRequest_not);

        }
    };
}
