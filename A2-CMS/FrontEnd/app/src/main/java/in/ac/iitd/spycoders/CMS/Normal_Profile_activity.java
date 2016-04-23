package in.ac.iitd.spycoders.CMS;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Prabhu on 3/29/2016.
 */
public class Normal_Profile_activity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.normal_profile_layout);

        TextView txtf_name=(TextView) findViewById(R.id.txtViewFirstNameVal);
        TextView txtl_name=(TextView) findViewById(R.id.txtViewLastNameVal);
        TextView txtlocality=(TextView) findViewById(R.id.txtViewLocalityVal);
        TextView txtusername=(TextView) findViewById(R.id.txtViewUserNameVal);
        TextView txtemail=(TextView) findViewById(R.id.txtViewEmailVal);
        TextView txtuserid=(TextView) findViewById(R.id.txtViewUserIDVal);
        TextView txtaddress=(TextView) findViewById(R.id.txtViewAddressVal);
        TextView txtmobile=(TextView) findViewById(R.id.txtViewMobileVal);
        User temp=Login_activity.logged_user;
        txtf_name.setText(temp.first_name);
        txtl_name.setText(temp.last_name);
        txtlocality.setText(String.valueOf(temp.locality));
        txtusername.setText(temp.username);
        txtemail.setText(temp.email);
        txtuserid.setText(String.valueOf(temp.id));
        txtaddress.setText(temp.address);
        txtmobile.setText(String.valueOf(temp.mobile));



    }
}

