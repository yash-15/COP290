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
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
    int numberOfStudents;
    EditText[] studentName, entryNumber;
    EditText teamName;
    Button btn_exit, btn_reset, btn_submit;
    CheckBox CBTM;
    //Declaration of the result string which will contain the response message from the server upon an attempt to post data
    String result;

    Toast toast;    //Ths will act as a floating status bar

    //isAnimated keeps track whether an object has been animated or not
    //This is necessary to avoid repeated animations
    boolean[] isAnimated;

    //A variable to store the member index in whose entry error is found
    byte index = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberOfStudents = 3;
        studentName = new EditText[numberOfStudents];
        entryNumber = new EditText[numberOfStudents];
        isAnimated = new boolean[numberOfStudents];

        //The declared variables are assigned values of their respective objects
        studentName[0] = (EditText) findViewById(R.id.txtNAME1);
        studentName[1] = (EditText) findViewById(R.id.txtNAME2);
        studentName[2] = (EditText) findViewById(R.id.txtNAME3);
        entryNumber[0] = (EditText) findViewById(R.id.txtENTRYNUMBER1);
        entryNumber[1] = (EditText) findViewById(R.id.txtENTRYNUMBER2);
        entryNumber[2] = (EditText) findViewById(R.id.txtENTRYNUMBER3);
        teamName = (EditText) findViewById(R.id.txtTEAM_NAME);
        CBTM = (CheckBox) findViewById(R.id.checkBoxAddThirdMember);
        btn_exit = (Button) findViewById(R.id.btnExit);
        btn_reset = (Button) findViewById(R.id.btnReset);
        btn_submit = (Button) findViewById(R.id.btnSUBMIT);

        //When the activity is first called excepting the team name text box all other fields are set to be invisible
        //Blanking the text boxes
        //So as to know that the text box on focus has been changed

        teamName.setText("");
        for (int i = 0; i < numberOfStudents; i++) {
            studentName[i].setVisibility(View.INVISIBLE);
            studentName[i].setText("");
            studentName[i].setOnFocusChangeListener(CheckFocus);

            entryNumber[i].setVisibility(View.INVISIBLE);
            entryNumber[i].setText("");
            entryNumber[i].setOnFocusChangeListener(CheckFocus);
        }

        //Submit button is also disabled
        btn_submit.setEnabled(false);
        btn_exit.setOnClickListener(onBut_Exit);
        btn_reset.setOnClickListener(onBut_Reset);
        btn_submit.setOnClickListener(onBut_Submit);

        teamName.setOnFocusChangeListener(CheckFocus);


        toast = Toast.makeText(getApplicationContext(), "Toasty", Toast.LENGTH_SHORT);
        toast.show();

        result = "";

        isAnimated = new boolean[3];
        for (int i = 0; i < 3; i++)
            isAnimated[i] = false;

        teamName.requestFocus();        //focus is set to team name text box

        teamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                studentName[0].setVisibility(View.VISIBLE);
                entryNumber[0].setVisibility(View.VISIBLE);
                if (!isAnimated[0]) {
                    isAnimated[0] = true;
                    studentName[0].startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    entryNumber[0].startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                }
                inspect_submit();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        for(int i=0;i<numberOfStudents;i++) {
            studentName[i].addTextChangedListener(new studentNameTextWatcher(i));
            entryNumber[i].addTextChangedListener(new entryNumberTextWatcher(i));
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private class studentNameTextWatcher implements TextWatcher {
        private int j;
        private studentNameTextWatcher(int z){
            j=z;
        }
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

            if (!isAlpha(String.valueOf(studentName[j].getText())))
                studentName[j].setError(getString(R.string.Incorrect_name_format));
        }
    }
    private class entryNumberTextWatcher implements TextWatcher {
        private int j;
        private entryNumberTextWatcher(int z){
            j=z;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (j + 1 < numberOfStudents - 1) {
                studentName[j + 1].setVisibility(View.VISIBLE);
                entryNumber[j + 1].setVisibility(View.VISIBLE);
                if (!isAnimated[j + 1]) {
                    isAnimated[j + 1] = true;
                    studentName[j + 1].startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    entryNumber[j + 1].startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                }
            } else if (j + 1 == numberOfStudents - 1) {
                CBTM.setVisibility(View.VISIBLE);
                if (!isAnimated[j + 1]) {
                    isAnimated[j + 1] = true;
                    CBTM.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                }
            }
            inspect_submit();
        }

        @Override
        public void afterTextChanged(Editable s) {
            //Checks the format of the text entered
            if (!checkEntryNoFormat(String.valueOf(entryNumber[j].getText())))
                entryNumber[j].setError(getString(R.string.Incorrect_entry_format));
        }
    }
    //Displays the appropriate message on the toast after a focus is changed
    private View.OnFocusChangeListener CheckFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (studentName[0].hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tnamecont)
                        + getString(R.string.first) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tentry)
                        + getString(R.string.first) + getString(R.string.member));
            } else if (studentName[1].hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tnamecont)
                        + getString(R.string.second) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tentry)
                        + getString(R.string.second) + getString(R.string.member));
            } else if (studentName[2].hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tnamecont)
                        + getString(R.string.third) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tentry)
                        + getString(R.string.third) + getString(R.string.member));
            } else if (entryNumber[0].hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tentrycont)
                        + getString(R.string.first) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.tname)
                        + getString(R.string.second) + getString(R.string.member));
            } else if (entryNumber[1].hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tentrycont)
                        + getString(R.string.second) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.layout_submit)
                        + getString(R.string.or) + getString(R.string.checkbox));
            } else if (entryNumber[2].hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.tentrycont)
                        + getString(R.string.third) + getString(R.string.member) + "\n" +
                        getString(R.string.next) + getString(R.string.layout_submit) + "!");
            } else if (teamName.hasFocus()) {
                toast.setText(getString(R.string.now) + getString(R.string.team) + "\n" +
                        getString(R.string.next) + getString(R.string.tname)
                        + getString(R.string.first) + getString(R.string.member));
            }
            if (hasFocus)
                toast.show();
        }
    };

    //This sub-procedure checks whether at the instance when it is invoked submit button should
    // be active or not
    public void inspect_submit() {
        boolean resp;
        resp = (String.valueOf(teamName.getText()).trim().length() > 0);
        for(int i=0;i<numberOfStudents-1 ;i++) {
            resp &= (String.valueOf(studentName[i].getText()).trim().length() > 0);
            resp &= (String.valueOf(entryNumber[i].getText()).trim().length() > 0);
        }
        if(CBTM.isChecked()) {
            resp &=  (String.valueOf(studentName[numberOfStudents-1].getText()).trim().length() > 0);
            resp &= (String.valueOf(entryNumber[numberOfStudents-1].getText()).trim().length() > 0);
        }
        btn_submit.setEnabled(resp);
    }


    //Checks whether checkbox is checked or not and works accordingly
    //Help obtained from http://developer.android.com/guide/topics/ui/controls/checkbox.html
    public void Select(View t) {

        boolean checked = ((CheckBox) t).isChecked();
        int id = numberOfStudents-1;

        switch (t.getId()) {
            case R.id.checkBoxAddThirdMember:
                if (checked) {
                    studentName[id].setVisibility(View.VISIBLE);
                    entryNumber[id].setVisibility(View.VISIBLE);
                    studentName[id].startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    entryNumber[id].startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.slide_in_left));
                    toast.setText(getString(R.string.now) + getString(R.string.activate_third_mem) + "!" +
                            "\n" + getString(R.string.next) + getString(R.string.tname) +
                            getString(R.string.third) + getString(R.string.member));
                    toast.show();
                } else {
                    studentName[id].setVisibility(View.INVISIBLE);
                    entryNumber[id].setVisibility(View.INVISIBLE);
                }
                inspect_submit();
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
            index = 0;
            for (int i = 0; i < numberOfStudents - 1; i++) {
                if (!(checkEntryNoFormat(entryNumber[i].getText().toString())
                        && entryNumber[i].getText().toString().length() == 11)) {
                    index = (byte) (i + 1);
                    WrongFormatDialogFragment wfdiafrag = new WrongFormatDialogFragment();
                    wfdiafrag.show(getFragmentManager(), "Wrong Format " + (i + 1));
                    break;
                }
            }
            if (index == 0 && CBTM.isChecked())
                if (!(checkEntryNoFormat(entryNumber[numberOfStudents - 1].getText().toString())
                    && entryNumber[numberOfStudents - 1].getText().toString().length() == 11)) {
                    int i = numberOfStudents - 1;
                    index = (byte) (i + 1);
                    WrongFormatDialogFragment wfdiafrag = new WrongFormatDialogFragment();
                    wfdiafrag.show(getFragmentManager(), "Wrong Format " + (i + 1));
                }
            else {
                //Checks whether any Entry Number has been repeated in the form
                index = 0;
                if (entryNumber[0].getText().toString().equals(entryNumber[1].getText().toString()))
                    index = 2;
                else if (CBTM.isChecked() && ((entryNumber[0].getText().toString().equals(entryNumber[2].getText().toString()))
                        || (entryNumber[1].getText().toString().equals(entryNumber[2].getText().toString()))))
                    index = 3;

                if (index != 0) {
                    EntryNoRepeatDialogFragment entryrepeatmsg = new EntryNoRepeatDialogFragment();
                    entryrepeatmsg.show(getFragmentManager(), "Entry No. repeat!");
                }
                else {

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
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        //Snippet for using BufferedReader to get data from url from
                        // https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
                        String inputLine;// reads one line at a time from the BufferedReader
                        String[] stemp = {entryNumber[0].getText().toString().toLowerCase()
                                , entryNumber[1].getText().toString().toLowerCase()
                                , CBTM.isChecked() ? entryNumber[2].getText().toString().toLowerCase() : "abcdefghijk"};

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
                                String temp2 = "";
                                if(index>0 && index<=numberOfStudents)
                                       temp2 = studentName[index-1].getText().toString();
                                if (temp1.toLowerCase().equals(temp2.toLowerCase())) {
                                    found[index - 1] = true;
                                    justfound = false;
                                }
                                else
                                    compatible = false;
                                if (!(compatible) || (found[0] && found[1] && found[2]))
                                    break;
                            } 
                            else
                                for(int i=0;i<numberOfStudents;i++) {
                                    if (inputLine.equals("<TR><TD ALIGN=LEFT>" + searchString[i] + "</TD>")) {
                                        justfound = true;
                                        index = (byte)(i+1);
                                        break;
                                    }
                                }
                            if(index==0)
                                justfound = false;
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
                                params.put("teamname", teamName.getText().toString());
                                params.put("entry1", entryNumber[0].getText().toString());
                                params.put("name1", studentName[0].getText().toString());
                                params.put("entry2", entryNumber[1].getText().toString());
                                params.put("name2", studentName[1].getText().toString());
                                //If the third member checkbox is unchecked then the information of third member is not sent
                                if (CBTM.isChecked()) {
                                    params.put("entry3", entryNumber[2].getText().toString());
                                    params.put("name3", studentName[2].getText().toString());
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

                            }
                            catch (Exception e) {
                                result = e.toString();
                            }
                            finally {
                                //For an already registered user
                                switch (result) {
                                    case "{\"RESPONSE_SUCCESS\": 0, \"RESPONSE_MESSAGE\": \"User already registered\"}":
                                        UserExistsDialogFragment existmsgbox = new UserExistsDialogFragment();
                                        existmsgbox.show(getFragmentManager(), "Already Exists");
                                        break;

                                    //Upon the successful addition of an entry
                                    case "{\"RESPONSE_SUCCESS\": 1, \"RESPONSE_MESSAGE\": \"Registration completed\"}":
                                        SuccMsgDialogFragment succmsgbox = new SuccMsgDialogFragment();
                                        succmsgbox.show(getFragmentManager(), "Successful Response");
                                        break;
                                    case "{\"RESPONSE_SUCCESS\": 0, \"RESPONSE_MESSAGE\": \"Data not posted!\"}":
                    /*
                        *This case should not arise in real
                        * Because this is the case when incomplete information is sent to the server
                        * But our pre-check after clicking the submit button would never allow to post such incomplete info.
                    */
                                        break;
                                    //Any other value of the string result implies a connection failure
                                    default:
                                        ConnFailDialogFragment connfailmsg = new ConnFailDialogFragment();
                                        connfailmsg.show(getFragmentManager(), "Connection Failed");
                                        break;
                                }
                            }
                        } else {
                            index = found[2] ? index : 3;
                            index = found[1] ? index : 2;
                            index = found[0] ? index : 1;
                            NotRegCourseDialogFragment notregdiafragment = new NotRegCourseDialogFragment();
                            notregdiafragment.show(getFragmentManager(), "Not registered for course");
                        }
                    }
                    catch (Exception e) {
                        ConnFailDialogFragment connfailmsg = new ConnFailDialogFragment();
                        connfailmsg.show(getFragmentManager(), "Connection Failed");
                    }
                }
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://spycoders.cop290registration/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://spycoders.cop290registration/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


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
                            entryNumber[index-1].requestFocus();
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
                            studentName[index-1].requestFocus();
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
                            entryNumber[index-1].requestFocus();
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
                            entryNumber[index-1].requestFocus();
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
                            studentName[0].requestFocus();
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
            builder.setMessage(getString(R.string.success_msg1) + teamName.getText().toString() + getString(R.string.success_msg2))
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
        for (int i = 0; i < l; i++) {
            char temp = s.charAt(i);
            if (temp < '0' || temp > '9')
                return false;
        }
        return true;
    }

    //Checks whether the characters are explicitly in (A-Z and 0-9)
    private boolean isAlphaNumeric(String s) {
        int l = s.length();
        for (int i = 0; i < l; i++) {
            char temp = s.charAt(i);
            if (!((temp >= 48 && temp <= 57) || (temp >= 65 && temp <= 90)))
                return false;
        }
        return true;
    }

    //Checks whether the characters are explicitly in (A-Z or a-z or ' ' or '.' )
    private boolean isAlpha(String s) {
        int l = s.length();
        for (int i = 0; i < l; i++) {
            char temp = s.charAt(i);
            if (!((temp >= 97 && temp <= 122) || (temp >= 65 && temp <= 90) || (temp == 32) || (temp == 46)))
                return false;
        }
        return true;
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