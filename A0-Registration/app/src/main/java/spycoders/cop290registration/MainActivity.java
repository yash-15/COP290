package spycoders.cop290registration;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity  {

    EditText name1txt, name2txt, name3txt, entrynumber1txt, entrynumber2txt, entrynumber3txt, teamNametxt;
    CheckBox CBTM;
    String result=new String("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name1txt = (EditText) findViewById(R.id.txtNAME1);

        name2txt = (EditText) findViewById(R.id.txtNAME2);
        name3txt = (EditText) findViewById(R.id.txtNAME3);
        entrynumber1txt = (EditText) findViewById(R.id.txtENTRYNUMBER1);
        entrynumber2txt = (EditText) findViewById(R.id.txtENTRYNUMBER2);
        entrynumber3txt = (EditText) findViewById(R.id.txtENTRYNUMBER3);
        teamNametxt = (EditText) findViewById(R.id.txtTEAM_NAME);
        CBTM=(CheckBox)findViewById(R.id.checkBoxAddThirdMember);


        final Button btn = (Button) findViewById(R.id.btnSUBMIT);

        btn.setOnClickListener(onBut);


        name1txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn.setEnabled((String.valueOf(teamNametxt.getText()).trim().length() > 0)
                        && (String.valueOf(name1txt.getText()).trim().length() > 0)
                        && (String.valueOf(name2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber1txt.getText()).trim().length() > 0));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        name2txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btn.setEnabled((String.valueOf(teamNametxt.getText()).trim().length() > 0)
                        && (String.valueOf(name1txt.getText()).trim().length() > 0)
                        && (String.valueOf(name2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber1txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber1txt.getText()).trim().length() > 0));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        entrynumber1txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name2txt.setVisibility(View.VISIBLE);
                entrynumber2txt.setVisibility(View.VISIBLE);
                btn.setEnabled((String.valueOf(teamNametxt.getText()).trim().length() > 0)
                        && (String.valueOf(name1txt.getText()).trim().length() > 0)
                        && (String.valueOf(name2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber1txt.getText()).trim().length() > 0));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        entrynumber2txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                CBTM.setVisibility(View.VISIBLE);

                btn.setEnabled((String.valueOf(teamNametxt.getText()).trim().length() > 0)
                        && (String.valueOf(name1txt.getText()).trim().length() > 0)
                        && (String.valueOf(name2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber1txt.getText()).trim().length() > 0));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });
        teamNametxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name1txt.setVisibility(View.VISIBLE);
                entrynumber1txt.setVisibility(View.VISIBLE);
                btn.setEnabled((String.valueOf(teamNametxt.getText()).trim().length() > 0)
                        && (String.valueOf(name1txt.getText()).trim().length() > 0)
                        && (String.valueOf(name2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber2txt.getText()).trim().length() > 0)
                        && (String.valueOf(entrynumber1txt.getText()).trim().length() > 0));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });






    }
    public void Select(View t) {

        boolean checked = ((CheckBox) t).isChecked();


        switch(t.getId()) {
            case R.id.checkBoxAddThirdMember:
                if (checked)
                {name3txt.setVisibility(View.VISIBLE);
                    entrynumber3txt.setVisibility(View.VISIBLE);}
                else
                {
                    name3txt.setVisibility(View.INVISIBLE);
                    entrynumber3txt.setVisibility(View.INVISIBLE);
                }

                    break;

        }
    }



    private View.OnClickListener onBut=new View.OnClickListener() {
        public void onClick(View v) {



            String url= "http://agni.iitd.ernet.in/cop290/assign0/register/";
            try{
                URL myURL = new URL(url);
                HttpURLConnection conn= (HttpURLConnection)myURL.openConnection();
                conn.setRequestMethod("POST");

                //Snippet from http://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
                Map<String,Object> params= new LinkedHashMap<>();
                params.put("teamname",teamNametxt.getText().toString());
                params.put("entry1", entrynumber1txt.getText().toString());
                params.put("name1",name1txt.getText().toString());
                params.put("entry2", entrynumber2txt.getText().toString());
                params.put("name2",name2txt.getText().toString());
                params.put("entry3", entrynumber3txt.getText().toString());
                params.put("name3",name3txt.getText().toString());

                StringBuilder postData = new StringBuilder();
                for (Map.Entry<String,Object> param : params.entrySet()) {
                    if (postData.length() != 0) postData.append('&');
                    postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                    postData.append('=');
                    postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
                }
                byte[] postDataBytes = postData.toString().getBytes("UTF-8");

                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                conn.setDoOutput(true);

                enableStrictMode();
                conn.getOutputStream().write(postDataBytes);

                Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                for ( int c = in.read(); c != -1; c = in.read() )
                   result=result.concat(Character.toString((char)c));
                //The String result contains the result of the submission.

            }
            catch(Exception e){result=e.toString();}
            finally{


                if (result.equals("{\"RESPONSE_SUCCESS\": 0, \"RESPONSE_MESSAGE\": \"User already registered\"}"))
                {
                   UserExistsDialogFragment existmsgbox = new UserExistsDialogFragment();
                    existmsgbox.show(getFragmentManager(),"Already Exists");
                }
                else if (result.equals("{\"RESPONSE_SUCCESS\": 1, \"RESPONSE_MESSAGE\": \"Registration completed\"}"))
                {
                    SuccMsgDialogFragment succmsgbox = new SuccMsgDialogFragment() ;
                    succmsgbox.show(getFragmentManager(),"Successful Response");
                }
                else if (result.equals("{\"RESPONSE_SUCCESS\": 0, \"RESPONSE_MESSAGE\": \"Data not posted!\"}"))
                {
                    /*
                    msg.setText("One or more fields are missing!!");
                    anot_form_btn.setText("Edit");
                    exit_btn.setText("EXIT");*/
                }
                else
                {
                    ConnFailDialogFragment connfailmsg=new ConnFailDialogFragment();
                    connfailmsg.show(getFragmentManager(),"Connection Failed");
                }



            }


        }
    };

    //No connection dialog box
    public class ConnFailDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
            builder.setMessage("The connection to the server failed!")
                    .setPositiveButton("Back",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            //Just exits the dialog box
                            result="";
                        }
                    })
                    .setNeutralButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Resends the request
                            onBut.onClick(getView());
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Exits the app
                            System.exit(0);
                        }
                    });
            return builder.create();

        }
    }
    //User already exists dialog box
    public class UserExistsDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("One or more users are already registered!")
                    .setPositiveButton("Edit",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id){
                            //Just exits the dialog box
                            result="";
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Exit the app
                            System.exit(0);
                        }
                    });
            return builder.create();
        }
    }


    //Success dialog box
    public class SuccMsgDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("success_message")
                    .setPositiveButton("new_team", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Reset the form and start afresh
                            result="";
                        }
                    })
                    .setNegativeButton("exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Exit the app
                            System.exit(0);
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    //To overcome the NetworkOnMainThreadException
    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }
}

