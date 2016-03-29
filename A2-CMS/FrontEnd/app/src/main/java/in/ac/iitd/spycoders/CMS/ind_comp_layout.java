package in.ac.iitd.spycoders.CMS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Prabhu on 3/29/2016.
 */
public class ind_comp_layout {

    LinearLayout ll;
    TextView cid,title,date;
    Button details;

    ind_comp_layout(Context c,ind_comp a)
    {
        ll= new LinearLayout(c);
        cid=new TextView(c);
        title=new TextView(c);
        date=new TextView(c);
        details=new Button(c);
        ll.setBackgroundColor(Color.parseColor("#07000000"));


        cid.setText("Complaint_ID: " + String.valueOf(a.id));

        title.setText("Title: " + a.Title);
        date.setText("Complained On: " + a.Reg_Date);
        details.setText("Details >>");
        final Context t_c=c;
        final ind_comp t_a=a;
        details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(t_c, View_Ind_Comp_activity.class);
                Login_activity.current_ind=t_a;
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                t_c.startActivity(intent);
            }
        });

        cid.setBackgroundColor(Color.parseColor("#07000000"));

        title.setBackgroundColor(Color.parseColor("#07000000"));
        date.setBackgroundColor(Color.parseColor("#07000000"));
        details.setBackgroundColor(Color.parseColor("#FFFFFF"));

        cid.setTextColor(Color.parseColor("#000000"));
        title.setTextColor(Color.parseColor("#000000"));
        date.setTextColor(Color.parseColor("#000000"));
        details.setTextColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.VERTICAL);

//        ll.setBackgroundColor(Color.parseColor("#ffffff"));
        ll.addView(cid, lp);
        ll.addView(title, lp);
        ll.addView(date, lp);
        ll.addView(details,lp);

    }
}
