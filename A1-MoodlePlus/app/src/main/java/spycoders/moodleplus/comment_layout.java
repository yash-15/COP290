package spycoders.moodleplus;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Prabhu on 2/22/2016.
 */
public class comment_layout {

    LinearLayout ll;
    TextView comment_id,commentator,commentator_ID,created_at,descr;

    comment_layout(Context c,comment cm){
    ll=new LinearLayout(c);


    //Alternatively we can use arrays
    comment_id=new TextView(c);
    commentator=new TextView(c);
    created_at=new TextView(c);
    commentator_ID=new TextView(c);
    descr=new TextView(c);


   comment_id.setText("Comment ID: "+cm.id);
    commentator.setText("Commentator: "+cm.commentator);
    created_at.setText("Created At: "+cm.created_at);
    commentator_ID.setText("Commentator ID: "+cm.user_id);
    descr.setText("Description: \n" + cm.description);

    LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    ll.setOrientation(LinearLayout.VERTICAL);
    ll.addView(comment_id,params);
    ll.addView(commentator_ID,params);
    ll.addView(commentator,params);
    ll.addView(created_at,params);
    ll.addView(descr,params);}
}
