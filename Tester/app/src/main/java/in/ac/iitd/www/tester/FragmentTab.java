package in.ac.iitd.www.tester;

/**
 * Created by Prabhu on 2/21/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentTab extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        String s=this.getTag();
        if(s.equals("About"))
        {
            v = inflater.inflate(R.layout.about_layout, container, false);
        }
        else if(s.equals("Assignments"))
        {
            v = inflater.inflate(R.layout.assignments_layout, container, false);
        }
        else if(s.equals("Grades"))
        {
            v = inflater.inflate(R.layout.grades_layout, container, false);
        }
        else
        {
            v = inflater.inflate(R.layout.threads_layout, container, false);
        }

        return v;
    }
}