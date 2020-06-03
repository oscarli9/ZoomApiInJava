package zoomapi.models;

public class Message {
    private String messageId;           // The message id
    private String dateTime;
    private String sender;              // Email address of the sender
    private String receiver;            // Could be an email address/a channel id
    private String receiverName;        // Name of a receiver (person/channel)
    private Boolean receiverIsChannel;
    private String message;
    private long timeStamp;

    public Message() {}

    public Message(String receiver, boolean receiverIsChannel, String message) {
        this.receiver = receiver;
        this.receiverIsChannel = receiverIsChannel;
        this.message = message;
    }

    public Message(String messageId, String dateTime, String sender, String receiver, String receiverName, boolean receiverIsChannel, String message, long timeStamp) {
        this.messageId = messageId;
        this.dateTime = dateTime;
        this.sender = sender;
        this.receiver = receiver;
        this.receiverName = receiverName;
        this.receiverIsChannel = receiverIsChannel;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public boolean isReceiverIsChannel() {
        return receiverIsChannel;
    }

    public void setReceiverIsChannel(boolean receiverIsChannel) {
        this.receiverIsChannel = receiverIsChannel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
