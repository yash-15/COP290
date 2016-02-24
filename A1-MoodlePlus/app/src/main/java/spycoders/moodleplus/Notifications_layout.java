package spycoders.moodleplus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Prabhu on 2/23/2016.
 */
public class Notifications_layout {

    LinearLayout ll;
    TextView time,postedBy,courseCode;
    Button btnView;
    //Color code - #F57F17 for new notification  #F9A825 for old
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


        final Context c_temp=c;
        final notif n_temp=n;
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(c_temp,Course_Page_activity.class);
                intent.putExtra("THREADS","true");
                Login_activity.current_course.code=n_temp.CourseCode;
                c_temp.startActivity(intent);
            }
        });
        ll.setBackgroundColor(n.seen ? Color.parseColor("#F9A825") : Color.parseColor("#F57F17"));
        ll.addView(time, params);
        ll.addView(postedBy,params);
        ll.addView(courseCode,params);
        params.gravity= Gravity.LEFT;
        ll.addView(btnView,params);
    }
}
