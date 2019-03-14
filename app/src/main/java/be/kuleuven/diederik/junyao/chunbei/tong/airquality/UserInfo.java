package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class UserInfo extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private Data data = new Data();

    private TextView firstname;
    private TextView lastName;
    private TextView email;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        data = (Data) intent.getSerializableExtra("data");
        String lName = user.getLastName();
        String fName = user.getFirstName();
        String eM = user.getMailAddress();

        firstname = (TextView) findViewById(R.id.info_firstname);
        lastName = (TextView) findViewById(R.id.info_lastname);
        email = (TextView) findViewById(R.id.info_email);
        back = (TextView) findViewById(R.id.info_back);

        back.setOnClickListener(this);

        firstname.setText(fName);
        lastName.setText(lName);
        email.setText(eM);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.info_back:
                intent = new Intent(UserInfo.this,Menu.class);
                intent.putExtra("user",user);
                intent.putExtra("data",data);
                startActivity(intent);
        }
    }
}
