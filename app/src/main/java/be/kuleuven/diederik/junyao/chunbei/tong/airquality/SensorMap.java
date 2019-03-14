package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.location.Location;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.content.ContextCompat;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.android.gms.common.ConnectionResult;
        import com.google.android.gms.common.api.GoogleApiClient;
        import com.google.android.gms.location.LocationListener;
        import com.google.android.gms.location.LocationRequest;
        import com.google.android.gms.location.LocationServices;
        import com.google.android.gms.maps.CameraUpdate;
        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.BitmapDescriptor;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.Circle;
        import com.google.android.gms.maps.model.CircleOptions;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.Hashtable;
        import java.util.Iterator;
        import java.util.Map;
        import java.util.Set;

public class SensorMap extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener,
        LocationListener, View.OnClickListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    //private Circle currentCircle;
    private static final int Request_User_Location_Code = 99;
    private ArrayList markers = new ArrayList<Marker>();
    private Data data;
    private Marker marker;
    Button getReport;
    Button goBack;
    private Sensor sensorOfMarker;
    private User user;
    private ImageView refresh;
    //private Map<String, double[]> currentV = new Hashtable<>();
    private Set<Measurement> currentV = new HashSet<>();
    private double[] gtCurrent = new double[2];
    private double[] agCurrent = new double[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_map);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {checkUserLocationPermission();}

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        data = (Data) intent.getSerializableExtra("data");
        //System.out.println("Sensors: "+data.getSensors());
        user = (User) intent.getSerializableExtra("user");

        goBack = findViewById(R.id.sensor_map_goBack);
        getReport = findViewById(R.id.sensor_map_getReport);
        refresh = findViewById(R.id.map_refresh);

        refresh.setOnClickListener(this);
        getReport.setOnClickListener(this);
        goBack.setOnClickListener(this);
        getReport.setVisibility(View.INVISIBLE);

//        addSensors();
        getCurrentValue("groupt");
        getCurrentValue("agora");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        addSensors();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            return;
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        sensorOfMarker=(Sensor)marker.getTag();
        getReport.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) { getReport.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sensor_map_goBack:
                Intent intent1 = new Intent (SensorMap.this, Menu.class);
                intent1.putExtra("data", data);
                intent1.putExtra("user", user);
                startActivity(intent1);
                finish();
                break;
            case R.id.map_refresh:
                getCurrentValue("groupt");
                getCurrentValue("agora");
                System.out.println("gt PM: "+gtCurrent[0]+" gt CO: "+gtCurrent[1]);
                addSensors();


//                ArrayList<Sensor> sensors = data.getSensors();
//                for(int i = 0; i < sensors.size(); i++){
//                    getCurrentValue(sensors.get(i).getLocation());
//                }
//                Iterator<Measurement> itr = currentV.iterator();
//                while(itr.hasNext()){
//                    Measurement m = itr.next();
//                    System.out.println("Location: "+m.getLocation()+" CO: "+m.getCoValue()+" PM: "+m.getPmValue());
//                }
                break;
            case R.id.sensor_map_getReport:
                Intent intent2 = new Intent (SensorMap.this, Report.class);
                intent2.putExtra("data", data);
                intent2.putExtra("user", user);
                intent2.putExtra("sensor", sensorOfMarker);
                startActivity(intent2);
                finish();
                break;
        }
    }

    public boolean checkUserLocationPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_Code);}
            else {ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},Request_User_Location_Code);}
            return false;
        }
        else{return true;}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case Request_User_Location_Code:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                        if(googleApiClient==null){buildGoogleApiClient();}
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{Toast.makeText(this,"Permission Denied...",Toast.LENGTH_SHORT).show();}
                break;
        }

    }

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location){

        lastLocation = location;
        mMap.clear();
        if(currentUserLocationMarker!=null){currentUserLocationMarker.remove();}

        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("user Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        currentUserLocationMarker=mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(5));

        if(googleApiClient!=null){LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);}
    }

    private void getCurrentValue(final String location){
        final String loc=location;
        final double[] values=new double[2];
        String url = "https://a18ee5air2.studev.groept.be/query/readCurrent.php?location=" + location;
        RequestQueue queue = Volley.newRequestQueue(SensorMap.this);

        System.out.println("Get CurrentValue starts");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Into response!");
//                            JSONObject jobj = new JSONObject(response);
//                            double currentPM = jobj.getDouble("valuePM");
//                            double currentCO = jobj.getDouble("valueCO");
//                            values[0]=currentPM;
//                            values[1]=currentCO;
                            JSONArray jarr = new JSONArray(response);
                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject jobj = jarr.getJSONObject(i);
                                double currentPM = jobj.getDouble("valuePM");
                                double currentCo=jobj.getDouble("valueCO");
                                String day = jobj.getString("timeStamps");
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
                                Measurement m = new Measurement(currentCo,currentPM,date,loc);
                                currentV.add(m);
                                if(location.equals("groupt")){
                                    gtCurrent[0] = currentPM;
                                    gtCurrent[1] = currentCo;
                                }
                                else if(location.equals("agora")){
                                    agCurrent[0] = currentPM;
                                    agCurrent[1] = currentCo;
                                }
                                values[0]=currentPM;
                                values[1]=currentCo;
                            }
                            System.out.println("End of response!");
                            System.out.println("currentPM: "+values[0]);
                            System.out.println("currentCO: "+values[1]);

                        } catch (JSONException e) {
                            System.out.println(e);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SensorMap.this, "Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }

    private void addSensors(){

        ArrayList<Sensor> sensors = new ArrayList<>();
        sensors = data.getSensors();
        if(!sensors.isEmpty()){
            Iterator<Sensor> it = sensors.iterator();
            while(it.hasNext()){
                Sensor currentSensor = it.next();

                LatLng latlng = new LatLng(currentSensor.getXcoordinate(),currentSensor.getYcoordinate());//latitude is xcoordinate, longitude is ycoordinate

                marker = findMarkerInListByPosition(latlng);

                if(marker!=null){
                    markers.remove(marker);
                    marker.remove();
                }

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latlng);
                markerOptions.title("Location: " + currentSensor.getLocation());

                if(currentSensor.getLocation().equals("groupt")){
                    markerOptions.snippet("PM value: " + Double.toString(gtCurrent[0])+", CO value: " +Double.toString(gtCurrent[1]));
                }else if(currentSensor.getLocation().equals("agora")){
                    markerOptions.snippet("PM value: " + Double.toString(agCurrent[0])+", CO value: " +Double.toString(agCurrent[1]));
                }

                //markerOptions.snippet("PM value: " + Double.toString(currentV.get(currentSensor.getLocation())[0])+", CO value: " +Double.toString(currentV.get(currentSensor.getLocation())[1]));
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                marker = mMap.addMarker(markerOptions);
                marker.setTag(currentSensor);
                markers.add(marker);
            }
        }
    }

    private Marker findMarkerInListByPosition(LatLng latlng) {
        Iterator<Marker> ite = markers.iterator();
        while(ite.hasNext()){
            Marker currentMarker = ite.next();
            if(currentMarker.getPosition().longitude==latlng.longitude && currentMarker.getPosition().latitude==latlng.latitude){
                return currentMarker;
            }
        }
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);}
    }

    @Override
    public void onConnectionSuspended(int i){}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){}

}


