package TeamAwesome;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import edu.neu.madcourse.TeamAwesome.R;


public class FCMActivity extends AppCompatActivity {

    private static final String TAG = FCMActivity.class.getSimpleName();

    // Please add the server key from your firebase console in the follwoing format "key=<serverKey>"
    private static final String SERVER_KEY = "key=AAAAWloylcA:APA91bEyib7LZmmaaa-thgoEkktpBYaXN1tBlBrw-V28Ko0l_FwhDp9-U_tbandrCpXIN8Y9GKq8LYiwWN5Y-vsvUFOcZoDwLMFnPM2grXzVMHJuRJrhv32qanEG_EeNE50QrUKqyqOB";

    // This is the client registration token
    private static final String CLIENT_REGISTRATION_TOKEN = "cHrPCKxxS2qP6Q-MsdDjT3:APA91bGMIpActqAD8vs0cTolEDM_caiRnHGmZDEY5fajniyorNbjGeJ71nzO3PEScjqFjkp4aQ-9jJEjv6YIko3adCqAPLnhqNObygbPO2PNMBkzPTemF_mBDdO6AIcuLiawNhgUCiJI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers_display);

        ImageButton logTokenButton = (ImageButton) findViewById(R.id.sticker1);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log and toast
              //  String token = GetCurrentToken();



                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( FCMActivity.this,  new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String token = instanceIdResult.getToken();
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.e("Token",token);
                        Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });





            }
        });
    }

//    public void subscribeToNews(View view){
//
//        FirebaseMessaging.getInstance().subscribeToTopic("news")
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = getString(R.string.msg_subscribed);
//                        if (!task.isSuccessful()) {
//                            msg = getString(R.string.msg_subscribe_failed);
//                        }
//                        Log.d(TAG, msg);
//                        Toast.makeText(FCMActivity.this, msg, Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

    /**
     * Button Handler; creates a new thread that sends off a message
     * @param type
     */
    public void sendMessageToNews(View type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessageToNews();
            }
        }).start();
    }

    private void sendMessageToNews(){
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        try {
            jNotification.put("message", "This is a Firebase Cloud Messaging topic \"news\" message!");
            jNotification.put("body", "News Body");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            jNotification.put("click_action", "OPEN_ACTIVITY_1");

            // Populate the Payload object.
            // Note that "to" is a topic, not a token representing an app instance
            jPayload.put("to", "/topics/news");
            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);

            // Open the HTTP connection and send the payload
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " + resp);
                    Toast.makeText(FCMActivity.this,"response was: " + resp,Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            Log.e(TAG,"sendMessageToNews threw error",e);
        }
    }

    public void sendMessageToDevice(View type) {

//        final String targetToken = ((EditText)findViewById(R.id.tokenEditText)).getText().toString();

        new Thread(new Runnable() {
            @Override
            public void run() {
                sendMessageToDevice(CLIENT_REGISTRATION_TOKEN);
            }
        }).start();
    }

    /**
     * Pushes a notification to a given device-- in particular, this device,
     * because that's what the instanceID token is defined to be.
     */
    private void sendMessageToDevice(String targetToken) {
        JSONObject jPayload = new JSONObject();
        JSONObject jNotification = new JSONObject();
        JSONObject jdata = new JSONObject();
        try {
            jNotification.put("title", "Message Title");
            jNotification.put("body", "Message body ");
            jNotification.put("sound", "default");
            jNotification.put("badge", "1");
            /*
            // We can add more details into the notification if we want.
            // We happen to be ignoring them for this demo.
            jNotification.put("click_action", "OPEN_ACTIVITY_1");
            */
            jdata.put("title","data title");
            jdata.put("content","data content");

            /***
             * The Notification object is now populated.
             * Next, build the Payload that we send to the server.
             */

            // If sending to a single client
            jPayload.put("to", targetToken); // CLIENT_REGISTRATION_TOKEN);

            /*
            // If sending to multiple clients (must be more than 1 and less than 1000)
            JSONArray ja = new JSONArray();
            ja.put(CLIENT_REGISTRATION_TOKEN);
            // Add Other client tokens
            ja.put(FirebaseInstanceId.getInstance().getToken());
            jPayload.put("registration_ids", ja);
            */

            jPayload.put("priority", "high");
            jPayload.put("notification", jNotification);
            jPayload.put("data",jdata);


            /***
             * The Payload object is now populated.
             * Send it to Firebase to send the message to the appropriate recipient.
             */
            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", SERVER_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Send FCM message content.
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(jPayload.toString().getBytes());
            outputStream.close();

            // Read FCM response.
            InputStream inputStream = conn.getInputStream();
            final String resp = convertStreamToString(inputStream);

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "run: " + resp);
                    Toast.makeText(FCMActivity.this,resp,Toast.LENGTH_LONG).show();
                }
            });
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper function
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }



}
