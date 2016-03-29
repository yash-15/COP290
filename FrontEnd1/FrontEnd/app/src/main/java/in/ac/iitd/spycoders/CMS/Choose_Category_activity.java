package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Choose_Category_activity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choose_category_layout);
        Button btn_lodge_ind = (Button) findViewById(R.id.btn_lodge_ind);
        btn_lodge_ind.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Choose_Category_activity.this, Choose_Authority_activity.class);
                Login_activity.lodge_var1=0;
                startActivity(intent);
            }
        });
        Button btn_lodge_hstl=(Button) findViewById(R.id.btn_lodge_hstl);
        btn_lodge_hstl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Choose_Category_activity.this, Choose_Authority_activity.class);
                Login_activity.lodge_var1=1;
                startActivity(intent);
            }
        });

        //A better way could be found out No net connection currently
        Button btn_lodge_insti=(Button) findViewById(R.id.btn_lodge_insti);
        btn_lodge_insti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Choose_Category_activity.this, Choose_Authority_activity.class);
                Login_activity.lodge_var1=2;
                startActivity(intent);
            }
        });

    }



}
