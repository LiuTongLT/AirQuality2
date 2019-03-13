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
    private TextView back;

    private Data data;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        Intent intent = getIntent();
        data = (Data) intent.getSerializableExtra("data");
        user = (User) intent.getSerializableExtra("user");

        back = (TextView) findViewById(R.id.position_back);
        groupt = (TextView) findViewById(R.id.list_groupt);
        groupt.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId())
        {
            case R.id.list_groupt:
                intent = new Intent(this,Report.class);
                intent.putExtra("data",data);
                intent.putExtra("user", user);
                intent.putExtra("location","groupt");
                startActivity(intent);
                break;
            case R.id.list_agora:
                intent = new Intent(this,Report.class);
                intent.putExtra("data",data);
                intent.putExtra("user", user);
                intent.putExtra("location","agora");
                startActivity(intent);
                break;
            case R.id.position_back:
                intent = new Intent(this,Menu.class);
                startActivity(intent);
            default: break;
        }
    }

}
