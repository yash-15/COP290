package spycoders.moodleplus;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Window;
import android.widget.TextView;

public class Course_Page_act extends FragmentActivity {
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_course__page);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

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



    }
}