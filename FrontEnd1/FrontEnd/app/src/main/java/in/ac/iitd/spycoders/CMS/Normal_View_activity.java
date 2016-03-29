package in.ac.iitd.spycoders.CMS;

import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Normal_View_activity extends AppCompatActivity {

    private FragmentTabHost mTabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_view_layout);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.setBackgroundColor(Color.parseColor("#3F51B5"));

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
