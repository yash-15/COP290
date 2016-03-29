package in.ac.iitd.spycoders.CMS;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class Base_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    MenuItem temp;boolean logout=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set the name, mode  and email of the logged_user
        View headerView = navigationView.getHeaderView(0);

        TextView txtName=(TextView) headerView.findViewById(R.id.txtName);
        TextView txtMode=(TextView) headerView.findViewById(R.id.txtMode);
        TextView txtEmail=(TextView) headerView.findViewById(R.id.txtEmail);

        txtName.setText(Login_activity.logged_user.first_name+" "+Login_activity.logged_user.last_name);
        txtEmail.setText(Login_activity.logged_user.email);
        if (Login_activity.logged_mode== Login_activity.mode.admin)
        {
            txtMode.setText(Login_activity.logged_admin.post);
        }
        else  if (Login_activity.logged_mode== Login_activity.mode.solver)
        {
            txtMode.setText(Login_activity.logged_solver.Department);
        }
        else
        {
            txtMode.setText("Normal User");
        }

        temp=(MenuItem) findViewById(R.id.action_logout);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id;
        try {
            id = item.getItemId();
        }
        catch(Exception e)
        {
            id=logout?R.id.action_logout:R.id.action_switch_mode;
        }
        final Toast toast=Toast.makeText(getBaseContext(),"Menu Click", Toast.LENGTH_SHORT);

        switch(id){
            case R.id.action_switch_mode:
                Intent intent=new Intent(getBaseContext(),Mode_Choose_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //clear history of pages
                startActivity(intent);
                toast.setText("Now you can change the mode of operation!");
                toast.show();
                break;
            case R.id.action_logout:
                String api="http://"+Login_activity.ip+":"+Login_activity.port+Login_activity.extras+"/default/logout.json";

                JsonObjectRequest jsObjRequest_logout = new JsonObjectRequest
                        (Request.Method.GET, api, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response.toString());
                                toast.setText("Logged Out Successfully!!");
                                toast.show();
                                Intent intent_logout=new Intent(getBaseContext(),Login_activity.class);
                                intent_logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear history of pages
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
        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        final Toast toast=Toast.makeText(getApplicationContext(), "Welcome To CMS", Toast.LENGTH_SHORT);
        switch(id)
        {
            case R.id.mitem_lodge:
                Intent intent;
                intent=new Intent(getBaseContext(),Choose_Category_activity.class);
                startActivity(intent);
                break;
            case R.id.mitem_view:

                if (Login_activity.logged_mode==Login_activity.mode.normal)
                {
                    intent=new Intent(getBaseContext(),Normal_View_activity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mitem_profile:
                intent = new Intent(getBaseContext(),MyProfile_Activity.class);
                startActivity(intent);
                toast.setText("Your Profile");

                break;
            case R.id.mitem_password:
                toast.setText("Functionality not available");
                break;
            case R.id.mitem_switch:
                logout=false;
                onOptionsItemSelected(temp);
                break;
            case R.id.mitem_logout:
                logout=true;
                onOptionsItemSelected(temp);
                break;
            case R.id.mitem_help:
                toast.setText("Functionality not available");
                break;
            default:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
