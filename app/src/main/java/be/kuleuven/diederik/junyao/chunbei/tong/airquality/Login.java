package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private boolean correct;
    private Button login;
    private TextView signUp;
    private User user;


    private static final String TAG = "Login";

    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(user==null){
            login = (Button) findViewById(R.id.button_login);
            login.setOnClickListener(this);
        }
        getUsersFromDatabase();


        signUp = (TextView) findViewById(R.id.signup_login);
        signUp.setOnClickListener(this);

        Intent intent = getIntent();
        data=(Data)intent.getSerializableExtra("data");
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            case R.id.button_login:
                getUsersFromDatabase();
                break;
            case R.id.signup_login:
                intent = new Intent(this, SignUp.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getUsersFromDatabase() {
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
        String url = "https://a18ee5air2.studev.groept.be/read.php";


        final TextView email = findViewById(R.id.user_email);
        final TextView password = findViewById(R.id.user_password);
        final String eMail = email.getText().toString();
        final String Pass = password.getText().toString();


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jarr = new JSONArray(response);
                            for (int i = 0; i < jarr.length(); i++) {
                                JSONObject jobj = jarr.getJSONObject(i);
                                String Email = jobj.getString("email");
                                String pass = jobj.getString("password");
                                String fName = jobj.getString("firstName");
                                String lName = jobj.getString("lastName");
                                if (eMail.equals(Email) && Pass.equals(pass)) {
                                    correct = true;
                                    user = new User(fName,lName,Email,pass);
                                }
                            }
                            if (correct) {
                                Intent intent = new Intent(getApplicationContext(), Menu.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Login.this, "Please check your email and password.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });


        queue1.add(stringRequest);
        correct = false;

    }

    public User getUser() {
        return user;
    }
}

