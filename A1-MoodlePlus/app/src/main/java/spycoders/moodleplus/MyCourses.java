package spycoders.moodleplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyCourses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);
        String url="http://192.168.254.1:8000/default/notifications.json";

    }
}
