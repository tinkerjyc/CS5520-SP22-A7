package TeamAwesome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class represents the activity of chat record between two specific users.
 */
public class UserChatRecordActivity extends AppCompatActivity
{
    //  Store the recyclerview adapter, the ArrayList to hold each SingleChat object
    //  and a TextView represents the chat information.
    private RecyclerViewAdapterChatRecord recyclerViewAdapterChatHistory;
    private final ArrayList<ChatRecord> chatLists = new ArrayList<>();
    private TextView sticker_count_info_textview;

    // Initialize a log tag.
    private static final String UserChatRecordActivityTAG = "UserChatRecordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_record_recycleview);

        // Retrieve extended data from the intent.
        Intent intent = getIntent();
        String receiver_username = intent.getStringExtra("receiver_username");
        String sender_username = intent.getStringExtra("sender_username");

        // Find and update the chat record info TextView object.
        TextView chat_record_info_textview = findViewById(R.id.chatRecordInfoTextView);
        chat_record_info_textview.append("This is chat history with user " + receiver_username);

        // Store the chat record's recycle view.
        RecyclerView recyclerViewChatRecord = findViewById(R.id.chatRecordRecycleView);
        // Initialize and set the layout manager of recyclerview.
        RecyclerView.LayoutManager recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewChatRecord.setLayoutManager(recyclerViewLayoutManger);

        // Set up the recycle view's adapter.
        recyclerViewAdapterChatHistory = new RecyclerViewAdapterChatRecord(chatLists,
                sender_username, receiver_username);
        recyclerViewChatRecord.setAdapter(recyclerViewAdapterChatHistory);

        // Get reference to root firebase database.
        // Store 2 database refs, one for root, and the other one for the chats.
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        // Get reference to "chat" location.
        DatabaseReference chatsDatabaseRef = databaseRef.child("chats");

        // Attach listener to receive events about data changes.
        chatsDatabaseRef.addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            // Get called when data changes.
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Initialize two stick related integer.
                int stickerSendNum = 0;
                int stickerReceiveNum = 0;
                // Clear the previous chat list.
                chatLists.clear();
                // Iterate each child DataSnapshot instance among all DataSnapshot.
                for(DataSnapshot eachChildSnapshot : snapshot.getChildren())
                {
                    // Find and store the each SingleChat object.
                    ChatRecord chat = eachChildSnapshot.getValue(ChatRecord.class);
                    // Check if both sender and receiver's names match the same one.
                    assert chat != null;
                    if(chat.getSender().equals(sender_username) && chat.getReceiver()
                            .equals(receiver_username))
                    {
                        // Add current SingleChat object to the chatList and increase send num.
                        chatLists.add(chat);
                        ++stickerSendNum;
                    }
                    // Check if both sender and receiver's names match the other one.
                    else if(chat.getSender().equals(receiver_username) && chat.getReceiver()
                            .equals(sender_username))
                    {
                        // Add current SingleChat object to the chatList and increase receive num.
                        chatLists.add(chat);
                        ++stickerReceiveNum;
                    }
                }
                // Sort based on epoch time from the earliest to the latest.
                chatLists.sort(Comparator.comparing(ChatRecord::getTime));

                // Notify any registered observers that the data set has changed.
                recyclerViewAdapterChatHistory.notifyDataSetChanged();

                // Update the stick count info TextView object.
                sticker_count_info_textview = findViewById(R.id.userStickerCountTextView);
                sticker_count_info_textview.setText(MessageFormat.format("Sent(<-) {0}" +
                        " Stickers! Received(->){1} Stickers!", stickerSendNum, stickerReceiveNum));
            }

            // Triggered in the event that this listener either failed at the server,
            // or is removed as a result of the security and Firebase Database rules.
            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.w(UserChatRecordActivityTAG, "Something weird happened",
                        error.toException());
            }
        });
    }
}
