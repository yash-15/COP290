package spycoders.moodleplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Base_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set the name and entry number of the logged_user
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_nav_base);

        TextView txtNavUserName=(TextView) headerView.findViewById(R.id.txtNavUserName);
        TextView txtNavEmail=(TextView) headerView.findViewById(R.id.txtNavEmail);

        txtNavUserName.setText(Login_activity.logged_user.firstName+" "+Login_activity.logged_user.lastName);
        txtNavEmail.setText(Login_activity.logged_user.email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent=new Intent(getBaseContext(),HomeScreen_activity.class);
        final Toast toast=Toast.makeText(getApplicationContext(), "Moodle Plus", Toast.LENGTH_SHORT);

        boolean change1=true,change2=true;
        //change1-toast show change2-start intent
        switch(id)
        {
            case R.id.menu_home:
                toast.setText("Home Page");
                break;
            case R.id.menu_notifications:
                intent=new Intent(getBaseContext(),Notifications_activity.class);
                toast.setText("Notifications");
                break;
            case R.id.menu_courses:
                intent=new Intent(getBaseContext(),MyCourses_activity.class);
                toast.setText("View all your courses");
                break;
            case R.id.menu_allGrades:
                intent=new Intent(getBaseContext(),AllGrades_activity.class);
                toast.setText("Check all your grades");
                break;
            case R.id.menu_profile:
                intent=new Intent(getBaseContext(),MyProfile_activity.class);
                toast.setText("Your Profile");
                break;
            case R.id.menu_password:
                toast.setText("Functionality not available");
                change2=false;
                break;
            case R.id.menu_logout:
                change1=false;change2=false;
                String api="http://"+Login_activity.ip+":"+Login_activity.port+"/default/logout.json";

                JsonObjectRequest jsObjRequest_logout = new JsonObjectRequest
                        (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response.toString());
                                toast.setText("Logged Out Successfully!!");
                                toast.show();
                                    /*
                                    *There are difficulties in using the above defined intent
                                    *Why I have written it in so inefficient way?
                                    *2 reasons:
                                    * We can't change a variable(here intent) that is not final within a class
                                    * I can't make intent final because it's value needs to be changed depending on the context
                                    * Secondly, On adding to a Request Queue, Surprisingly the lines of code following it seem
                                    * to be executed prior to the execution of the request
                                    * May be this is a feature of volley
                                    */
                                Intent intent_logout=new Intent(getBaseContext(),Login_activity.class);
                                intent_logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent_logout);
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                toast.setText("Can't logout: Check your network connection!");
                                toast.show();
                            }
                        });
                Login_activity.queue.add(jsObjRequest_logout);


                break;
            case R.id.menu_help:
                toast.setText("Functionality not available");
                change2=false;
                break;
            default:change1=false;change2=false;
        }

        if (change1) {toast.show();}
        if (change2) {startActivity(intent);}
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
