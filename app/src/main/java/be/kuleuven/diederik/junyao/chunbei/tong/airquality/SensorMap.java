package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

        import android.Manifest;
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
        import android.widget.Toast;

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

        import java.util.ArrayList;
        import java.util.Iterator;

public class SensorMap extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    //private Circle currentCircle;
    private static final int Request_User_Location_Code = 99;
    private ArrayList markers = new ArrayList<Marker>();
    private Data data = new Data();
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_map);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {checkUserLocationPermission();}

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //temporally added like this, in the future with database
        try{
            data.addSensor(new Sensor(1,50.874780, 4.707391,"GroepT"));
            data.addSensor(new Sensor(2,50.875178, 4.707976,"CinemaZ"));
            data.addSensor(new Sensor(3,50.875882, 4.707917,"Alma1"));
            data.addSensor(new Sensor(4,50.875222, 4.709558,"Spar"));
            data.addSensor(new Sensor(5,50.877863, 4.704656,"College De Valk"));

        }
        catch(AlreadyAddedException A){}
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            return;
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

        /*if(currentCircle!=null){currentCircle.remove();}
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(5);
        circleOptions.strokeColor(Color.GREEN);
        circleOptions.strokeWidth(1);
        circleOptions.fillColor(Color.argb(60,0,127,14));
        currentCircle=mMap.addCircle(circleOptions);*/

        addSensors();
    }

    private void addSensors(){

        ArrayList sensors = data.getSensors();
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
                markerOptions.title("SensorID: " + Integer.toString(currentSensor.getSensorId()));
                markerOptions.snippet("Location: " + currentSensor.getLocation());
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                marker = mMap.addMarker(markerOptions);
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
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);}
    }

    @Override
    public void onConnectionSuspended(int i){}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){}
}


