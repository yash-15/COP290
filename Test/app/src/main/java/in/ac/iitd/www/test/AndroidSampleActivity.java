package in.ac.iitd.www.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;




public class AndroidSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Mike Testing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button button_1 = (Button) findViewById(R.id.button_1);
        button_1.setOnClickListener(onBut);



    }
HttpClient httpClient = new DefaultHttpClient();


    private View.OnClickListener onBut=new View.OnClickListener() {
        public void onClick(View v) {   
            EditText name1=(EditText)findViewById(R.id.text_name);
            String a=new String();
            a=name1.getText().toString();
            if (a.equals("Prabhu Prasad")) {
                name1.setText("Name1");
            }
            else if (a.equals("Name1")){
                name1.setText("Prabhu Prasad");
            }
        }
    };

   // @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_android_sample, menu);
        return true;
    }

    //@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
