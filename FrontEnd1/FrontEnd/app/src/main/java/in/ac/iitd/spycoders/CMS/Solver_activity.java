package in.ac.iitd.spycoders.CMS;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Solver_activity extends Base_activity {

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
