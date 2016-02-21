package spycoders.moodleplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeScreen_act extends AppCompatActivity {

    Button btn_notifications,btn_myCourses,btn_allGrades,btn_myProfile,btn_password
            ,btn_logout,btn_help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        String url="http://192.168.254.1:8000/default/notifications.json";
        btn_notifications=(Button)findViewById(R.id.btnNotifications);
        btn_myCourses=(Button) findViewById(R.id.btnMyCourses);
        btn_allGrades=(Button) findViewById(R.id.btnAllGrades);
        btn_myProfile=(Button)findViewById(R.id.btnMyProfile);
        btn_password=(Button)findViewById(R.id.btnPassword);
        btn_logout=(Button)findViewById(R.id.btnLogout);
        btn_help=(Button) findViewById(R.id.btnHelp);

        btn_notifications.setOnClickListener(onBut);
        btn_myCourses.setOnClickListener(onBut);
        btn_allGrades.setOnClickListener(onBut);
        btn_myProfile.setOnClickListener(onBut);
        btn_password.setOnClickListener(onBut);
        btn_logout.setOnClickListener(onBut);
        btn_help.setOnClickListener(onBut);
    }


    private View.OnClickListener onBut = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(HomeScreen_act.this,HomeScreen_act.class);
            boolean change=true;
            switch(v.getId())
            {

                case R.id.btnNotifications:
                    intent= new Intent(HomeScreen_act.this,Notifications_act.class);
                    break;
                case R.id.btnMyCourses:
                    intent= new Intent(HomeScreen_act.this,MyCourses_act.class);
                    break;
                case R.id.btnAllGrades:
                    intent= new Intent(HomeScreen_act.this,AllGrades_act.class);
                    break;
                default:change=false;
            }
            if (change)
            {
                finish();
                startActivity(intent);
            }
        }
    };

}
