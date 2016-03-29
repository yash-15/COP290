package in.ac.iitd.spycoders.CMS;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Admin_activity extends Base_activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationView.getMenu().clear(); //clear old inflated items.
        navigationView.inflateMenu(R.menu.menu_solver_admin);
        navigationView.inflateMenu(R.menu.menu_others);

        LinearLayout base = (LinearLayout) findViewById(R.id.ll_base);
        View v = getLayoutInflater().inflate(R.layout.solver_admin_layout, base, false);
        base.addView(v);

    }



}