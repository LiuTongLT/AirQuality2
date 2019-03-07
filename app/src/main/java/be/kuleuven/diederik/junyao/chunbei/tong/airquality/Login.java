package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private Button login;
    private TextView signUp;

    private static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.button_login);
        signUp = (TextView) findViewById(R.id.signup_login);

        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

        getUsersFromDatabase();
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId())
        {
            case R.id.button_login: intent = new Intent(this,Menu.class); startActivity(intent); break;
            case R.id.signup_login: intent = new Intent(this,SignUp.class); startActivity(intent);break;
            default: break;
        }
    }

    private void getUsersFromDatabase() {

    }


}

