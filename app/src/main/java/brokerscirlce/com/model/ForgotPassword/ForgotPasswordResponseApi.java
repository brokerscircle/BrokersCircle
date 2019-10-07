package brokerscirlce.com.model.ForgotPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponseApi {

    @SerializedName("response")
    @Expose
    private ForgotPasswordResponseUtil response;

    public ForgotPasswordResponseUtil getResponse() {
        return response;
    }

    public void setResponse(ForgotPasswordResponseUtil response) {
        this.response = response;
    }
}
