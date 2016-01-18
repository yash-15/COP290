package spycoders.cop290registration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    EditText name1txt, name2txt, name3txt, entrynumber1txt, entrynumber2txt, entrynumber3txt, teamNametxt;
    public CheckBox CBTM;
    //View t;

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
                String result=new String("");
                for ( int c = in.read(); c != -1; c = in.read() )
                   result=result.concat(Character.toString((char)c));
                //The String result contains the result of the submission.
                //Proper notifications to be added later.
            }
            catch(Exception e){System.out.println(e.toString());}



        }
    };

    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

