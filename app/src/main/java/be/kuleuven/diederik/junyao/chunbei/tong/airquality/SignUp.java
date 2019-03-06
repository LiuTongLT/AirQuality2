package be.kuleuven.diederik.junyao.chunbei.tong.airquality;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id = "1";
        String email = "tong@gmail.com";
        writeNewUser(id,email);
    }

    private void writeNewUser(String userId, String mailAddress) {
        User user = new User(mailAddress);
        mDatabase.child("user").child(userId).setValue(user);
    }
}
