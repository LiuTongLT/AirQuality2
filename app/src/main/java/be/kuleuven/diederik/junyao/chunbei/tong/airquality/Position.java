package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Position extends AppCompatActivity implements View.OnClickListener{

    private TextView groupt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position);

        groupt = (TextView) findViewById(R.id.list_groupt);

        groupt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId())
        {
            case R.id.list_groupt: intent = new Intent(this,Report.class); startActivity(intent); break;
            case R.id.list_agora: intent = new Intent(this,Report.class); startActivity(intent); break;
            default: break;
        }
    }
}
