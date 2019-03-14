package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Position extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Data data;
    private User user;
    private Sensor sensor;
    private ArrayList sensors;
    private ArrayList locations;
    private Button goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        goBack=findViewById(R.id.position_goBack);
        goBack.setOnClickListener(this);

        Intent intent = getIntent();
        data = (Data) intent.getSerializableExtra("data");
        user = (User) intent.getSerializableExtra("user");

        sensors = data.getSensors();
        locations = new ArrayList<String>();

        Iterator<Sensor> it=sensors.iterator();
        while(it.hasNext()){
            Sensor currentSensor=it.next();
            locations.add(currentSensor.getLocation());
            System.out.println(currentSensor.location);
        }

        ListView listView = findViewById(R.id.position_listview);
        ArrayAdapter <String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,locations);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.position_goBack:
                Intent intent = new Intent(this,Menu.class);
                intent.putExtra("data", data);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
                break;
            default:break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){

        sensor=(Sensor)sensors.get(i);

        Intent intent = new Intent(this,Report.class);
        intent.putExtra("data",data);
        intent.putExtra("user",user);
        intent.putExtra("sensor",sensor);
        startActivity(intent);
        finish();

    }
}
