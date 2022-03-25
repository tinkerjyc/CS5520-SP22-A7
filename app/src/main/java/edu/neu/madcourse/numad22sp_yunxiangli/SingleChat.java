package edu.neu.madcourse.numad22sp_yunxiangli;

public class SingleChat {
    private String sender;
    private String receiver;
    private String sticker;
    private String time;
    private int sticker_id;

    public SingleChat(){}

    public SingleChat(String sender, String receiver, String sticker, String time, int sticker_id) {
        this.sender = sender;
        this.receiver = receiver;
        this.sticker = sticker;
        this.time = time;
        this.sticker_id = sticker_id;
    }

    public String getSender() {
        return this.sender;
    }

    public String getReceiver() {
        return this.receiver;
    }

    public String getSticker() {
        return this.sticker;
    }

    public String getTime() {
        return this.time;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSticker(String sticker) {
        this.sticker = sticker;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSticker_id() {
        return sticker_id;
    }

    public void setSticker_id(int sticker_id) {
        this.sticker_id = sticker_id;
    }

}
