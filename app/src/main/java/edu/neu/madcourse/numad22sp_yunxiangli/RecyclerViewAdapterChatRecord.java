package edu.neu.madcourse.numad22sp_yunxiangli;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class RecyclerViewAdapterChatRecord extends RecyclerView.Adapter<RecyclerViewAdapterChatRecord.RecyclerChatViewHolder> {
    private ArrayList<ChatRecord> chat_card_list;
    private String sender_username, receiver_username;
    private HashMap<String, Integer> sticker_id_mapping;

    public RecyclerViewAdapterChatRecord(ArrayList<ChatRecord> chat_card_list,
                                         String sender_username, String receiver_username) {
        this.chat_card_list = chat_card_list;
        this.sender_username = sender_username;
        this.receiver_username = receiver_username;
        this.sticker_id_mapping = new HashMap<>();
        map_sticker_ids();
    }

    private void map_sticker_ids() {
        sticker_id_mapping.put("sticker14", R.drawable.sticker_1);
        sticker_id_mapping.put("sticker2", R.drawable.sticker_2);
        sticker_id_mapping.put("sticker3", R.drawable.sticker_3);
        sticker_id_mapping.put("sticker4", R.drawable.sticker_4);
        sticker_id_mapping.put("sticker5", R.drawable.sticker_5);
        sticker_id_mapping.put("sticker6", R.drawable.sticker_6);
        sticker_id_mapping.put("sticker7", R.drawable.sticker_7);
        sticker_id_mapping.put("sticker8", R.drawable.sticker_8);
        sticker_id_mapping.put("sticker9", R.drawable.sticker_9);
        sticker_id_mapping.put("sticker10", R.drawable.sticker_10);
        sticker_id_mapping.put("sticker12", R.drawable.sticker_12);
        sticker_id_mapping.put("sticker13", R.drawable.sticker_13);
    }

    @NonNull
    @Override
    public RecyclerChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_viewholder, parent, false);
        return new RecyclerChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerChatViewHolder holder, int position) {
        ChatRecord chatCard = chat_card_list.get(position);

        String sticker_tag = chatCard.getSticker();
        String time = chatCard.getTime();
        Long value = Long.parseLong(time);
        Date date = new Date(value);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formatted = format.format(date);

        String sender = chatCard.getSender();
        String receiver = chatCard.getReceiver();

        if (sender.equals(sender_username) && receiver.equals(receiver_username)) {
            holder.getSenderSticker().setImageResource(sticker_id_mapping.get(sticker_tag));
            holder.getReceiverSticker().setImageResource(0);
            holder.getSenderStickerTime().setText(formatted);
            holder.getReceiverStickerTime().setText("");
        } else if (sender.equals(receiver_username) && receiver.equals(sender_username)) {
            holder.getReceiverSticker().setImageResource(sticker_id_mapping.get(sticker_tag));
            holder.getSenderSticker().setImageResource(0);
            holder.getReceiverStickerTime().setText(formatted);
            holder.getSenderStickerTime().setText("");
        }
    }

    @Override
    public int getItemCount() {
        return chat_card_list.size();
    }

    public static class RecyclerChatViewHolder extends RecyclerView.ViewHolder {
        private ImageView senderSticker;
        private ImageView receiverSticker;
        private TextView senderStickerTime;
        private TextView receiverStickerTime;

        public ImageView getSenderSticker() {
            return senderSticker;
        }

        public ImageView getReceiverSticker() {
            return receiverSticker;
        }

        public TextView getSenderStickerTime() {
            return senderStickerTime;
        }

        public TextView getReceiverStickerTime() {
            return receiverStickerTime;
        }

        public RecyclerChatViewHolder(@NonNull View itemView) {
            super(itemView);
            senderSticker = itemView.findViewById(R.id.imageViewSender);
            receiverSticker = itemView.findViewById(R.id.imageViewReceiver);
            senderStickerTime = itemView.findViewById(R.id.textViewSenderTime);
            receiverStickerTime = itemView.findViewById(R.id.textViewReceiverTime);
        }
    }
}

