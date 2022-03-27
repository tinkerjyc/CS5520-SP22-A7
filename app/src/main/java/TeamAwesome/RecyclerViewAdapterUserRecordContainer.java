package TeamAwesome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.madcourse.TeamAwesome.R;

/**
 * This class represents the RecyclerViewAdapter of UserRecordContainer.
 */
public class RecyclerViewAdapterUserRecordContainer extends
        RecyclerView.Adapter<RecyclerViewAdapterUserRecordContainer
                .RecyclerUserRecordContainerViewHolder>
{
    // Store the List of UserInfo objects and the UserRecordListener.
    private final ArrayList<UserInfo> userInfoArrayList;
    private final UserRecordListener userRecordListener;

    // Constructor.
    public RecyclerViewAdapterUserRecordContainer(ArrayList<UserInfo> user_card_list,
                                                  UserRecordListener userRecordListener)
    {
        this.userInfoArrayList = user_card_list;
        this.userRecordListener = userRecordListener;
    }

    // Called to create a new RecyclerUserRecordContainerViewHolder.
    @NonNull
    @Override
    public RecyclerUserRecordContainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                    int viewType)
    {
        // Create UserRecordContainerActivity from user_record_container layout xml file.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_record_container, parent,
                false);
        return new RecyclerUserRecordContainerViewHolder(view, userRecordListener);
    }

    // Called by RecyclerView to display the UserInfo object's data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull RecyclerUserRecordContainerViewHolder viewHolder,
                                 int position)
    {
        // Set current viewHolder's username and sticker's tag.
        String username = userInfoArrayList.get(position).getUsername();
        viewHolder.getUsername().setText(username);
        viewHolder.getSendStickerButton().setTag(username);
    }

    // Returns the total number of UserInfo objects in the data set held by the adapter.
    @Override
    public int getItemCount()
    {
        return userInfoArrayList.size();
    }

    /**
     * This class represents the ViewHolder of RecyclerUserRecordContainer(also implement
     * the OnClickListener Interface).
     */
    public static class RecyclerUserRecordContainerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        // Store the username, send sticker button and UserRecordListener.
        private final TextView username;
        private final Button sendStickerButton;
        private final UserRecordListener userRecordListener;

        // Constructor.
        public RecyclerUserRecordContainerViewHolder(@NonNull View itemView, UserRecordListener
                userRecordListener)
        {
            super(itemView);

            // Store username TextView, sendStickerButton and userRecordListener.
            username = itemView.findViewById(R.id.usernameTextView);
            sendStickerButton = itemView.findViewById(R.id.sendStickerButton);
            this.userRecordListener = userRecordListener;

            // Set click listener to the sendSticker button.
            itemView.setOnClickListener(this);
            sendStickerButton.setOnClickListener(v ->
                    userRecordListener.onUserSendStickerButtonClick(getAdapterPosition()));
        }

        // Getter functions.

        public TextView getUsername()
        {
            return username;
        }

        public Button getSendStickerButton()
        {
            return sendStickerButton;
        }

        // Triggered when user clicks the record bar(except the send sticker button).
        @Override
        public void onClick(View v)
        {
            userRecordListener.onUserChatRecordClick(getAdapterPosition());
        }
    }

    /**
     * The interface represents the whole user record listener.
     */
    public interface UserRecordListener
    {

        // Triggered when user clicks the record bar(except the send sticker button).
        void onUserChatRecordClick(int position);

        // Triggered when user clicks the sender clicker button.
        void onUserSendStickerButtonClick(int position);
    }
}
