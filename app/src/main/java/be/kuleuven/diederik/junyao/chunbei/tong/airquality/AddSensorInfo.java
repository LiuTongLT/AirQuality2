package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AddSensorInfo extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private Data data;
    private User user;

    private TextView lat;
    private TextView lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sensor_info);

        Intent intent = getIntent();
        latitude = (double) intent.getSerializableExtra("latitude");
        longitude = (double) intent.getSerializableExtra("longitude");
        data = (Data) intent.getSerializableExtra("data");
        user = (User) intent.getSerializableExtra("user");

        lat = findViewById(R.id.add_sensor_info_latitude);
        lng = findViewById(R.id.add_sensor_info_longitude);

        lat.setText("Latitude: " + latitude);
        lng.setText("Longitude: " + longitude);

    }
}
