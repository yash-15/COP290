package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import in.ac.iitd.spycoders.CMS.R;

public class category extends AppCompatActivity {

    private Button Ins;
    private Button Hos;
    private Button Ind;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category);
        Ins = (Button) findViewById(R.id.institute_button);
        Ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), category_2.class);
                startActivity(intent);


            }
        });
        Hos = (Button) findViewById(R.id.hostel_button);
        Hos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), category_2.class);
                startActivity(intent);



            }
        });
        Ind = (Button) findViewById(R.id.individual_button);
        Ind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), category_2.class);
                startActivity(intent);



            }
        });
    }
}
