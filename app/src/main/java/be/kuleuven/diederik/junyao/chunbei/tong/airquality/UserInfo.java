package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserInfo extends AppCompatActivity {

    private User user;

    private TextView firstname;
    private TextView lastName;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        String lName = user.getLastName();
        String fName = user.getFirstName();
        String eM = user.getMailAddress();

        firstname = (TextView) findViewById(R.id.info_firstname);
        lastName = (TextView) findViewById(R.id.info_lastname);
        email = (TextView) findViewById(R.id.info_email);

        firstname.setText(fName);
        lastName.setText(lName);
        email.setText(eM);
    }
}
