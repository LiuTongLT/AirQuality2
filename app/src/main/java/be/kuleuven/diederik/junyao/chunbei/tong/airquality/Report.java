package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.lang.*;

public class Report extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Data data = new Data();
    private User user;
    private ArrayList<Measurement> measurements=new ArrayList<Measurement>();
    private Sensor sensor;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        data=(Data) intent.getSerializableExtra("data");
        user = (User) intent.getSerializableExtra("user");
        sensor =(Sensor) intent.getSerializableExtra("sensor");
        System.out.println("Location: " + sensor.getLocation());
        String location = (String) intent.getSerializableExtra("location");
        try{
            if(location!=null){
                measurements=data.getMeasurementsByLocation(location);
            }
            else if(sensor!=null){
                measurements=data.getMeasurementsByLocation(sensor.getLocation());
            }
            Collections.sort(measurements, new Comparator<Measurement>() {
                @Override
                public int compare(Measurement m1, Measurement m2) {
                    return (m1.getDay()-m2.getDay());
                }
            });
        }
        catch(EmptyListException E){}

        if(measurements.size()!=0){
            DataPoint[] pm = new DataPoint[measurements.size()];
            for(int i = 0; i<measurements.size(); i++){
                double day=measurements.get(i).getDay();
                double pmValue=measurements.get(i).getPmValue();
                DataPoint datapointPm = new DataPoint(day,pmValue);
                pm[i] = datapointPm;
                System.out.println(pm[i]);
            }

            GraphView graph = (GraphView) findViewById(R.id.graphPM);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pm);
            graph.addSeries(series);
        }

        if(measurements.size()!=0){
            DataPoint[] co = new DataPoint[measurements.size()];
            for(int i = 0; i<measurements.size(); i++){
                double day=measurements.get(i).getDay();
                double coValue=measurements.get(i).getCoValue();
                DataPoint datapointCo = new DataPoint(day,coValue);
                co[i] = datapointCo;
                System.out.println(co[i]);
            }

            GraphView graph = (GraphView) findViewById(R.id.graphCO);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(co);
            graph.addSeries(series);
        }

        drawerLayout = findViewById(R.id.report_drawer_layout);
        NavigationView navigationView = findViewById(R.id.report_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        Intent intent;
        int id = menuItem.getItemId();
        if (id == R.id.report_to_map) {
            intent = new Intent(this,SensorMap.class);
            intent.putExtra("data",data);
            intent.putExtra("user",user);
            startActivity(intent);
            finish();
        }else if(id == R.id.report_back_to_menu){
            intent = new Intent(this,Menu.class);
            intent.putExtra("data",data);
            intent.putExtra("user",user);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.report_refresh){
            Toast.makeText(Report.this,"Not implemented yet",Toast.LENGTH_SHORT).show();}
        //menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}
