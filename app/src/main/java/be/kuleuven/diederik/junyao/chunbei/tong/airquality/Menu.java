package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Menu extends AppCompatActivity implements View.OnClickListener{

    private CardView mapMenu, listMenu, userInfoMenu, signOutMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mapMenu = (CardView) findViewById(R.id.menu_map);
        listMenu = (CardView) findViewById(R.id.menu_list);
        userInfoMenu = (CardView) findViewById(R.id.menu_userinfo);
        signOutMenu = (CardView) findViewById(R.id.menu_out);

        mapMenu.setOnClickListener(this);
        listMenu.setOnClickListener(this);
        userInfoMenu.setOnClickListener(this);
        signOutMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId())
        {
            case R.id.menu_out: intent = new Intent(this,Login.class); startActivity(intent); break;
            case R.id.menu_userinfo: intent = new Intent(this,UserInfo.class); startActivity(intent); break;
            case R.id.menu_map: intent = new Intent(this, SensorMap.class); startActivity(intent); break;
            case R.id.menu_list: intent = new Intent(this,Position.class); startActivity(intent); break;
            default: break;

        }

    }
}
