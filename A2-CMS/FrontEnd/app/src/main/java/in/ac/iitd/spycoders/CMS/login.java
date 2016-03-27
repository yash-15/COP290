package in.ac.iitd.spycoders.CMS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.net.CookieHandler;
import java.net.CookieManager;


public class login extends AppCompatActivity{

    private Button clearData;
    private EditText userName, password;

    Toast toastObj;
    public static RequestQueue queue;
    String url;

    String ip,port;

    CookieManager manager;

    SharedPreferences loginCredentials, userInfo;
    SharedPreferences.Editor loginCredentialsEditor,userInfoEditor;

    String storedUser,storedPassword;

    CheckBox rememberMe;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queue= Volley.newRequestQueue(this);

        userName =(EditText) findViewById(R.id.textUserName);
        password = (EditText) findViewById(R.id.textPassword);

        Button login = (Button) findViewById(R.id.btnLogin);
        login.setOnClickListener(onBut_Login);

        clearData =(Button) findViewById(R.id.btnClear);
        clearData.setOnClickListener(onBut_Clear_Data);

        Button forgotPassword = (Button) findViewById(R.id.btnFP);
        forgotPassword.setOnClickListener(onBut_ForgotPassword);

        rememberMe =(CheckBox) findViewById(R.id.checkBoxRem_me);

        toastObj = new Toast(getApplicationContext());
        toastObj.setDuration(Toast.LENGTH_SHORT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //TODO : toolbar seems obsolete here

        manager = new CookieManager();
        CookieHandler.setDefault(manager);
        //TODO : are the cookies handled completely

        loginCredentials = getSharedPreferences("LoginCredentials", MODE_PRIVATE);
        userInfo = getSharedPreferences("UserInfo",MODE_PRIVATE);
        loginCredentialsEditor = loginCredentials.edit();
        userInfoEditor = userInfo.edit();

        storedUser = loginCredentials.getString("UserName", "");
        storedPassword = loginCredentials.getString("Password", "");
        ip= loginCredentials.getString("IP", "127.0.0.1");
        port= loginCredentials.getString("Port","8000");


        userName.setText(storedUser);
        password.setText(storedPassword);

        rememberMe.setChecked(!storedUser.equals(""));
        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    if (!storedUser.equals(""))
                        popToast("For clearing stored user data press \"Clear localData\" !");
                } else {
                    if (storedUser.equals(""))
                        popToast("This user data will be stored on successful login!");
                    else
                        popToast("On Successful login this user data will replace the already stored user data!");
                }
            }
        });

        if(!storedUser.equals(""))
            clearData.setVisibility(View.VISIBLE);
        else
            clearData.setVisibility(View.INVISIBLE);

    }

    /*
    TODO : Set IP and Port settings from app
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




                txtIP.setText(ip);txtPort.setText(port);txtSettingsUserName.setText(storedUser);
                txtSettingsPswd.setText(storedPassword);

                builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPreferences.Editor editor= loginCredentials.edit();
                        editor.putString("IP",txtIP.getText().toString());
                        editor.putString("Port",txtPort.getText().toString());
                        editor.putString("UserName",txtSettingsUserName.getText().toString());
                        editor.putString("Password", txtSettingsPswd.getText().toString());
                        editor.commit();
                        toastObj.setText("Changes made successfully!");
                        toastObj.show();
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
    */

    private View.OnClickListener onBut_Login = new View.OnClickListener(){
        public void onClick(View v){
            url="http://"+ip+":"+port+"/default/login.json?userid="
                    + userName.getText().toString()
                    + "&password="+ password.getText().toString();

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    if (response.optBoolean("success")) {
                        JSONObject response_userInfo=response.optJSONObject("user");
                        userInfoEditor.putString("userName", response_userInfo.optString("username"));
                        userInfoEditor.putString("password", response_userInfo.optString("password"));
                        userInfoEditor.putString("firstName", response_userInfo.optString("first_name"));
                        userInfoEditor.putString("lastName", response_userInfo.optString("last_name"));
                        userInfoEditor.putString("email", response_userInfo.optString("last_name"));
                        userInfoEditor.putString("address", response_userInfo.optString("last_name"));
                        userInfoEditor.putInt("userId", response_userInfo.optInt("id"));
                        userInfoEditor.putInt("locality", response_userInfo.optInt("locality"));
                        userInfoEditor.putLong("mobile", response_userInfo.optLong("mobile"));
                        //TODO : add support for profile-pic
                        userInfoEditor.apply();

                        if(rememberMe.isChecked()){
                            loginCredentialsEditor.putString("UserName", userName.getText().toString());
                            loginCredentialsEditor.putString("Password", password.getText().toString());
                            loginCredentialsEditor.putString("IP", ip);
                            loginCredentialsEditor.putString("Port", port);
                            loginCredentialsEditor.apply();
                        }
                        Intent intent= new Intent(login.this,category.class);
                        // FIXME: link class correctly
                        finish();
                        startActivity(intent);
                        popToast("Login Successful!");
                    }
                    else {
                        popToast("Login Failed - Incorrect UserName or Password!");
                        password.requestFocus();
                        password.selectAll();
                    }
                }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        popToast("Network Error!");
                    }
                }
            );

            queue.add(jsObjRequest);
        }
    };

    private View.OnClickListener onBut_ForgotPassword =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popToast("Contact Server Administrators!");
        }
    };

    private View.OnClickListener onBut_Clear_Data=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            loginCredentialsEditor.putString("UserName","");
            loginCredentialsEditor.putString("Password", "");
            loginCredentialsEditor.commit();
            userName.setText("");
            password.setText("");
            storedUser ="";
            storedPassword ="";
            userName.requestFocus();
            rememberMe.setChecked(false);
            clearData.setVisibility(View.INVISIBLE);
            popToast("User localData cleared!");
        }
    };

    private void popToast(String message) {
        toastObj.setText(message);
        toastObj.show();
    }
}
