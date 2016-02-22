package spycoders.moodleplus;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Prabhu on 2/21/2016.
 */
public class AllGrades_layout {
    LinearLayout ll;
    TextView sl,courseCode,descr,grade,weight;

    AllGrades_layout(Context c, allgrades g)
    {
        ll=new LinearLayout(c);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params;

        sl=new TextView(c);
        courseCode=new TextView(c);
        descr=new TextView(c);
        grade=new TextView(c);
        weight=new TextView(c);

        sl.setTextSize(20);
        courseCode.setTextSize(20);
        descr.setTextSize(20);
        grade.setTextSize(20);
        weight.setTextSize(20);

        sl.setText(g.sl.toString());
        courseCode.setText(g.courseCode);
        descr.setText(g.descr);
        grade.setText(g.score.toString() + "/" + g.outOf.toString());
        weight.setText(g.weight.toString());

        sl.setTextColor(Color.parseColor("#000000"));
        courseCode.setTextColor(Color.parseColor("#000000"));
        descr.setTextColor(Color.parseColor("#000000"));
        grade.setTextColor(Color.parseColor("#000000"));
        weight.setTextColor(Color.parseColor("#000000"));
        //Source for unit conversions
       // http://stackoverflow.com/questions/3379973/how-to-programatically-set-the-width-of-an-android-edittext-view-in-dps-not-pix



        int unit = TypedValue.COMPLEX_UNIT_DIP;
        DisplayMetrics metrics = c.getResources().getDisplayMetrics();

        int dipPixel = (int)(TypedValue.applyDimension(unit, 30, metrics));
        params=new LinearLayout.LayoutParams(dipPixel,LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(sl,params);

        dipPixel = (int) (TypedValue.applyDimension(unit, 75, metrics));
        params = new LinearLayout.LayoutParams(dipPixel, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(courseCode, params);


        dipPixel = (int)(TypedValue.applyDimension(unit, 105, metrics));
        params=new LinearLayout.LayoutParams(dipPixel,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 0, 0);
        ll.addView(descr, params);

        dipPixel = (int)(TypedValue.applyDimension(unit, 75, metrics));
        params=new LinearLayout.LayoutParams(dipPixel,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 0, 0);
        ll.addView(grade, params);

        params=new LinearLayout.LayoutParams(dipPixel,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 0, 0);
        ll.addView(weight,params);

    }
}
