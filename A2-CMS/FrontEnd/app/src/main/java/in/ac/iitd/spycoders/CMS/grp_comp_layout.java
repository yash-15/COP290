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
public class grp_comp_layout {
    LinearLayout ll;
    TextView cid,title,date,cur_admin;
    Button details;

    grp_comp_layout(Context c, grp_comp a,boolean insti)
    {
        ll= new LinearLayout(c);
        cid=new TextView(c);
        title=new TextView(c);
        date=new TextView(c);
        cur_admin=new TextView(c);
        details=new Button(c);
        ll.setBackgroundColor(Color.parseColor("#07000000"));


        cid.setText("Complaint_ID: " + String.valueOf(a.id));

        title.setText("Title: " + a.Title);
        date.setText("Complained On: " + a.Reg_Date);
        cur_admin.setText("Current Admin ID: "+a.Cur_Admin_ID);
        details.setText("Details >>");
        final Context t_c=c;
        final grp_comp t_a=a;
        final boolean t_insti=insti;
        details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(t_c, View_Grp_Comp_activity.class);
                Login_activity.current_grp=t_a;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("INSTI",t_insti?"true":"false");
                t_c.startActivity(intent);
            }
        });

        cid.setBackgroundColor(Color.parseColor("#07000000"));

        title.setBackgroundColor(Color.parseColor("#07000000"));
        date.setBackgroundColor(Color.parseColor("#07000000"));
        cur_admin.setBackgroundColor(Color.parseColor("#07000000"));
        details.setBackgroundColor(Color.parseColor("#FFFFFF"));

        cid.setTextColor(Color.parseColor("#000000"));
        title.setTextColor(Color.parseColor("#000000"));
        date.setTextColor(Color.parseColor("#000000"));
        cur_admin.setTextColor(Color.parseColor("#000000"));
        details.setTextColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.VERTICAL);

//        ll.setBackgroundColor(Color.parseColor("#ffffff"));
        ll.addView(cid, lp);
        ll.addView(title, lp);
        ll.addView(date, lp);
        ll.addView(cur_admin,lp);
        ll.addView(details,lp);

    }
}
