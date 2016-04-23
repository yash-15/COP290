package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Solver_Admin_activity extends Base_activity {

    Button btnView,btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.getMenu().clear(); //clear old inflated items.
        navigationView.inflateMenu(R.menu.menu_solver_admin);
        navigationView.inflateMenu(R.menu.menu_others);

        LinearLayout base = (LinearLayout) findViewById(R.id.ll_base);
        View v = getLayoutInflater().inflate(R.layout.solver_admin_layout, base, false);
        base.addView(v);

        btnView=(Button) findViewById(R.id.btn_norm_view);
        btnProfile=(Button) findViewById(R.id.btn_norm_profile);




        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getBaseContext(), Normal_View_activity.class);
                startActivity(intent);
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (Login_activity.logged_mode== Login_activity.mode.solver) {
                    intent = new Intent(getBaseContext(), Solver_Profile_activity.class);
                }
                else
                {
                    intent=new Intent(getBaseContext(), Admin_Profile_activity.class);
                }
                startActivity(intent);

            }
        });


    }



}