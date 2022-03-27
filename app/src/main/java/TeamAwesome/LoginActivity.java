package TeamAwesome;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.neu.madcourse.TeamAwesome.R;

/**
 * This class represents the login activity(username page).
 */
public class LoginActivity extends AppCompatActivity
{

    // Store the client registration token
    private static String CLIENT_REGISTER_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Our group choose to use Firebase Realtime Database & Firebase Cloud Messaging.

        // Generate the token for the first time, then no need to do later.
        // Adds a listener that is called when the Task completes.
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task ->
        {
            if (!task.isSuccessful())
            {
                Toast.makeText(LoginActivity.this, "Something wired happen D: !",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                // Store the CLIENT_REGISTER_TOKEN for the first time
                if (CLIENT_REGISTER_TOKEN == null)
                {
                    CLIENT_REGISTER_TOKEN = task.getResult();
                }
                Log.e("CLIENT_REGISTER_TOKEN already exist", CLIENT_REGISTER_TOKEN);
            }
        });
    }

    // Called when clicking Login button
    public void onClickLoginButton(View view)
    {
        Toast.makeText(LoginActivity.this, "Login button clicked!",
                Toast.LENGTH_SHORT).show();

        // Get username textview, replace all white space with empty string.
        TextView usernameTextView = findViewById(R.id.editUsername);
        String username = usernameTextView.getText().toString()
                .replaceAll("\\s", "");

        // Check if username is empty or not.
        if (username.length() == 0)
        {
            Toast myToast = Toast.makeText(this, "Username cannot be empty!",
                    Toast.LENGTH_LONG);
            myToast.show();
        }
        else
        {
            // Start a new MainPageActivity with extra username content.
            Intent loginToMainPageIntent = new Intent(LoginActivity.this,
                    MainPageActivity.class);
            loginToMainPageIntent.putExtra("username", username);
            startActivity(loginToMainPageIntent);
        }
    }
}
