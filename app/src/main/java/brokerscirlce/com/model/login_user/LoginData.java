
package brokerscirlce.com.model.login_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginData {

    @SerializedName("token")
    @Expose
    private LoginToken token;
    @SerializedName("user")
    @Expose
    private UserData user;

    public LoginToken getToken() {
        return token;
    }

    public void setToken(LoginToken token) {
        this.token = token;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

}
