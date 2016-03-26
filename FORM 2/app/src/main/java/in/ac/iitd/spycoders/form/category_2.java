package in.ac.iitd.spycoders.form;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by sauhardgupta on 25/03/16.
 */
public class category_2 extends category {

    Button cat_1;
    Button cat_2;
    Button cat_3;
    Button cat_4;
    Button cat_5;
    Button cat_6;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.category2);

        cat_1 = (Button) findViewById(R.id.category_1);
        cat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), complaint_form.class);
                startActivity(intent);
            }
        });
        cat_2 = (Button) findViewById(R.id.category_2);
        cat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), complaint_form.class);
                startActivity(intent);
            }
        });
        cat_3 = (Button) findViewById(R.id.category_3);
        cat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), complaint_form.class);
                startActivity(intent);
            }
        });
        cat_4 = (Button) findViewById(R.id.category_4);
        cat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), complaint_form.class);
                startActivity(intent);
            }
        });
        cat_5 = (Button) findViewById(R.id.category_5);
        cat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), complaint_form.class);
                startActivity(intent);
            }
        });
        cat_6 = (Button) findViewById(R.id.category_6);
        cat_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), complaint_form.class);
                startActivity(intent);
            }
        });
    }
}
