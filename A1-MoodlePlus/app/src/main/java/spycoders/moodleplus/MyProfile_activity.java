package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyProfile_activity extends Base_activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout base=(LinearLayout) findViewById(R.id.ll_base);
        View v=getLayoutInflater().inflate(R.layout.activity_my_profile, base, false);
        base.addView(v);
        TextView txtView_profile[]= new TextView[7];
        for (int i=0;i<7;i++)
        {
            int id=0;
            String info="";
            switch(i)
            {
                case 0:
                    id=R.id.txtViewFirstNameVal;
                    info=Login_activity.logged_user.firstName;
                    break;
                case 1:
                    id=R.id.txtViewLastNameVal;
                    info=Login_activity.logged_user.lastName;
                    break;
                case 2:
                    id=R.id.txtViewEntryNoVal;
                    info=Login_activity.logged_user.entryNo;
                    break;
                case 3:
                    id=R.id.txtViewEmailVal;
                    info=Login_activity.logged_user.email;
                    break;
                case 4:
                    id=R.id.txtViewUserNameVal;
                    info=Login_activity.logged_user.username;
                    break;
                case 5:
                    id=R.id.txtViewUserIDVal;
                    info=String.valueOf(Login_activity.logged_user.User_ID);
                    break;
                case 6:
                    id=R.id.txtViewTypeVal;
                    info=Login_activity.logged_user.User_type==1?"Professor":"Student";
                    break;
            }
            txtView_profile[i]=(TextView) findViewById(id);
            txtView_profile[i].setText(info);
        }
    }
}
