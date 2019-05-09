package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class AddSensorInfo extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private Spinner dropdown1;
    private Spinner dropdown2;
    private Spinner dropdown3;
    private Spinner dropdown4;
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

        dropdown1 = findViewById(R.id.spinner1);
        String[] items1 = new String[]{"Sensor 1", "PM", "CO", "not used"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);

        dropdown2 = findViewById(R.id.spinner2);
        String[] items2 = new String[]{"Sensor 2", "PM", "CO", "not used"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        dropdown3 = findViewById(R.id.spinner3);
        String[] items3 = new String[]{"Sensor 3", "PM", "CO", "not used"};
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items3);
        dropdown3.setAdapter(adapter3);

        dropdown4 = findViewById(R.id.spinner4);
        String[] items4 = new String[]{"Sensor 4", "PM", "CO", "not used"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        dropdown4.setAdapter(adapter4);


    }
}
