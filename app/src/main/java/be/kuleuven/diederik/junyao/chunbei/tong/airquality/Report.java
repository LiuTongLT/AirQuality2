package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.lang.*;

public class Report extends AppCompatActivity {

    private ArrayList<DataPoint> seriesPM = new ArrayList<>();
    private ArrayList<DataPoint> seriesCO = new ArrayList<>();
    Data data = new Data();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        seriesPM = (ArrayList<DataPoint>) intent.getSerializableExtra("PM array");
        seriesCO = (ArrayList<DataPoint>) intent.getSerializableExtra("CO array");
        System.out.println("Size PM: "+ seriesPM.size());
        System.out.println("Size CO: "+ seriesCO.size());

        if(seriesPM.size()!=0){
            DataPoint[] pm = new DataPoint[seriesPM.size()];
            for(int i = 0; i<seriesPM.size(); i++){
                pm[i] = seriesPM.get(i);
                System.out.println(pm[i]);
            }

            GraphView graph = (GraphView) findViewById(R.id.graphPM);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pm);
            graph.addSeries(series);
        }

        if(seriesCO.size()!=0){
            DataPoint[] co = new DataPoint[seriesCO.size()];
            for(int i = 0; i<seriesCO.size(); i++){
                co[i] = seriesCO.get(i);
                System.out.println(co[i]);
            }

            GraphView graph = (GraphView) findViewById(R.id.graphCO);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(co);
            graph.addSeries(series);
        }

    }
}
