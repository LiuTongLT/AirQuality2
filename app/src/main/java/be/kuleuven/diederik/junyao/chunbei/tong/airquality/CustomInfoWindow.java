package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindow(Context ctx){context = ctx;}

    @Override
    public View getInfoWindow(Marker marker) {return null;}

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.custom_marker_info, null);

        TextView location = view.findViewById(R.id.custom_marler_location);
        TextView coValue = view.findViewById(R.id.custom_marker_co_value);
        TextView pmValue = view.findViewById(R.id.custom_marker_pm_value);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        try {
        location.setText(infoWindowData.getLocation());
        coValue.setText(infoWindowData.getCoValue());
            pmValue.setText(infoWindowData.getPmValue());
        } catch (NullPointerException e) {
        }

        return view;
    }
}
