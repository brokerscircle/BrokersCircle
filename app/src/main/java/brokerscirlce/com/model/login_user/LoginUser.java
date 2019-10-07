
package brokerscirlce.com.model.login_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginUser {

    @SerializedName("response")
    @Expose
    private LoginResponse response;

//    public String serialize() {
//        // Serialize this class into a JSON string using GSON
//        Gson gson = new Gson();
//        return gson.toJson(this);
//    }
//
//    static public LoginUser create(String serializedData) {
//        // Use GSON to instantiate this class using the JSON representation of the state
//        Gson gson = new Gson();
//        return gson.fromJson(serializedData, LoginUser.class);
//    }

    public LoginResponse getResponse() {
        return response;
    }

    public void setResponse(LoginResponse response) {
        this.response = response;
    }

}
