package in.ac.iitd.spycoders.CMS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Prabhu on 3/29/2016.
 */
public class log_layout {

    LinearLayout ll;
    TextView cid,action_taken,date,admin1,admin2;
    Button v_comp;

    log_layout(Context c,log a)
    {
        ll= new LinearLayout(c);
        cid=new TextView(c);
        action_taken=new TextView(c);
        date=new TextView(c);
        admin1=new TextView(c);
        admin2=new TextView(c);
        v_comp=new Button(c);
        ll.setBackgroundColor(Color.parseColor("#07000000"));


        cid.setText("Complaint_ID: " + String.valueOf(a.Complaint_ID));
        action_taken.setText("Action: " + a.action_taken);
        date.setText("Date_Time: " + a.mod_date);
        admin1.setText("Admin_primary: " + a.Admin1);
        admin2.setText("Admin_secondary: "+(a.Admin2==-1?"":a.Admin2));
        v_comp.setText("View Complaint >>");
        final Context t_c=c;
        final log t_a=a;
        v_comp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(t_c, View_Grp_Comp_activity.class);
                Login_activity.current_grp.id=t_a.Complaint_ID;
                Login_activity.logs=true;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                t_c.startActivity(intent);
            }
        });

        cid.setBackgroundColor(Color.parseColor("#07000000"));
        action_taken.setBackgroundColor(Color.parseColor("#07000000"));
        date.setBackgroundColor(Color.parseColor("#07000000"));
        admin1.setBackgroundColor(Color.parseColor("#07000000"));
        admin2.setBackgroundColor(Color.parseColor("#07000000"));
        v_comp.setBackgroundColor(Color.parseColor("#FFFFFF"));

        cid.setTextColor(Color.parseColor("#000000"));
        action_taken.setTextColor(Color.parseColor("#000000"));
        date.setTextColor(Color.parseColor("#000000"));
        admin1.setTextColor(Color.parseColor("#000000"));
        admin2.setTextColor(Color.parseColor("#000000"));
        v_comp.setTextColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.VERTICAL);

//        ll.setBackgroundColor(Color.parseColor("#ffffff"));
        ll.addView(cid, lp);
        ll.addView(action_taken, lp);
        ll.addView(date, lp);
        ll.addView(admin1,lp);
        ll.addView(admin2,lp);
        ll.addView(v_comp,lp);

    }
}
