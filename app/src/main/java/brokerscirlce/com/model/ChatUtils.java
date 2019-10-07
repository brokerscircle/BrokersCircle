package brokerscirlce.com.model;

public class ChatUtils {

    private String id, profileImage, name, message, time;
    private boolean status;

    public ChatUtils() {
    }

    public ChatUtils(String id, String profileImage, String name, String message, String time, boolean status) {
        this.id = id;
        this.profileImage = profileImage;
        this.name = name;
        this.message = message;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
