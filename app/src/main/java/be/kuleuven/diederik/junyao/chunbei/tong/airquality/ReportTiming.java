package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class ReportTiming extends Fragment {
    View view;

    private Data timingData = new Data();
    private User user;
    private ArrayList<Measurement> measurements=new ArrayList<Measurement>();
    private Sensor sensor;
    private DrawerLayout drawerLayout;

    public ReportTiming() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_report_timing,container,false);

        Intent intent = getActivity().getIntent();
        timingData=(Data) intent.getSerializableExtra("data");
        user = (User) intent.getSerializableExtra("user");
        sensor =(Sensor) intent.getSerializableExtra("sensor");
        System.out.println("Location: " + sensor.getLocation());
        String location = (String) intent.getSerializableExtra("location");
        try{
            if(location!=null){
                measurements=timingData.getMeasurementsByLocation(location);
            }
            else if(sensor!=null){
                measurements=timingData.getMeasurementsByLocation(sensor.getLocation());
            }
            Collections.sort(measurements, new Comparator<Measurement>() {
                @Override
                public int compare(Measurement m1, Measurement m2) {
                    return (m1.getSeconds()-m2.getSeconds());
                }
            });
        }
        catch(EmptyListException E){}

        if(measurements.size()!=0){
            DataPoint[] pm = new DataPoint[measurements.size()];
            for(int i = 0; i<measurements.size(); i++){
                double day=measurements.get(i).getDay();
                double pmValue=measurements.get(i).getPmValue();
                DataPoint datapointPm = new DataPoint(i,pmValue);
                pm[i] = datapointPm;
                System.out.println(pm[i]);
            }

            GraphView graph = (GraphView) view.findViewById(R.id.graphPM_timing);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(pm);
            graph.addSeries(series);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(2);

            graph.getViewport().setYAxisBoundsManual(true);
        }

        if(measurements.size()!=0){
            DataPoint[] co = new DataPoint[measurements.size()];
            for(int i = 0; i<measurements.size(); i++){
                double day=measurements.get(i).getDay();
                double coValue=measurements.get(i).getCoValue();
                DataPoint datapointCo = new DataPoint(i,coValue);
                co[i] = datapointCo;
                System.out.println(co[i]);
            }

            GraphView graph = (GraphView) view.findViewById(R.id.graphCO_timing);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(co);
            graph.addSeries(series);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(20);

            graph.getViewport().setYAxisBoundsManual(true);
        }

        // Inflate the layout for this fragment
        return view;
    }
}
