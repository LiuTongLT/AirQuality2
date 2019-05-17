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
        else if(id == R.id.report_refresh){
            Toast.makeText(NewReport.this,"Not implemented yet",Toast.LENGTH_SHORT).show();}
        //menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        return true;
    }
}
