package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class category_2 extends category {

    private Button cat_1;
    private Button cat_2;
    private Button cat_3;
    private Button cat_4;
    private Button cat_5;
    private Button cat_6;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category2);

        cat_1 = (Button) findViewById(R.id.category_1);
        cat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Complaint_Form_activity.class);
                startActivity(intent);
            }
        });
        cat_2 = (Button) findViewById(R.id.category_2);
        cat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Complaint_Form_activity.class);
                startActivity(intent);
            }
        });
        cat_3 = (Button) findViewById(R.id.category_3);
        cat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Complaint_Form_activity.class);
                startActivity(intent);
            }
        });
        cat_4 = (Button) findViewById(R.id.category_4);
        cat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Complaint_Form_activity.class);
                startActivity(intent);
            }
        });
        cat_5 = (Button) findViewById(R.id.category_5);
        cat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Complaint_Form_activity.class);
                startActivity(intent);
            }
        });
        cat_6 = (Button) findViewById(R.id.category_6);
        cat_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Complaint_Form_activity.class);
                startActivity(intent);
            }
        });
    }
}
