package edu.neu.madcourse.numad22sp_yunxiangli;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class represents the activity of displaying all available stickers after user
 * clicking "send sticker" button.
 */
public class StickersDisplayActivity extends AppCompatActivity
{
    // Store sender and receiver's usernames in string form.
    private String sender_username;
    private String receiver_username;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers_display);

        // Fetch and store sender and receiver's usernames.
        sender_username = getIntent().getStringExtra("sender_username");
        receiver_username = getIntent().getStringExtra("receiver_username");

        // Set up the send_sticker_info TextView object.
        TextView send_sticker_info = findViewById(R.id.sendStickerInfo);
        send_sticker_info.append("Send any of these stickers to " + receiver_username);

    }

    // called upon clicking any image
    public void onClickImageButton(View view)
    {
        String sticker_tag = (String)view.getTag();
        Toast.makeText(StickersDisplayActivity.this, "Image clicked for tag "
                        + sticker_tag, Toast.LENGTH_SHORT).show();
        long epochTime=System.currentTimeMillis();
        saveRecordToDatabase(sender_username, receiver_username, sticker_tag,String.valueOf(epochTime));
    }

    private void saveRecordToDatabase(String sender, String receiver, String sticker_tag, String epochTime)
    {
        // save to firebase
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mChat = mDatabase.child("chats");

        ChatRecord chat = new ChatRecord(sender, receiver, sticker_tag, epochTime);

        String uniqueId = sender.concat(epochTime);
        //add to database
        Context context = getApplicationContext();
        mChat.child(uniqueId).setValue(chat, (databaseError, ref) ->
        {
            if (databaseError != null) {
                Toast myToast1= Toast.makeText(context, "Failed to send sticker", Toast.LENGTH_LONG);
                myToast1.show();
            } else {
                Toast myToast = Toast.makeText(context, chat.getSticker().concat(" Sent!"), Toast.LENGTH_LONG);
                myToast.show();
            }
        });
    }
}
