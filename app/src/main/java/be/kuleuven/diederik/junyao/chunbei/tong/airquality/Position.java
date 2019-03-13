package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class Position extends AppCompatActivity implements View.OnClickListener{

    private TextView groupt;

    Date today = new Date();

    private ArrayList<DataPoint> seriesPMGT = new ArrayList<>();
    private ArrayList<DataPoint> seriesCOGT = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        seriesPMGT = getData("groupt")[0];
        seriesCOGT = getData("groupt")[1];

        groupt = (TextView) findViewById(R.id.list_groupt);
        groupt.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId())
        {
            case R.id.list_groupt:
                intent = new Intent(this,Report.class);
                intent.putExtra("PM array",seriesPMGT);
                intent.putExtra("CO array", seriesCOGT);
                startActivity(intent);
                break;
            case R.id.list_agora:
                intent = new Intent(this,Report.class);
                intent.putExtra("location","agora");
                startActivity(intent);
                break;
            default: break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList[] getData(String location) {

        final String loc=location;
        final ArrayList[] series = new ArrayList[2];
        final ArrayList<DataPoint> seriespm = new ArrayList<>();
        final ArrayList<DataPoint> seriesco = new ArrayList<>();

        String url = "https://a18ee5air2.studev.groept.be/query/avgWeek.php?location=" + location;
        RequestQueue queue = Volley.newRequestQueue(Position.this);

        /*LocalDateTime localToday = LocalDateTime.ofInstant(today.toInstant(), ZoneId.systemDefault());
        final int dayOfToday = localToday.getDayOfMonth();
        System.out.println("Day of today: " + dayOfToday);*/

        System.out.println("Get data starts");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Into response!");
                            JSONArray jarr = new JSONArray(response);
                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject jobj = jarr.getJSONObject(i);
                                String day = jobj.getString("days");
                                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
                                Double pm_value = jobj.getDouble("avg_pm");
                                Double co_value = jobj.getDouble("avg_CO");
                                /*
                                Data data = new Data();
                                try{
                                data.addMeasurement(new Measurement(co_value,pm_value,date,loc));}
                                catch(AlreadyAddedException A){}*/

                                int dayOfDate = (int) (day.charAt(8) - '0') * 10 + (int) (day.charAt(9) - '0');

                                DataPoint dataPointPM = new DataPoint(dayOfDate, pm_value);
                                seriespm.add(dataPointPM);

                                DataPoint dataPointCO = new DataPoint(dayOfDate, co_value);
                                seriesco.add(dataPointCO);

                                System.out.println("Date: " + date);
                                System.out.println("PM: " + pm_value);
                                System.out.println("CO: " + co_value);
                                System.out.println("Day of date: " + dayOfDate);
                            }
                            System.out.println("End of response!");
                        } catch (JSONException e) {
                            System.out.println(e);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Position.this, "Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        queue.add(stringRequest);
        series[0] = seriespm;
        series[1] = seriesco;
        return series;
    }
}
