package edu.neu.madcourse.numad22sp_yunxiangli;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StickersDisplayActivity extends AppCompatActivity {
    private String current_user_username;
    private String friend_username;
    private DatabaseReference mDatabase;
    private DatabaseReference mChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickers_display);

        current_user_username = getIntent().getStringExtra("current_user_username");
        friend_username = getIntent().getStringExtra("friend_username");
        TextView send_sticker_info = (TextView) findViewById(R.id.sendStickerInfo);
        send_sticker_info.append("Send any of these stickers to " + friend_username);

    }

    // called upon clicking any image
    public void onClickImageButton(View view) {
        String sticker_tag = (String)view.getTag();
        System.out.println("Image clicked for tag " + sticker_tag);
        long epochTime=System.currentTimeMillis();
        saveRecordToDatabase(current_user_username, friend_username, sticker_tag,String.valueOf(epochTime));
    }

    private void saveRecordToDatabase(String sender, String receiver, String sticker_tag, String epochTime) {
        // save to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mChat = mDatabase.child("chats");

        Context c = getApplicationContext();
        int sticker_id = c.getResources().getIdentifier("drawable/"+sticker_tag, null, c.getPackageName());
        SingleChat chat = new SingleChat(sender, receiver, sticker_tag, epochTime, sticker_id);

        String uniqueId = sender.concat(epochTime);
        //add to database
        Context context = getApplicationContext();
        mChat.child(uniqueId).setValue(chat, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference ref) {
                if (databaseError != null) {
                    Toast myToast1= Toast.makeText(context, "Failed to send sticker", Toast.LENGTH_LONG);
                    myToast1.show();
                } else {
                    Toast myToast = Toast.makeText(context, chat.getSticker().concat(" Sent!"), Toast.LENGTH_LONG);
                    myToast.show();
                }
            }
        });
    }
}
