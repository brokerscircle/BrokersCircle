
package brokerscirlce.com.model.login_user;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("messages")
    @Expose
    private List<String> messages = null;
    @SerializedName("data")
    @Expose
    private LoginData data;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

}
