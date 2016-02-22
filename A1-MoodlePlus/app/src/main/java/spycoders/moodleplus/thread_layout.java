package spycoders.moodleplus;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Prabhu on 2/22/2016.
 */
public class thread_layout {

    LinearLayout ll;
    TextView id,createdAt,updatedAt,title,descr;
    Button btnViewComments;
    thread_layout(Context c, thread t)
    {
        ll=new LinearLayout(c);
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        id=new TextView(c);
        createdAt=new TextView(c);
        updatedAt=new TextView(c);
        title=new TextView(c);
        descr=new TextView(c);

        id.setText("Thread ID: "+t.id);
        createdAt.setText("Created At: "+t.created_at);
        updatedAt.setText("Updated At: "+t.updated_at);
        title.setText("Title: "+t.title);
        descr.setText("Description: \n"+t.description);

        btnViewComments = new Button(c);
        btnViewComments.setText("View Comments>>");
        btnViewComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll.setBackgroundColor(Color.parseColor("#00bfff"));
        ll.addView(id, params);
        ll.addView(createdAt,params);
        ll.addView(updatedAt,params);
        ll.addView(title,params);
        ll.addView(descr,params);
        params.gravity= Gravity.RIGHT;
        ll.addView(btnViewComments,params);
    }
}
