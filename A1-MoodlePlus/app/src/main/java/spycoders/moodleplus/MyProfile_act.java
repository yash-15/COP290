package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MyProfile_act extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        TextView txtView_profile[]= new TextView[7];
        for (int i=0;i<7;i++)
        {
            int id=0;
            String info="";
            switch(i)
            {
                case 0:
                    id=R.id.txtViewFirstNameVal;
                    info=Login_act.logged_user.firstName;
                    break;
                case 1:
                    id=R.id.txtViewLastNameVal;
                    info=Login_act.logged_user.lastName;
                    break;
                case 2:
                    id=R.id.txtViewEntryNoVal;
                    info=Login_act.logged_user.entryNo;
                    break;
                case 3:
                    id=R.id.txtViewEmailVal;
                    info=Login_act.logged_user.email;
                    break;
                case 4:
                    id=R.id.txtViewUserNameVal;
                    info=Login_act.logged_user.username;
                    break;
                case 5:
                    id=R.id.txtViewUserIDVal;
                    info=String.valueOf(Login_act.logged_user.User_ID);
                    break;
                case 6:
                    id=R.id.txtViewTypeVal;
                    info=Login_act.logged_user.User_type==1?"Professor":"Student";
                    break;
            }
            txtView_profile[i]=(TextView) findViewById(id);
            txtView_profile[i].setText(info);
        }
    }
}
