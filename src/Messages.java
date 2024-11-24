/*import java.time.LocalDateTime;

public class Messages {
    private String sender;
    private String receiver;
    private String content;
    private Status status = Status.UNSEEN;
    private LocalDateTime timeStamp;

    public Messages(String sender, String receiver, String content,LocalDateTime timeStamp) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        //this.status = status;
        this.timeStamp = LocalDateTime.now();
    }
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Status isStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return String.format("Sender: %s\n Receiver: %s\n Content: %s\n", sender, receiver, content);
    }
}*/
import java.time.LocalDateTime;

public class Messages implements Comparable<Messages> {

    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timeStamp;
    private Status status;

    public Messages(String sender, String receiver, String content,LocalDateTime timeStamp,Status status) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timeStamp = LocalDateTime.now();
        this.status = Status.UNSEEN;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Status isStatus() {
        return status;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return String.format("Sender: %s\n Receiver: %s\n Content: %s\n", sender, receiver, content);
    }

    @Override
    public int compareTo(Messages o) {
        return this.timeStamp.compareTo(o.timeStamp);
    }
}