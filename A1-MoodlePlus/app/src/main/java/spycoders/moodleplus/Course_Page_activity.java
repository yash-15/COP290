package spycoders.moodleplus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

public class Course_Page_activity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    public static  boolean setCourseData=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_course__page);

        String select_thread="false";
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                select_thread= "false";
            } else {
                select_thread= extras.getString("THREADS");
                if (select_thread.equals("true"))
                {

                    setCourseData=true;
                }
            }
        }

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.setBackgroundColor(Color.parseColor("#FFC400"));

        mTabHost.addTab(
                mTabHost.newTabSpec("About").setIndicator("About", null),
                FragmentTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("Assignments").setIndicator("Assignments", null),
                FragmentTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("Grades").setIndicator("Grades", null),
                FragmentTab.class, null);
        mTabHost.addTab(
                mTabHost.newTabSpec("Threads").setIndicator("Threads", null),
                FragmentTab.class, null);

        if (select_thread.equals("true"))
        {
            mTabHost.setCurrentTab(3);
            select_thread="false";
        }





    }
}
