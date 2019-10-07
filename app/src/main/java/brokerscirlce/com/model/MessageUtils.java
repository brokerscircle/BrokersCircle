package brokerscirlce.com.model;

public class MessageUtils {

    String id, message, time, useremail;
    boolean currentUser;

    public MessageUtils() {
    }

    public MessageUtils(String id, String message, String time, String useremail, boolean currentUser) {
        this.id = id;
        this.message = message;
        this.time = time;
        this.useremail = useremail;
        this.currentUser = currentUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public boolean isCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }
}
