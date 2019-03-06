package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private Button login;
    private TextView signUp;

    private static final String TAG = "Login";

  //  private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.button_login);
        signUp = (TextView) findViewById(R.id.signup_login);

        login.setOnClickListener(this);
        signUp.setOnClickListener(this);

//        mDatabase = FirebaseDatabase.getInstance().getReference();
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

    public void basicReadWrite() {
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("email");

        myRef.setValue("Hello@world");
        // [END write_message]

        // [START read_message]
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        // [END read_message]
    }
}

