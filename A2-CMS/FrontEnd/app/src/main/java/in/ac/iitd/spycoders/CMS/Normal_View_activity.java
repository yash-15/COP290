package in.ac.iitd.spycoders.CMS;

import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Normal_View_activity extends AppCompatActivity {

    public static FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_view_layout);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.setBackgroundColor(Color.parseColor("#3F51B5"));

        if (Login_activity.logged_mode==Login_activity.mode.admin)
        {
            mTabHost.addTab(
                    mTabHost.newTabSpec("Approved").setIndicator("Approved",null),
                    Normal_View_fragment.class,null);
            mTabHost.addTab(
                    mTabHost.newTabSpec("Pending").setIndicator("Pending", null),
                    Normal_View_fragment.class,null);
            mTabHost.addTab(
                    mTabHost.newTabSpec("Unread").setIndicator("Unread",null),
                    Normal_View_fragment.class,null);
            mTabHost.addTab(
                    mTabHost.newTabSpec("Logs").setIndicator("Logs",null),
                    Normal_View_fragment.class,null);
        }
        else if (Login_activity.logged_mode==Login_activity.mode.solver)
        {
            mTabHost.addTab(
                    mTabHost.newTabSpec("S_Individual").setIndicator("Individual",null),
                    Normal_View_fragment.class,null);
            mTabHost.addTab(
                    mTabHost.newTabSpec("Group").setIndicator("Group",null),
                    Normal_View_fragment.class,null);
            mTabHost.addTab(
                    mTabHost.newTabSpec("Resolved").setIndicator("Resolved",null),
                    Normal_View_fragment.class,null);
        }
        else {
            mTabHost.addTab(
                    mTabHost.newTabSpec("Individual").setIndicator("Individual", null),
                    Normal_View_fragment.class, null);
            mTabHost.addTab(
                    mTabHost.newTabSpec("Locality").setIndicator("Locality", null),
                    Normal_View_fragment.class, null);
            mTabHost.addTab(
                    mTabHost.newTabSpec("Institute").setIndicator("Institute", null),
                    Normal_View_fragment.class, null);
        }
    }
}
