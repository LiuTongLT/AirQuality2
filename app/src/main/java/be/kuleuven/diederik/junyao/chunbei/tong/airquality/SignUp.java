package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static android.app.ProgressDialog.show;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button signUp;
    Button confirm;
    Button goBack;
    EditText firstName;
    EditText lastName;
    EditText eMailAddress;
    EditText password;
    EditText repeatPassword;
    Data data = new Data();

    private boolean exist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.sign_up);
        confirm = findViewById(R.id.sign_up_confirm);
        goBack=findViewById(R.id.sign_up_goBack);

        signUp.setOnClickListener(this);
        confirm.setOnClickListener(this);
        goBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_up_confirm:
                checkInfo();
                break;
            case R.id.sign_up:
                saveInfo();
                break;
            case R.id.sign_up_goBack:
                changeActivity();
                break;
            default: break;

        }
    }

    private void checkData(){
        String password_1 = password.getText().toString();
        String repeatPassword_1 = password.getText().toString();
        String firstName_1 = firstName.getText().toString();
        String lastName_1 = lastName.getText().toString();
        String eMailAddress_1 = eMailAddress.getText().toString();
        
        if(password_1.equals(repeatPassword_1)
            && firstName_1.length()>0
            && lastName_1.length()>0
            && eMailAddress_1.contains("@")){

            //add user to database and check if it's already in the database.
            changeActivity(); //when successful

        }
        else{
            Toast.makeText(this, "Something went wrong. Try Again!",Toast.LENGTH_SHORT).show();
        }
    }

    public void changeActivity(){
        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);
    }

    public void checkInfo() {
            firstName = findViewById(R.id.sign_up_firstName);
            lastName = findViewById(R.id.sign_up_lastName);
            eMailAddress = findViewById(R.id.sign_up_eMailAddress);
            password = findViewById(R.id.sign_up_password);
            repeatPassword = findViewById(R.id.sign_up_repeatPassword);

            final String email = eMailAddress.getText().toString();
            final String fName = firstName.getText().toString();
            final String lName = lastName.getText().toString();
            final String pass = password.getText().toString();
            String rPass = repeatPassword.getText().toString();

            if (!pass.equals(rPass)) {
                Toast.makeText(this, "Please check your password again!", Toast.LENGTH_SHORT).show();
            }
            else {
                RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());
                String url = "https://a18ee5air2.studev.groept.be/query/readUser.php";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jarr = new JSONArray(response);
                                    for(int i = 0; i<jarr.length(); i++){
                                        JSONObject jobj = jarr.getJSONObject(i);
                                        String Email = jobj.getString("email");
                                        if (email.equals(Email)) {
                                            exist = true;
                                        }
                                    }
                                    if (exist) {
                                        Toast.makeText(SignUp.this, "This email address is already in use. Try another one!", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(SignUp.this, "Correct!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    System.out.println(e);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUp.this, "Error...", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
                queue1.add(stringRequest);
                exist = false;
            }
    }

    public void saveInfo(){
            firstName = findViewById(R.id.sign_up_firstName);
            lastName = findViewById(R.id.sign_up_lastName);
            eMailAddress = findViewById(R.id.sign_up_eMailAddress);
            password = findViewById(R.id.sign_up_password);
            repeatPassword = findViewById(R.id.sign_up_repeatPassword);

            final String email = eMailAddress.getText().toString();
            final String fName = firstName.getText().toString();
            final String lName = lastName.getText().toString();
            final String pass = password.getText().toString();

            final AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);

            String url = "https://a18ee5air2.studev.groept.be/query/insertUser.php?email=" + email + "&first=" + fName + "&last=" + lName + "&pass=" + pass;
            RequestQueue queue2 = Volley.newRequestQueue(SignUp.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            builder.setTitle("Dear, ");
                            builder.setMessage("Create an account successfully~ Please remember your email and password. They are important for the later login.");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    firstName.setText("");
                                    lastName.setText("");
                                    eMailAddress.setText("");
                                    password.setText("");
                                    changeActivity();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            System.out.println(response);

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SignUp.this, "Please check your email ...",Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });
            queue2.add(stringRequest);
    }
}
