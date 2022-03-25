package edu.neu.madcourse.numad22sp_yunxiangli;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = LoginActivity.class.getSimpleName();
    // This is the client registration token
    private static String CLIENT_REGISTRATION_TOKEN;
    private DatabaseReference mDatabase;
    private DatabaseReference mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mMessage = mDatabase.child("message");

        // Generate the token for the first time, then no need to do later
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                } else {
                    if (CLIENT_REGISTRATION_TOKEN == null) {
                        CLIENT_REGISTRATION_TOKEN = task.getResult();
                    }
                    Log.e("CLIENT_REGISTRATION_TOKEN", CLIENT_REGISTRATION_TOKEN);
//                        Toast.makeText(LoginActivity.this, "CLIENT_REGISTRATION_TOKEN Existed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // called upon clicking Login button
    public void onClickLoginButton(View view) {
        System.out.println("Login button clicked");

        TextView usernameTextView = (TextView) findViewById(R.id.editUsername);
        String username = usernameTextView.getText().toString().replaceAll("\\s", "");

        if (username.length() == 0) {
            Toast myToast = Toast.makeText(this, "Enter Username!", Toast.LENGTH_LONG);
            myToast.show();
        }else {
            Intent intent1 = new Intent(LoginActivity.this, MainPageActivity.class);
            intent1.putExtra("USERNAME", username);
            startActivity(intent1);
        }
    }

    // saves the new user to database
    private void createNewUser(String username) {
    }

    // returns True if user exists, else False
    private boolean userExists(String username) {
        // write an api call to fetch if user exists
        return true;
    }
}
