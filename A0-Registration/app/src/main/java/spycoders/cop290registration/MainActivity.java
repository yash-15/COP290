/*************************************************************************************************
 * ================================
 * COP290 TEAM REGISTRATION APP
 * ================================
 *
 * DEVELOPING TEAM
 * ------------------
 * SPYCODERS
 *
 * DEVELOPERS
 * -------------
 * SAUHARD GUPTA          S      2013ME10117
 * PRABHU PRASAD PANDA    P      2013ME10859
 * YASH KUMAR BANSAL      Y      2013ME10742
 *                        C
 *                        O
 *                        D
 *                        E
 *                        R
 *                        S
 *
 * GUIDED BY
 * ------------
 * PROF. VINAY JOSEPH RIBEIRO
 *
 * SPECIAL THANKS TO
 * -------------------
 * http://developer.android.com
 * http://stackoverflow.com
 * https://github.com
 * https://slack.com   (https://spycoderscop290.slack.com)
 * For adding animation:- https://www.youtube.com/watch?v=0gElZRDtWHs
 * For error sorting:- http://www.donnfelker.com/android-validation-with-edittext/
 ************************************************************************************************/

package spycoders.cop290registration;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {

    //Initial Declaration
    //Declaration of Objects in the layout
    EditText name1txt, name2txt, name3txt, entrynumber1txt, entrynumber2txt, entrynumber3txt, teamNametxt;
    Button btn_exit, btn_reset, btn_submit;
    CheckBox CBTM;

    //Declaration of the result string which will contain the response message from the server upon an attempt to post data
    String result;

    Toast toast;    //Ths will act as a floating status bar

    //animated keeps track whether an object has animated or not
    //This is necessary to avoid repeated animations
    boolean[] animated;

    //A variable to store the member index in whose entry error is found
    byte index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The declared variables are assigned values of their respective objects
        name1txt = (EditText) findViewById(R.id.txtNAME1);
        name2txt = (EditText) findViewById(R.id.txtNAME2);
        name3txt = (EditText) findViewById(R.id.txtNAME3);
        entrynumber1txt = (EditText) findViewById(R.id.txtENTRYNUMBER1);
        entrynumber2txt = (EditText) findViewById(R.id.txtENTRYNUMBER2);
        entrynumber3txt = (EditText) findViewById(R.id.txtENTRYNUMBER3);
        teamNametxt = (EditText) findViewById(R.id.txtTEAM_NAME);
        CBTM = (CheckBox) findViewById(R.id.checkBoxAddThirdMember);
        btn_exit = (Button) findViewById(R.id.btnExit);
        btn_reset = (Button) findViewById(R.id.btnReset);
        btn_submit = (Button) findViewById(R.id.btnSUBMIT);

        //When the activity is first called excepting the team name text box all other fields are set to be invisible

        name1txt.setVisibility(View.INVISIBLE);
        name2txt.setVisibility(View.INVISIBLE);
        name3txt.setVisibility(View.INVISIBLE);
        entrynumber1txt.setVisibility(View.INVISIBLE);
        entrynumber2txt.setVisibility(View.INVISIBLE);
        entrynumber3txt.setVisibility(View.INVISIBLE);

        //Submit button is also disabled
        btn_submit.setEnabled(false);

        //Blanking the textboxes
        teamNametxt.setText("");
        name1txt.setText("");
        name2txt.setText("");
        name3txt.setText("");
        entrynumber1txt.setText("");
        entrynumber2txt.setText("");
        entrynumber3txt.setText("");


        btn_exit.setOnClickListener(onBut_Exit);
        btn_reset.setOnClickListener(onBut_Reset);
        btn_submit.setOnClickListener(onBut_Submit);

        //So as to know that the text box on focus has been changed
        name1txt.setOnFocusChangeListener(CheckFocus);
        name2txt.setOnFocusChangeListener(CheckFocus);
        name3txt.setOnFocusChangeListener(CheckFocus);
        entrynumber1txt.setOnFocusChangeListener(CheckFocus);
        entrynumber2txt.setOnFocusChangeListener(CheckFocus);
        entrynumber3txt.setOnFocusChangeListener(CheckFocus);
        teamNametxt.setOnFocusChangeListener(CheckFocus);


        toast = Toast.makeText(getApplicationContext(), "Toasty", Toast.LENGTH_SHORT);

        result = new String("");

        animated = new boolean[3];
        // 0:checkbox;1<=i<=2:team member i (both name and entry no.)
        //This check is not reqd. for member 3 as they get activated by check box.
        for (int i = 0; i < 3; i++) animated[i] = false;

        teamNametxt.requestFocus();        //focus is set to team name text box

        teamNametxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                name1txt.setVisibility(View.VISIBLE);
                entrynumber1txt.setVisibility(View.VISIBLE);
                if (!animated[1]) {
                    animated[1] = true;
                    name1txt.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    entrynumber1txt.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                }
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        name1txt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Checks the format of the text entered
                if (!isAlpha(String.valueOf(name1txt.getText())))
                    name1txt.setError(getString(R.string.Incorrect_name_format));
            }


        });

        name2txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Checks the format of the text entered
                if (!isAlpha(String.valueOf(name2txt.getText())))
                    name2txt.setError(getString(R.string.Incorrect_name_format));
            }

        });
        name3txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Checks the format of the text entered
                if (!isAlpha(String.valueOf(name3txt.getText())))
                    name3txt.setError(getString(R.string.Incorrect_name_format));

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
                if (!animated[2]) {
                    animated[2] = true;
                    name2txt.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    entrynumber2txt.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                }
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Checks the format of the text entered
                if (!checkEntryNoFormat(String.valueOf(entrynumber1txt.getText())))
                    entrynumber1txt.setError(getString(R.string.Incorrect_entry_format));

            }

        });
        entrynumber2txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                CBTM.setVisibility(View.VISIBLE);
                if (!animated[0]) {
                    animated[0] = true;
                    CBTM.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                }
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Checks the format of the text entered
                if (!checkEntryNoFormat(String.valueOf(entrynumber2txt.getText())))
                    entrynumber2txt.setError(getString(R.string.Incorrect_entry_format));
            }

        });

        entrynumber3txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Checks the format of the text entered
                if (!checkEntryNoFormat(String.valueOf(entrynumber3txt.getText())))
                    entrynumber3txt.setError(getString(R.string.Incorrect_entry_format));
            }

        });


    }

    //Displays the appropriate message on the toast after a focus is changed
    private View.OnFocusChangeListener CheckFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (name1txt.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tnamecont)
                        + getString(R.string.first) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tentry)
                        + getString(R.string.first) + getString(R.string.member));
            } else if (name2txt.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tnamecont)
                        + getString(R.string.second) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tentry)
                        + getString(R.string.second) + getString(R.string.member));
            } else if (name3txt.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tnamecont)
                        + getString(R.string.third) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tentry)
                        + getString(R.string.third) + getString(R.string.member));
            } else if (entrynumber1txt.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tentrycont)
                        + getString(R.string.first) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tname)
                        + getString(R.string.second) + getString(R.string.member));
            } else if (entrynumber2txt.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tentrycont)
                        + getString(R.string.second) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.layout_submit)
                        + getString(R.string.or) + getString(R.string.checkbox));
            } else if (entrynumber3txt.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tentrycont)
                        + getString(R.string.first) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.layout_submit) + "!");
            } else if (teamNametxt.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.team) + "\n" +
                        getString(R.string.next) + getString(R.string.tname)
                        + getString(R.string.first) + getString(R.string.member));
            } else {
            }
            if (hasFocus) toast.show();

        }
    };

    //This sub-procedure checks whether at the instance when it is invoked submit button should
    // be active or not
    public void inspect_submit() {
        btn_submit.setEnabled((String.valueOf(teamNametxt.getText()).trim().length() > 0)
                && (String.valueOf(name1txt.getText()).trim().length() > 0)
                && (String.valueOf(name2txt.getText()).trim().length() > 0)
                && (String.valueOf(entrynumber2txt.getText()).trim().length() > 0)
                && (String.valueOf(entrynumber1txt.getText()).trim().length() > 0)
                && (!(CBTM.isChecked()) || ((String.valueOf(name3txt.getText()).trim().length() > 0)
                && (String.valueOf(entrynumber3txt.getText()).trim().length() > 0))));
    }


    //Checks whether checkbox is checked or not and works accordingly
    //Help obtained from http://developer.android.com/guide/topics/ui/controls/checkbox.html
    public void Select(View t) {

        boolean checked = ((CheckBox) t).isChecked();


        switch (t.getId()) {
            case R.id.checkBoxAddThirdMember:
                if (checked) {
                    name3txt.setVisibility(View.VISIBLE);
                    entrynumber3txt.setVisibility(View.VISIBLE);
                    name3txt.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    entrynumber3txt.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    toast.setText(getString(R.string.now) + getString(R.string.activate_third_mem) + "!" +
                            "\n" + getString(R.string.next) + getString(R.string.tname) +
                            getString(R.string.third) + getString(R.string.member));
                    toast.show();
                } else {
                    name3txt.setVisibility(View.INVISIBLE);
                    entrynumber3txt.setVisibility(View.INVISIBLE);
                }
                inspect_submit();

                break;

        }
    }


    private View.OnClickListener onBut_Exit = new View.OnClickListener() {
        public void onClick(View v) {
            System.exit(0);
        }
    };

    private View.OnClickListener onBut_Reset = new View.OnClickListener() {
        public void onClick(View v) {
            //Snippet from
            // http://stackoverflow.com/questions/15564614/how-to-restart-an-android-application-programmatically
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();  //kills the current activity
            startActivity(i);
        }
    };
    private View.OnClickListener onBut_Submit = new View.OnClickListener() {
        public void onClick(View v) {

            //At first check whether the entry number is of appropriate format i.e, 20NN-CCC-NNNN
            //C-Character N-Number
            //In the department field the third one is character so as to support MTech Students as well.
            //Some MTech students are also registered for this course.
            //Scope for improvement - This can be done during focus change as well.
            if (!(checkEntryNoFormat(entrynumber1txt.getText().toString())
                    && entrynumber1txt.getText().toString().length() == 11)) {
                index = 1;
                WrongFormatDialogFragment wfdiafrag = new WrongFormatDialogFragment();
                wfdiafrag.show(getFragmentManager(), "Wrong Format 1");
            } else if (!(checkEntryNoFormat(entrynumber2txt.getText().toString()))) {
                index = 2;
                WrongFormatDialogFragment wfdiafrag = new WrongFormatDialogFragment();
                wfdiafrag.show(getFragmentManager(), "Wrong Format 2");
            } else if (CBTM.isChecked() && !(checkEntryNoFormat(entrynumber3txt.getText().toString()))) {
                index = 3;
                WrongFormatDialogFragment wfdiafrag = new WrongFormatDialogFragment();
                wfdiafrag.show(getFragmentManager(), "Wrong Format 3");
            } else {


                //Checks whether any Entry Number has been repeated in the form

                index = 0;
                if (entrynumber1txt.getText().toString().equals(entrynumber2txt.getText().toString())) {
                    index = 2;
                } else if (CBTM.isChecked() && ((entrynumber1txt.getText().toString().equals(entrynumber3txt.getText().toString()))
                        || (entrynumber2txt.getText().toString().equals(entrynumber3txt.getText().toString())))) {
                    index = 3;
                }

                if (index != 0) {
                    EntryNoRepeatDialogFragment entryrepeatmsg = new EntryNoRepeatDialogFragment();
                    entryrepeatmsg.show(getFragmentManager(), "Entry No. repeat!");
                } else {

                    //Requires Internet Connection

                    String url1 = "http://ldap1.iitd.ernet.in/LDAP/courses/COP290.shtml"; //Mailing List site
                    String url2 = "http://agni.iitd.ernet.in/cop290/assign0/register/"; //Registration site

                    URL myURL;
                    HttpURLConnection conn;

                    //Checking whether the entered name of each team member corresponds to the entered
                    //entry no.

                    try {

                        result = "";
                        myURL = new URL(url1);
                        conn = (HttpURLConnection) myURL.openConnection();

                        enableStrictMode();
                        BufferedReader in = new BufferedReader(new InputStreamReader(
                                conn.getInputStream())); //Snippet for using BufferedReader to get data from url from
                        // https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
                        String inputLine;// reads one line at a time from the BufferedReader
                        String[] stemp = {entrynumber1txt.getText().toString().toLowerCase()
                                , entrynumber2txt.getText().toString().toLowerCase()
                                , CBTM.isChecked() ? entrynumber3txt.getText().toString().toLowerCase() : "abcdefghijk"};

                        //searchString contains the kerberos id version of entry number
                        String[] searchString = {stemp[0].substring(4, 7).concat(stemp[0].substring(2, 4)).concat(stemp[0].substring(7, 11)),
                                stemp[1].substring(4, 7).concat(stemp[1].substring(2, 4)).concat(stemp[1].substring(7, 11)),
                                stemp[2].substring(4, 7).concat(stemp[2].substring(2, 4)).concat(stemp[2].substring(7, 11))};

                        boolean[] found = new boolean[3];
                        if (!(CBTM.isChecked())) found[2] = true;
                        boolean justfound = false, compatible = true;

                        while ((inputLine = in.readLine()) != null) {
                            if (justfound) {
                                String temp1 = inputLine.substring(4, inputLine.length() - 5);
                                String temp2;
                                switch (index) {
                                    case 1:
                                        temp2 = name1txt.getText().toString();
                                        break;
                                    case 2:
                                        temp2 = name2txt.getText().toString();
                                        break;
                                    case 3:
                                        temp2 = name3txt.getText().toString();
                                        break;
                                    default:
                                        temp2 = "";
                                }
                                if (temp1.toLowerCase().equals(temp2.toLowerCase())) {
                                    found[index - 1] = true;
                                    justfound = false;
                                } else {
                                    compatible = false;
                                }
                                if (!(compatible) || (found[0] && found[1] && found[2])) break;
                            } else if (inputLine.equals("<TR><TD ALIGN=LEFT>" + searchString[0] + "</TD>")) {
                                justfound = true;
                                index = 1;
                            } else if (inputLine.equals("<TR><TD ALIGN=LEFT>" + searchString[1] + "</TD>")) {
                                justfound = true;
                                index = 2;
                            } else if (inputLine.equals("<TR><TD ALIGN=LEFT>" + searchString[2] + "</TD>")) {
                                justfound = true;
                                index = 3;
                            } else {
                                justfound = false;
                            }
                        }
                        in.close();

                        if (!compatible) {//if there is a mismatch
                            NameEntryMismatchDialogFragment mismatchdiafragment = new NameEntryMismatchDialogFragment();
                            mismatchdiafragment.show(getFragmentManager(), "Name Entry Mismatch");
                        } else if (found[0] && found[1] && found[2]) {
                            //Posting the form entries to the server @ url2
                            try {
                                result = "";
                                myURL = new URL(url2);
                                conn = (HttpURLConnection) myURL.openConnection();
                                conn.setRequestMethod("POST");

                                //Snippet for connection codes from
                                // http://stackoverflow.com/questions/10500775/parse-json-from-httpurlconnection-object
                                Map<String, Object> params = new LinkedHashMap<>();
                                params.put("teamname", teamNametxt.getText().toString());
                                params.put("entry1", entrynumber1txt.getText().toString());
                                params.put("name1", name1txt.getText().toString());
                                params.put("entry2", entrynumber2txt.getText().toString());
                                params.put("name2", name2txt.getText().toString());
                                //If the third member checkbox is unchecked then the information of third member is not sent
                                if (CBTM.isChecked()) {
                                    params.put("entry3", entrynumber3txt.getText().toString());
                                    params.put("name3", name3txt.getText().toString());
                                }
                                //I don't know whether this is reqd. or not but for safety I have included this
                                // Anyways this is not going to do any harm
                                else {
                                    params.put("entry3", "");
                                    params.put("name3", "");
                                }

                                StringBuilder postData = new StringBuilder();
                                for (Map.Entry<String, Object> param : params.entrySet()) {
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

                                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                                for (int c = in.read(); c != -1; c = in.read())
                                    result = result.concat(Character.toString((char) c));
                                //The String result contains the result of the submission.

                            } catch (Exception e) {
                                result = e.toString();
                            } finally {
                                //For an already registered user
                                if (result.equals("{\"RESPONSE_SUCCESS\": 0, \"RESPONSE_MESSAGE\": \"User already registered\"}")) {
                                    UserExistsDialogFragment existmsgbox = new UserExistsDialogFragment();
                                    existmsgbox.show(getFragmentManager(), "Already Exists");
                                }

                                //Upon the successful addition of an entry
                                else if (result.equals("{\"RESPONSE_SUCCESS\": 1, \"RESPONSE_MESSAGE\": \"Registration completed\"}")) {
                                    SuccMsgDialogFragment succmsgbox = new SuccMsgDialogFragment();
                                    succmsgbox.show(getFragmentManager(), "Successful Response");
                                } else if (result.equals("{\"RESPONSE_SUCCESS\": 0, \"RESPONSE_MESSAGE\": \"Data not posted!\"}")) {
                    /*
                        *This case should not arise in real
                        * Because this is the case when incomplete information is sent to the server
                        * But our pre-check after clicking the submit button would never allow to post such incomplete info.
                    */
                                }

                                //Any other value of the string result implies a connection failure
                                else {
                                    ConnFailDialogFragment connfailmsg = new ConnFailDialogFragment();
                                    connfailmsg.show(getFragmentManager(), "Connection Failed");
                                }


                            }
                        } else {
                            index = found[2] ? (byte) index : 3;
                            index = found[1] ? (byte) index : 2;
                            index = found[0] ? (byte) index : 1;
                            NotRegCourseDialogFragment notregdiafragment = new NotRegCourseDialogFragment();
                            notregdiafragment.show(getFragmentManager(), "Not registered for course");
                        }
                    } catch (Exception e) {

                        ConnFailDialogFragment connfailmsg = new ConnFailDialogFragment();
                        connfailmsg.show(getFragmentManager(), "Connection Failed");
                    }
                }

            }

        }
    };


    //Help for making dialog box received from
    //http://developer.android.com/guide/topics/ui/dialogs.html

    //Entry Number Repeat Dialog Box
    public class EntryNoRepeatDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.repeat_entry_no_title));
            builder.setMessage(getString(R.string.repeat_entry_no_msg1) + index + getString(R.string.repeat_entry_no_msg2))
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Just exits the dialog box
                            result = "";
                            dialog.dismiss();
                            switch (index) {
                                case 1:
                                    entrynumber1txt.requestFocus();
                                    break;
                                case 2:
                                    entrynumber2txt.requestFocus();
                                    break;
                                case 3:
                                    entrynumber3txt.requestFocus();
                                    break;
                            }
                        }
                    });
            return builder.create();

        }
    }


    //Name Entry Number Mismatch Dialog Box
    public class NameEntryMismatchDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.name_entry_mismatch_title));
            builder.setMessage(getString(R.string.name_entry_mismatch_msg) + index + " !")
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Just exits the dialog box
                            result = "";
                            dialog.dismiss();
                            switch (index) {
                                case 1:
                                    name1txt.requestFocus();
                                    break;
                                case 2:
                                    name2txt.requestFocus();
                                    break;
                                case 3:
                                    name3txt.requestFocus();
                                    break;
                            }
                        }
                    });
            return builder.create();

        }
    }


    //Team Member not registered for COP290 course
    public class NotRegCourseDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.not_reg_title));
            builder.setMessage(getString(R.string.not_reg_msg1) + index + getString(R.string.not_reg_msg2))
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Just exits the dialog box
                            result = "";
                            dialog.dismiss();
                            switch (index) {
                                case 1:
                                    entrynumber1txt.requestFocus();
                                    break;
                                case 2:
                                    entrynumber2txt.requestFocus();
                                    break;
                                case 3:
                                    entrynumber3txt.requestFocus();
                                    break;
                            }
                        }
                    });
            return builder.create();

        }
    }

    //Incorrect Entry Number Format Dialog Box
    public class WrongFormatDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.Incorrect_entry_format_title));
            builder.setMessage(getString(R.string.Incorrect_entry_format))
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Just exits the dialog box
                            result = "";
                            dialog.dismiss();
                            switch (index) {
                                case 1:
                                    entrynumber1txt.requestFocus();
                                    break;
                                case 2:
                                    entrynumber2txt.requestFocus();
                                    break;
                                case 3:
                                    entrynumber3txt.requestFocus();
                                    break;
                            }
                        }
                    });
            return builder.create();

        }
    }

    //No connection dialog box
    public class ConnFailDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.net_error_title));
            builder.setMessage(getString(R.string.net_error_msg))
                    .setPositiveButton(getString(R.string.back), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Just exits the dialog box
                            result = "";
                        }
                    })
                    .setNeutralButton(getString(R.string.retry), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Resends the request
                            result = "";
                            onBut_Submit.onClick(getView());
                        }
                    })
                    .setNegativeButton(getString(R.string.layout_exit), new DialogInterface.OnClickListener() {
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
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.user_exists_title));
            builder.setMessage(getString(R.string.user_exists_msg))
                    .setPositiveButton(getString(R.string.edit), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Just exits the dialog box and sets focus on member 1
                            result = "";
                            dialog.dismiss();
                            name1txt.requestFocus();
                        }
                    })
                    .setNegativeButton(getString(R.string.layout_exit), new DialogInterface.OnClickListener() {
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
            builder.setTitle(getString(R.string.success_title));
            builder.setMessage(getString(R.string.success_msg1) + teamNametxt.getText().toString() + getString(R.string.success_msg2))
                    .setPositiveButton(getString(R.string.new_team), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Reset the form and start afresh
                            //Snippet used from
                            // http://stackoverflow.com/questions/15564614/how-to-restart-an-android-application-programmatically
                            Intent i = getBaseContext().getPackageManager()
                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();  //kills the current activity
                            startActivity(i);

                        }
                    })
                    .setNegativeButton(getString(R.string.layout_exit), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Exit the app
                            System.exit(0);
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }


    //This function checks whether any given string is a natural number or not
    private boolean isNumeric(String s) {
        int l = s.length();
        boolean ans = true;
        for (int i = 0; i < l; i++) {
            char temp = s.charAt(i);
            if (temp < 48 || temp > 57) {
                ans = false;
                break;
            }
        }
        return ans;
    }

    //Checks whether the characters are explicitly in (A-Z and 0-9)
    private boolean isAlphaNumeric(String s) {
        int l = s.length();
        boolean ans = true;
        for (int i = 0; i < l; i++) {
            char temp = s.charAt(i);
            if (!((temp >= 48 && temp <= 57) || (temp >= 65 && temp <= 90))) {
                ans = false;
                break;
            }
        }
        return ans;
    }

    //Checks whether the characters are explicitly in (A-Z or a-z or ' ' or '.' )
    private boolean isAlpha(String s) {
        int l = s.length();
        boolean ans = true;
        for (int i = 0; i < l; i++) {
            char temp = s.charAt(i);
            if (!((temp >= 97 && temp <= 122) || (temp >= 65 && temp <= 90) || (temp == 32) || (temp == 46))) {
                ans = false;
                break;
            }
        }
        return ans;
    }


    //Checks whether a given string till its length abides with the entry no. format
    public boolean checkEntryNoFormat(String s) {
        String ref_string = "2013ME10859";
        int l = s.length();
        if (l > 11) {
            return false;
        } else {
            String test_string = s.concat(ref_string.substring(l, 11));
            return ((test_string.substring(0, 2).equals("20")) && (isNumeric(test_string.substring(2, 4)))
                    && (isAlphaNumeric(test_string.substring(4, 7)))
                    && (isNumeric(test_string.substring(7, 11))));
        }
    }


    //To overcome the NetworkOnMainThreadException
    //Snippet from
    //http://stackoverflow.com/questions/8258725/strict-mode-in-android-2-2
    public void enableStrictMode() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }
}