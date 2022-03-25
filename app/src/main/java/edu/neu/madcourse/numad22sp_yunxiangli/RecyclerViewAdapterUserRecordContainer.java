package edu.neu.madcourse.numad22sp_yunxiangli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapterUserRecordContainer extends RecyclerView.Adapter<RecyclerViewAdapterUserRecordContainer.RecyclerUserRecordContainerViewHolder> {
    private ArrayList<UserInfo> user_card_list;
    private UserRecordListener userRecordListener;

    public RecyclerViewAdapterUserRecordContainer(ArrayList<UserInfo> user_card_list, UserRecordListener userRecordListener) {
        this.user_card_list = user_card_list;
        this.userRecordListener = userRecordListener;
    }

    @NonNull
    @Override
    public RecyclerUserRecordContainerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_record_container, parent, false);
        return new RecyclerUserRecordContainerViewHolder(view, userRecordListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerUserRecordContainerViewHolder holder, int position) {
        String username = user_card_list.get(position).getUsername();
        holder.getUsername().setText(username);
        holder.getSendSticker().setTag(username);
    }

    @Override
    public int getItemCount() {
        return user_card_list.size();
    }

    public static class RecyclerUserRecordContainerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView username;
        private Button sendSticker;
        private UserRecordListener userRecordListener;

        public RecyclerUserRecordContainerViewHolder(@NonNull View itemView, UserRecordListener userRecordListener) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTextView);
            sendSticker = itemView.findViewById(R.id.sendStickerButton);
            this.userRecordListener = userRecordListener;

            itemView.setOnClickListener(this);
            sendSticker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userRecordListener.onUserSendStickerButtonClick(getAdapterPosition());
                }
            });
        }

        public TextView getUsername() {
            return username;
        }

        public Button getSendSticker() {
            return sendSticker;
        }

        @Override
        public void onClick(View v) {
            userRecordListener.onUserClick(getAdapterPosition());
        }
    }

    public interface UserRecordListener {
        void onUserClick(int position);

        void onUserSendStickerButtonClick(int position);
    }
}
