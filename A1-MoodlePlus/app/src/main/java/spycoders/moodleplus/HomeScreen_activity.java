package spycoders.moodleplus;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomeScreen_activity extends Base_activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout base=(LinearLayout) findViewById(R.id.ll_base);
        View v=getLayoutInflater().inflate(R.layout.activity_home_screen, base, false);
        base.addView(v);

    }
}
