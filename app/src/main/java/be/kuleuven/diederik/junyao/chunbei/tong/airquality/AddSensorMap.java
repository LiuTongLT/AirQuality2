package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddSensorMap

        extends
        FragmentActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private TextView latitude;
    private TextView longitude;
    private LatLng cameraPostition;
    private Button nextStep;
    private DrawerLayout drawerLayout;
    private Data data;
    private Data timingData;
    private User user;
    private Measurement currentGT;
    private Measurement currentAG;
    private LatLng sensorPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        latitude = findViewById(R.id.add_sensor_map_latitude);
        longitude = findViewById(R.id.add_sensor_map_longitude);
        nextStep = findViewById(R.id.add_sensor_map_nextStep);
        nextStep.setOnClickListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        double lat = (double) intent.getSerializableExtra("latitude");
        double lng = (double) intent.getSerializableExtra("longitude");
        user = (User) intent.getSerializableExtra("user");
        data = (Data) intent.getSerializableExtra("data");
        timingData = (Data) intent.getSerializableExtra("timingData");
        currentGT = (Measurement)intent.getSerializableExtra("currentGT");
        currentAG = (Measurement) intent.getSerializableExtra("currentAG");
        cameraPostition = new LatLng(lat, lng);

        System.out.println("user first: "+ user.getFirstName());

        drawerLayout = findViewById(R.id.add_sensor_map_drawer_layout);

        NavigationView navigationView = findViewById(R.id.add_sensor_map_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPostition, 17.0f), 1000, null);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Here a new sensor?").draggable(true));

        latitude.setText("Latitude: " + latLng.latitude);
        longitude.setText("Longitude: " + latLng.longitude);

        sensorPosition = latLng;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_sensor_map_nextStep:
                if (sensorPosition != null) {
                    Intent intent = new Intent(AddSensorMap.this, AddSensorInfo.class);
                    intent.putExtra("latitude", sensorPosition.latitude);
                    intent.putExtra("longitude", sensorPosition.longitude);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.add_sensor_map_to_map) {
            Intent intent = new Intent(AddSensorMap.this, Menu.class);
            intent.putExtra("data", data);
            intent.putExtra("user", user);
            intent.putExtra("currentGT",currentGT);
            intent.putExtra("currentAG",currentAG);
            intent.putExtra("timingData",timingData);
            startActivity(intent);
            finish();

        } else if (id == R.id.add_sensor_map_to_menu) {
            Intent intent = new Intent(AddSensorMap.this, SensorMap.class);
            intent.putExtra("data", data);
            intent.putExtra("user", user);
            intent.putExtra("currentGT",currentGT);
            intent.putExtra("currentAG",currentAG);
            intent.putExtra("timingData",timingData);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawers();

        return true;
    }
}
