package in.ac.iitd.www.testing;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Prabhu on 2/21/2016.
 */
public class Notif_layout {
    LinearLayout ll;
    TextView v1,v2,v3;
    //Color code - #00bfff for new notification  #c0fff4 for old
    //TextView
    Notif_layout(Context c)
    {
        ll=new LinearLayout(c);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        v1=new TextView(c);
        v2=new TextView(c);
        v3=new TextView(c);
        v1.setText("TV1");
        v2.setText("TV2");
        v3.setText("TV3");
        ll.addView(v1, params);
        ll.addView(v2,params);
        ll.addView(v3,params);
    }
}
