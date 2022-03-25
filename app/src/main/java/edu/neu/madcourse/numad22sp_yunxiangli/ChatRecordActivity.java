package edu.neu.madcourse.numad22sp_yunxiangli;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class ChatRecordActivity  extends AppCompatActivity {
    private RecyclerView recyclerViewChatHistory;
    private RecyclerView.LayoutManager recyclerViewLayoutManger;
    private RecyclerViewAdapterChatRecord recyclerViewAdapterChatHistory;
    private ArrayList<SingleChat> chatLists = new ArrayList<>();

    private DatabaseReference mDatabase;
    private DatabaseReference mChat;
    private TextView sticker_count_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_record_recycleview);

        Intent intent = getIntent();
        String friend_username = intent.getStringExtra("friend_username");
        String current_user_username = intent.getStringExtra("current_user_username");

        TextView chat_info = (TextView) findViewById(R.id.chatHistoryInfoText);
        chat_info.append("This is chat history with " + friend_username);

        recyclerViewChatHistory = findViewById(R.id.recyclerChatHistory);
        recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewChatHistory.setLayoutManager(recyclerViewLayoutManger);

        recyclerViewAdapterChatHistory = new RecyclerViewAdapterChatRecord(chatLists,
                current_user_username, friend_username);
        recyclerViewChatHistory.setAdapter(recyclerViewAdapterChatHistory);

        //reference to firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //get user reference
        mChat = mDatabase.child("chats");

        mChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sent = 0, received = 0;
                chatLists.clear();
                for(DataSnapshot snapshot1 :snapshot.getChildren()){
                    SingleChat chat = snapshot1.getValue(SingleChat.class);
                    if(chat.getSender().equals(current_user_username) && chat.getReceiver().equals(friend_username)){
                        chatLists.add(chat);
                        sent ++;
                    }
                    else if(chat.getSender().equals(friend_username) && chat.getReceiver().equals(current_user_username)){
                        chatLists.add(chat);
                        received ++;
                    }
                }
                //sort based on epoch time
                chatLists.sort((c1,c2)->c1.getTime().compareTo(c2.getTime()));

                recyclerViewAdapterChatHistory.notifyDataSetChanged();
                sticker_count_info = (TextView) findViewById(R.id.userStickerCountTextView);
                sticker_count_info.setText("Stickers sent " + sent + " Received " + received);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
