package TeamAwesome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import edu.neu.madcourse.TeamAwesome.R;

/**
 * This class represents the activity of the main page
 * (also implements UserRecordListener interface).
 */
public class MainPageActivity extends AppCompatActivity implements
        RecyclerViewAdapterUserRecordContainer.UserRecordListener
{

    // Store the adapter of the recyclerview, ArrayList of UserInfo objects and usernameStr string.
    private RecyclerViewAdapterUserRecordContainer recyclerViewAdapterUserRecordContainer;
    private final ArrayList<UserInfo> userInfoList = new ArrayList<>();
    private String usernameStr;

    // Initialize a log tag.
    private static final String MainPageActivityTAG = "MainPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        // Store passed username.
        usernameStr = getIntent().getStringExtra("username");
        // Set up the user info text view.
        TextView user_info_textview = findViewById(R.id.userInfoTextView);
        user_info_textview.setTypeface(null, Typeface.BOLD_ITALIC);
        user_info_textview.append("Hello User: " + usernameStr +
                ", send a sticker to any of the app" + " users. Click on each row to see record " +
                "between you and that user");
        UserInfo userInfo = new UserInfo(usernameStr);

        // Get reference of root node.
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        // Get "users" reference
        DatabaseReference usersDBRef = mDatabase.child("users");
        // Add userInfo object to users database.
        usersDBRef.child(usernameStr).setValue(userInfo);

        // Set up recyclerview layout manager.
        RecyclerView recyclerViewForAllUsers = findViewById(R.id.recyclerViewAllChats);
        RecyclerView.LayoutManager recyclerViewLayoutManger = new LinearLayoutManager(this);
        recyclerViewForAllUsers.setLayoutManager(recyclerViewLayoutManger);

        // Set recyclerView's adapter.
        recyclerViewAdapterUserRecordContainer = new RecyclerViewAdapterUserRecordContainer
                (userInfoList, this);
        recyclerViewForAllUsers.setAdapter(recyclerViewAdapterUserRecordContainer);

        // Attach listener to receive events about data changes.
        usersDBRef.addValueEventListener(new ValueEventListener()
        {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            // Get called when data changes.
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                // Clear the previous userInfo list.
                userInfoList.clear();
                // Iterate each child DataSnapshot instance among all DataSnapshot.
                for(DataSnapshot eachChildSnapshot :snapshot.getChildren())
                {
                    // Find and store each UserInfo object.
                    UserInfo userInfo = eachChildSnapshot.getValue(UserInfo.class);
                    assert userInfo != null;
                    // Check if username string matches.
                    if(!userInfo.getUsername().equals(usernameStr))
                    {
                        // Add current SingleChat object to the chatList and increase send num.
                        userInfoList.add(userInfo);
                    }
                }
                // Notify any registered observers that the data set has changed.
                recyclerViewAdapterUserRecordContainer.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Log.w(MainPageActivityTAG, "Something weird happened", error.toException());
            }
        });
    }

    // Triggered when user clicks the record bar(except the send sticker button).
    // Start the ChatRecordActivity.
    @Override
    public void onUserChatRecordClick(int position)
    {
        Toast.makeText(MainPageActivity.this, "user chat record clicked!",
                Toast.LENGTH_SHORT).show();

        // Create a new Intent object of UserChatRecordActivity.
        Intent intent = new Intent(this, UserChatRecordActivity.class);
        // Add specific(clicked) user record's related sender name and receiver's name.
        intent.putExtra("receiver_username", userInfoList.get(position).getUsername());
        intent.putExtra("sender_username", usernameStr);

        startActivity(intent);
    }

    // Triggered when user clicks the send sticker button.
    // Start the ChatRecordActivity.
    @Override
    public void onUserSendStickerButtonClick(int position)
    {
        Toast.makeText(MainPageActivity.this, "send sticker button clicked!",
                Toast.LENGTH_SHORT).show();

        // Create a new Intent object of  StickersDisplayActivity.
        Intent intent = new Intent(this, StickersDisplayActivity.class);
        // Add specific(clicked) button's related sender name and receiver's name.
        intent.putExtra("receiver_username", userInfoList.get(position).getUsername());
        intent.putExtra("sender_username", usernameStr);

        startActivity(intent);
    }
}
