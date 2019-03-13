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
    private String location;

    Date today = new Date();
//    private DataPoint[] seriesPM = {new DataPoint(4,5),new DataPoint(5,0),new DataPoint(6,0),new DataPoint(7,0),
//            new DataPoint(8,4),new DataPoint(9,10),new DataPoint(10,0)};

    private ArrayList<DataPoint> seriesPM = new ArrayList<>();
    private ArrayList<DataPoint> seriesCO = new ArrayList<>();
//    private ArrayList<DataPoint> seriesCO = new ArrayList<>();

    //private LineGraphSeries<DataPoint> seriesPM = new LineGraphSeries<>();
    //private LineGraphSeries<DataPoint> seriesCO = new LineGraphSeries<>();

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
        //System.out.println("Location: "+location);

//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
//                new DataPoint(0, 1),
//                new DataPoint(1, 5),
//                new DataPoint(2, 3),
//                new DataPoint(3, 2),
//                new DataPoint(4, 6)
//        });
//        graph.addSeries(series);


        //getData();
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void getData(){
//        String url = "https://a18ee5air2.studev.groept.be/query/avgWeek.php?location="+location;
//        RequestQueue queue = Volley.newRequestQueue(Report.this);
//
//        LocalDateTime localToday = LocalDateTime.ofInstant(today.toInstant(), ZoneId.systemDefault());
//        final int dayOfToday = localToday.getDayOfMonth();
//        System.out.println("Day of today: "+dayOfToday);
//
//        System.out.println("Get data starts");
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            System.out.println("Into response!");
//                            JSONArray jarr = new JSONArray(response);
//                            for(int i = 0; i<jarr.length(); i++){
//                                JSONObject jobj = jarr.getJSONObject(i);
//                                String day = jobj.getString("days");
//                                Date date=new SimpleDateFormat("yyyy-MM-dd").parse(day);
//                                Double pm_value = jobj.getDouble("avg_pm");
//                                Double co_value = jobj.getDouble("avg_CO");
//
//                                int dayOfDate = (int)(day.charAt(8)-'0')*10+(int)(day.charAt(9)-'0');
//
//                                DataPoint dataPointPM = new DataPoint(dayOfDate,pm_value);
//
//                                if(7-dayOfToday+dayOfDate >= 0 && 7-dayOfToday+dayOfDate <= 6){
//                                    seriesPM[7-dayOfToday+dayOfDate] = dataPointPM;
//                                }
//
//                                DataPoint dataPointCO = new DataPoint(date,co_value);
//
//                                System.out.println("Date: "+date);
//                                System.out.println("PM: "+pm_value);
//                                System.out.println("Day of date: "+dayOfDate);
//                                System.out.println("SeriesPM: "+seriesPM[7-dayOfToday+dayOfDate]);
//                            }
//                            System.out.println("End of response!");
//                        } catch (JSONException e) {
//                            System.out.println(e);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Report.this, "Error...", Toast.LENGTH_SHORT).show();
//                error.printStackTrace();
//            }
//        });
//        queue.add(stringRequest);
//        GraphView graph = (GraphView) findViewById(R.id.graph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(seriesPM);
//        graph.addSeries(series);
//    }
}
