package spycoders.moodleplus;



import android.content.Context;
import android.graphics.Color;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Prabhu on 2/23/2016.
 */
public class Ass_layout {

    LinearLayout ll;
    TextView id,name,time,deadline,late_all;
    WebView descr;

    Ass_layout(Context c,assignment a)
    {
        ll= new LinearLayout(c);
        id=new TextView(c);
        name=new TextView(c);
        time=new TextView(c);
        deadline=new TextView(c);
        late_all=new TextView(c);
        descr=new WebView(c);
        ll.setBackgroundColor(Color.parseColor("#07000000"));


        id.setText("ID: " + String.valueOf(a.id));
        
        name.setText("Name: "+a.name);
        time.setText("Starting from: " + a.time);
        deadline.setText("Deadline: " + a.deadline);
        late_all.setText("Late Days Allowed: " + a.late_all);

        id.setBackgroundColor(Color.parseColor("#07000000"));

        name.setBackgroundColor(Color.parseColor("#07000000"));
        time.setBackgroundColor(Color.parseColor("#07000000"));
        deadline.setBackgroundColor(Color.parseColor("#07000000"));
        late_all.setBackgroundColor(Color.parseColor("#07000000"));

        id.setTextColor(Color.parseColor("#000000"));
        name.setTextColor(Color.parseColor("#000000"));
        time.setTextColor(Color.parseColor("#000000"));
        deadline.setTextColor(Color.parseColor("#000000"));
        late_all.setTextColor(Color.parseColor("#000000"));

        descr.getSettings().setJavaScriptEnabled(true);
        descr.loadDataWithBaseURL("", "<p><u>Details of Assignment</u></p>" + a.descr, "text/html", "UTF-8", "");
        descr.setBackgroundColor(Color.parseColor("#07000000"));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams
                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setOrientation(LinearLayout.VERTICAL);

//        ll.setBackgroundColor(Color.parseColor("#ffffff"));
        ll.addView(id, lp);
        ll.addView(name, lp);
        ll.addView(time, lp);
        ll.addView(deadline, lp);
        ll.addView(late_all, lp);lp = new LinearLayout.LayoutParams
            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(descr,lp);

    }
}
