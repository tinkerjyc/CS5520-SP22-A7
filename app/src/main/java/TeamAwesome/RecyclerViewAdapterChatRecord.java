package TeamAwesome;

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

/**
 * This class represents the recyclerview adapter of ChatRecord.
 */
public class RecyclerViewAdapterChatRecord extends
        RecyclerView.Adapter<RecyclerViewAdapterChatRecord.RecyclerChatRecordViewHolder>
{
    // Store the list of ChatRecord objects, sender and receiver names
    // and a hashmap of each sticker's string name and its related integer id.
    private final ArrayList<ChatRecord> chat_card_list;
    private final String sender_username;
    private final String receiver_username;
    private final HashMap<String, Integer> stickerStr_id_hashmap;

    // Constructor.
    public RecyclerViewAdapterChatRecord(ArrayList<ChatRecord> chat_card_list,
                                         String sender_username, String receiver_username)
    {
        this.chat_card_list = chat_card_list;
        this.sender_username = sender_username;
        this.receiver_username = receiver_username;
        this.stickerStr_id_hashmap = new HashMap<>();
        // Map each sticker's string with related integer id.
        mapAll_stickerStr_to_id();
    }

    // Map each Sticker's string with related integer id.
    private void mapAll_stickerStr_to_id()
    {
        stickerStr_id_hashmap.put("sticker1", R.drawable.sticker_1);
        stickerStr_id_hashmap.put("sticker2", R.drawable.sticker_2);
        stickerStr_id_hashmap.put("sticker3", R.drawable.sticker_3);
        stickerStr_id_hashmap.put("sticker4", R.drawable.sticker_4);
        stickerStr_id_hashmap.put("sticker5", R.drawable.sticker_5);
        stickerStr_id_hashmap.put("sticker6", R.drawable.sticker_6);
        stickerStr_id_hashmap.put("sticker7", R.drawable.sticker_7);
        stickerStr_id_hashmap.put("sticker8", R.drawable.sticker_8);
        stickerStr_id_hashmap.put("sticker9", R.drawable.sticker_9);
        stickerStr_id_hashmap.put("sticker10", R.drawable.sticker_10);
        stickerStr_id_hashmap.put("sticker11", R.drawable.sticker_11);
        stickerStr_id_hashmap.put("sticker12", R.drawable.sticker_12);
        stickerStr_id_hashmap.put("sticker13", R.drawable.sticker_13);
    }

    // Called to create a new RecyclerChatViewHolder.
    @NonNull
    @Override
    public RecyclerChatRecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Create a new RecyclerChatViewHolder based on chat_record_holder xml file.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_record_holder, parent, false);
        return new RecyclerChatRecordViewHolder(view);
    }

    // Called by RecyclerView to display the ChatRecord object's data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull RecyclerChatRecordViewHolder viewHolder, int position)
    {
        // Get specified ChatRecord object.
        ChatRecord chatRecord = chat_card_list.get(position);

        // Correct time here, wrong time inside the database.
        String sticker_tag = chatRecord.getSticker();
        String time = chatRecord.getTime();
        long value = Long.parseLong(time);
        Date date = new Date(value);
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd|HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formatted = format.format(date);

        // Get sender and receiver's name in string form
        String senderStr = chatRecord.getSender();
        String receiverStr = chatRecord.getReceiver();

        // Check sender and receiver names match the same order case.
        if (senderStr.equals(sender_username) && receiverStr.equals(receiver_username))
        {
            // Set sender sticker's ImageView.
            viewHolder.getSenderSticker().setImageResource(stickerStr_id_hashmap.get(sticker_tag));
            // Set sender sticker's sending time.
            viewHolder.getSenderStickerTime().setText(formatted);
            // Clear the receiver sticker's ImageView.
            viewHolder.getReceiverSticker().setImageResource(android.R.color.transparent);
            // Clear the receiver sticker's receiving time.
            viewHolder.getReceiverStickerTime().setText("");
        }
        // Check sender and receiver names match the reverse order case.
        else if (senderStr.equals(receiver_username) && receiverStr.equals(sender_username))
        {
            // Set receiver sticker's ImageView.
            viewHolder.getReceiverSticker().setImageResource(stickerStr_id_hashmap.get(sticker_tag));
            // Set receiver sticker's time.
            viewHolder.getReceiverStickerTime().setText(formatted);
            // Clear the sender sticker's ImageView.
            viewHolder.getSenderSticker().setImageResource(android.R.color.transparent);
            // Clear the sender sticker's sending time.
            viewHolder.getSenderStickerTime().setText("");
        }
    }

    // Returns the total number of ChatRecord objects in the data set held by the adapter.
    @Override
    public int getItemCount()
    {
        return chat_card_list.size();
    }

    /**
     * This class represents the ViewHolder of each ChatRecord.
     */
    public static class RecyclerChatRecordViewHolder extends RecyclerView.ViewHolder {
        private final ImageView senderStickerImageView;
        private final ImageView receiverStickerImageView;
        private final TextView senderStickerTimeTextView;
        private final TextView receiverStickerTimeTextView;

        // All four getter functions.

        public ImageView getSenderSticker()
        {
            return senderStickerImageView;
        }

        public ImageView getReceiverSticker()
        {
            return receiverStickerImageView;
        }

        public TextView getSenderStickerTime()
        {
            return senderStickerTimeTextView;
        }

        public TextView getReceiverStickerTime()
        {
            return receiverStickerTimeTextView;
        }

        // Constructor.
        public RecyclerChatRecordViewHolder(@NonNull View itemView)
        {
            super(itemView);
            senderStickerImageView = itemView.findViewById(R.id.imageViewSender);
            receiverStickerImageView = itemView.findViewById(R.id.imageViewReceiver);
            senderStickerTimeTextView = itemView.findViewById(R.id.textViewSenderTime);
            receiverStickerTimeTextView = itemView.findViewById(R.id.textViewReceiverTime);
        }
    }
}

