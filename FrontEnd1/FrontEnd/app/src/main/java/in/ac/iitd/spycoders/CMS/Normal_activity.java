package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Normal_activity extends Base_activity {
    Button button1,button2,button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        navigationView.getMenu().clear(); //clear old inflated items.
        navigationView.inflateMenu(R.menu.menu_normal);
        navigationView.inflateMenu(R.menu.menu_others);

        LinearLayout base = (LinearLayout) findViewById(R.id.ll_base);
        View v = getLayoutInflater().inflate(R.layout.normal_layout, base, false);
        base.addView(v);
        button1 = (Button) findViewById(R.id.btn_norm_lodge);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Choose_Category_activity.class);
                startActivity(intent);
            }
        });
        button2 = (Button) findViewById(R.id.btn_norm_view);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (Login_activity.logged_mode == Login_activity.mode.normal) {
                    intent = new Intent(getBaseContext(), Normal_View_activity.class);
                    startActivity(intent);
                }
            }
        });
        button3 = (Button) findViewById(R.id.btn_norm_profile);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getBaseContext(),MyProfile_Activity.class);
                startActivity(intent);

            }
        });
    }
}
