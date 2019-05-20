package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewReport extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;

    private Data data = new Data();
    private User user;
    private Sensor sensor;
    private Data timingData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        Intent intent = getIntent();
        data=(Data) intent.getSerializableExtra("data");
        user = (User) intent.getSerializableExtra("user");
        sensor =(Sensor) intent.getSerializableExtra("sensor");
        timingData = (Data) intent.getSerializableExtra("timingData");

        tabLayout=(TabLayout) findViewById(R.id.tabLayoutId);
        viewPager=(ViewPager)findViewById(R.id.viewPagerId);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ReportDays(),"Last 7 days");
        viewPagerAdapter.addFragment(new ReportTiming(),"Real time trend");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout = findViewById(R.id.report_drawer_layout);
        NavigationView navigationView = findViewById(R.id.report_nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener(){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}
            @Override
            public void onDrawerOpened(View drawerView) {}
            @Override
            public void onDrawerClosed(View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {}
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        int id = menuItem.getItemId();
        if (id == R.id.report_to_map) {
            intent = new Intent(this,SensorMap.class);
            intent.putExtra("data",data);
            intent.putExtra("user",user);
            intent.putExtra("timingData",timingData);
            startActivity(intent);
            finish();
        }else if(id == R.id.report_back_to_menu){
            intent = new Intent(this,Menu.class);
            intent.putExtra("data",data);
            intent.putExtra("user",user);
            intent.putExtra("timingData",timingData);
            startActivity(intent);
            finish();
        }
//        else if(id == R.id.report_refresh){
//            getTimingMeasurements(sensor.getLocation());
//        }
        else if(id == R.id.report_refresh){
            Toast.makeText(NewReport.this,"Not implemented yet",Toast.LENGTH_SHORT).show();}
        //menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }

    public void getTimingMeasurements(String location) {

        final String loc=location;

        String url = "https://a18ee5air2.studev.groept.be/query/readTwenty.php?location="+location;
        RequestQueue queue = Volley.newRequestQueue(NewReport.this);

        System.out.println("Get timing measurements starts");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Into response! timing");
                            JSONArray jarr = new JSONArray(response);
                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject jobj = jarr.getJSONObject(i);
                                String time = jobj.getString("timeStamps");
                                Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time);
                                Double pm_value = jobj.getDouble("valuePM");
                                Double co_value = jobj.getDouble("valueCO");

                                try{
                                    timingData.addMeasurement(new Measurement(co_value,pm_value,date,loc));}
                                catch(AlreadyAddedException A){
                                    Toast.makeText(NewReport.this,"Add measurement failed!",Toast.LENGTH_SHORT).show();
                                }

                                System.out.println("Date: " + date);
                                System.out.println("PM: " + pm_value);
                                System.out.println("CO: " + co_value);
                            }
                            System.out.println("End of response! timing");

                        } catch (JSONException e) {
                            System.out.println(e);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewReport.this, "Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

}
