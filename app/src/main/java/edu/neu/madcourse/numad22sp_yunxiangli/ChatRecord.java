package edu.neu.madcourse.numad22sp_yunxiangli;

/**
 * This class represents a single chat record.
 */
public class ChatRecord
{
    // Store sender, receiver, sticker and time information in string form.
    private String sender;
    private String receiver;
    private String sticker;
    private String time;

    // This is required by ProGuard.
    public ChatRecord(){}

    // Constructor.
    public ChatRecord(String sender, String receiver, String sticker, String time)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.sticker = sticker;
        this.time = time;
    }

    // Altogether four getter functions.

    public String getSender()
    {
        return this.sender;
    }

    public String getReceiver()
    {
        return this.receiver;
    }

    public String getSticker()
    {
        return this.sticker;
    }

    public String getTime()
    {
        return this.time;
    }

}
