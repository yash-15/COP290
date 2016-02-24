package spycoders.moodleplus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;



import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLEncoder;

public class Login_activity extends AppCompatActivity {

    Button btn_Login,btn_Clear_Data,btn_FP;
    EditText txt_UserName,txt_Pswd;

    Toast toast;
    public static RequestQueue queue;
    String url;

    static String ip,port;

    CookieManager manager;
    boolean login_success=false;

    static User logged_user;

    static L_List_course courseList;

    static course current_course;

    static L_List_thread threadList;

    static thread current_thread;

    SharedPreferences sharedpreferences;

    String stored_user,stored_password;

    CheckBox cBoxRemMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);

        queue= Volley.newRequestQueue(this);

        txt_UserName =(EditText) findViewById(R.id.txtUserName);
        txt_Pswd = (EditText) findViewById(R.id.txtPswd);

        btn_Login=(Button) findViewById(R.id.btnLogin);
        btn_Login.setOnClickListener(onBut_Login);

        btn_Clear_Data=(Button) findViewById(R.id.btnClear);
        btn_Clear_Data.setOnClickListener(onBut_Clear_Data);

        btn_FP=(Button) findViewById(R.id.btnFP);
        btn_FP.setOnClickListener(onBut_FP);

        cBoxRemMe=(CheckBox) findViewById(R.id.checkBoxRem_me);

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

        sharedpreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        stored_user=sharedpreferences.getString("UserName", "");
        stored_password = sharedpreferences.getString("Password", "");
        ip=sharedpreferences.getString("IP","0.0.0.0");
        port=sharedpreferences.getString("Port","8000");

        txt_UserName.setText(stored_user);
        txt_Pswd.setText(stored_password);

        cBoxRemMe.setChecked(!stored_user.equals(""));
        cBoxRemMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //do stuff
                if (!isChecked) {
                    if (!stored_user.equals("")) {
                        toast.setText("For clearing stored user data press \"Clear Data\" !");
                        toast.show();
                    }
                } else {
                    if (stored_user.equals("")) {
                        toast.setText("This user data will be stored on successful login!");
                        toast.show();
                    } else {
                        toast.setText("On Successful login this user data will replace the already stored user data!");
                        toast.show();
                    }
                }
            }
        });

        if(!stored_user.equals(""))
        {
            btn_Clear_Data.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_Clear_Data.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                LayoutInflater inflater = LayoutInflater.from(Login_activity.this);
                View dialogView = inflater.inflate(R.layout.app_settings, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(Login_activity.this);
                builder.setView(dialogView);
                builder.setTitle("Moodle Plus Settings");

                final EditText txtIP = (EditText) dialogView.findViewById(R.id.txtIP);
                final EditText txtPort = (EditText) dialogView.findViewById(R.id.txtPort);
                final EditText txtSettingsUserName = (EditText) dialogView.findViewById(R.id.txtSettingsUserName);
                final EditText txtSettingsPswd = (EditText) dialogView.findViewById(R.id.txtSettingsPassword);

                final CheckBox cBoxShowPswd=(CheckBox) dialogView.findViewById(R.id.checkBoxSettings);
                final Button btnForgetUser=(Button) dialogView.findViewById(R.id.btnSettingsForget);




                txtIP.setText(ip);txtPort.setText(port);txtSettingsUserName.setText(stored_user);
                txtSettingsPswd.setText(stored_password);

                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferences.Editor editor=sharedpreferences.edit();
                        editor.putString("IP",txtIP.getText().toString());
                        editor.putString("Port",txtPort.getText().toString());
                        editor.putString("UserName",txtSettingsUserName.getText().toString());
                        editor.putString("Password", txtSettingsPswd.getText().toString());
                        editor.commit();
                        toast.setText("Changes made successfully!");
                        toast.show();
                        Intent intent=new Intent(getBaseContext(),Login_activity.class);
                        finish();
                        startActivity(intent);
                    }

                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                final AlertDialog settings_dialog = builder.create();
                settings_dialog.show();
                btnForgetUser.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        txtSettingsUserName.setText("");
                        txtSettingsPswd.setText("");
                    }
                });

                cBoxShowPswd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        //do stuff
                        if (!isChecked) {
                            txtSettingsPswd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            txtSettingsPswd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        } else {
                            txtSettingsPswd.setInputType(InputType.TYPE_CLASS_TEXT);
                        }
                    }
                });


                break;
            default:
                break;
        }

        return true;
    }


    private View.OnClickListener onBut_Login = new View.OnClickListener(){
        public void onClick(View v){
            url="http://"+Login_activity.ip+":"+Login_activity.port+"/default/login.json?userid="
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

                                if(cBoxRemMe.isChecked()){
                                    SharedPreferences.Editor editor=sharedpreferences.edit();
                                    editor.putString("UserName",txt_UserName.getText().toString());
                                    editor.putString("Password",txt_Pswd.getText().toString());
                                    editor.commit();}
                                Intent intent= new Intent(Login_activity.this,HomeScreen_activity.class);
                                finish();
                                startActivity(intent);
                                toast.setText("Login Successful!");
                                toast.show();
                            }
                            else {
                                toast.setText("Login Failed - Incorrect UserName or Password!");
                                toast.show();
                                txt_Pswd.requestFocus();
                                txt_Pswd.selectAll();
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

    private View.OnClickListener onBut_Clear_Data=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            SharedPreferences.Editor editor=sharedpreferences.edit();
            editor.putString("UserName","");
            editor.putString("Password","");
            editor.commit();
            txt_UserName.setText("");
            txt_Pswd.setText("");
            stored_user="";
            stored_password="";
            txt_UserName.requestFocus();
            cBoxRemMe.setChecked(false);
            btn_Clear_Data.setVisibility(View.INVISIBLE);
            toast.setText("User Data cleared!");
            toast.show();
        }
    };




}
