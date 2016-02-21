package spycoders.moodleplus;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by Prabhu on 2/21/2016.
 */
public class Notifications_layout {
    LinearLayout ll;
    TextView time,postedBy,courseCode;
    Button btnView;
    //Color code - #00bfff for new notification  #c0fff4 for old
    //TextView
    Notifications_layout(Context c, notif n)
    {
        ll=new LinearLayout(c);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        time=new TextView(c);
        postedBy=new TextView(c);
        courseCode=new TextView(c);

        time.setText("Posted At: "+n.time_created);
        postedBy.setText("Posted By: "+n.postedBy);
        courseCode.setText("Course Code: "+n.CourseCode);

        btnView = new Button(c);
        btnView.setText("View>>");
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll.setBackgroundColor(n.seen ? Color.parseColor("#c0fff4") : Color.parseColor("#00bfff"));
        ll.addView(time, params);
        ll.addView(postedBy,params);
        ll.addView(courseCode,params);
        params.gravity= Gravity.RIGHT;
        ll.addView(btnView,params);
    }
}
