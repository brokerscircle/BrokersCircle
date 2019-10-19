package brokerscircle.com.model.ForgotPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponseData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("primary_email_status")
    @Expose
    private String primaryEmailStatus;
    @SerializedName("primary_number")
    @Expose
    private String primaryNumber;
    @SerializedName("primary_number_status")
    @Expose
    private String primaryNumberStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryEmailStatus() {
        return primaryEmailStatus;
    }

    public void setPrimaryEmailStatus(String primaryEmailStatus) {
        this.primaryEmailStatus = primaryEmailStatus;
    }

    public String getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getPrimaryNumberStatus() {
        return primaryNumberStatus;
    }

    public void setPrimaryNumberStatus(String primaryNumberStatus) {
        this.primaryNumberStatus = primaryNumberStatus;
    }
}
