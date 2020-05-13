package zoomapi;

public class Message {
    private String messageId;
    private String dateTime;
    private String sender;              // Email address of the sender
    private String receiver;            // Could be an email address/a channel id
    private String receiverName;        // Name of a receiver (person/channel)
    private boolean receiverIsChannel;
    private String message;

    public Message(String receiver, boolean receiverIsChannel, String message) {
        this.receiver = receiver;
        this.receiverIsChannel = receiverIsChannel;
        this.message = message;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public boolean receiverIsChannel() {
        return receiverIsChannel;
    }

    public String getMessage() {
        return message;
    }
}
