package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.app.ProgressDialog.show;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    Button signUp;
    Button goBack;
    EditText firstName;
    EditText lastName;
    EditText eMailAddress;
    EditText password;
    EditText repeatPassword;
    Data data = new Data();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.sign_up_signUp);
        goBack = findViewById(R.id.sign_up_goBack);
        firstName = findViewById(R.id.sign_up_firstName);
        lastName = findViewById(R.id.sign_up_lastName);
        eMailAddress = findViewById(R.id.sign_up_eMailAddress);
        password = findViewById(R.id.sign_up_password);
        repeatPassword = findViewById(R.id.sign_up_repeatPassword);

        signUp.setOnClickListener(this);
        goBack.setOnClickListener(this);

        //import data from Intent
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sign_up_signUp:
                checkData();
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
            
            try{
                data.addUser(new User(firstName_1,lastName_1,eMailAddress_1,password_1));
                Toast.makeText(this, "Signed Up successfully!",Toast.LENGTH_SHORT).show();
                changeActivity();
            }
            catch (AlreadyAddedException a){
                Toast.makeText(this, "This eMailaddress is already in use. Try another one!",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Something went wrong. Try Again!",Toast.LENGTH_SHORT).show();
        }
    }

    public void changeActivity(){
        Intent intent = new Intent(SignUp.this, Login.class);
        intent.putExtra("data",data);
        startActivity(intent);
        finish();
    }
}
