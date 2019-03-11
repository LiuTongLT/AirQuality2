package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
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

import java.util.Date;

public class Report extends AppCompatActivity {
    private String location;
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        location = (String) intent.getSerializableExtra("location");
        System.out.println("Location: "+location);

//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        graph.addSeries(series);

        getData();
    }

    public void getData(){
        String url = "https://a18ee5air2.studev.groept.be/query/avgWeek.php?location"+location;
        RequestQueue queue = Volley.newRequestQueue(Report.this);

        System.out.println("Get data starts");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Into response!");
                            JSONArray jarr = new JSONArray(response);
                            for(int i = 0; i<jarr.length(); i++){
                                JSONObject jobj = jarr.getJSONObject(i);
                                String day = jobj.getString("days");
                                Double pm_value = jobj.getDouble("avg_pm");
                                Double co_value = jobj.getDouble("avg_co");

                                System.out.println("Day: "+day);
                                System.out.println("PM: "+pm_value);
                            }
                            System.out.println("End of response!");
                        } catch (JSONException e) {
                            System.out.println(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Report.this, "Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
    }
}
