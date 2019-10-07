package brokerscirlce.com.model.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersUtil {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("no_of_records")
    @Expose
    private Integer noOfRecords;
    @SerializedName("data")
    @Expose
    private List<UsersData> data = null;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNoOfRecords() {
        return noOfRecords;
    }

    public void setNoOfRecords(Integer noOfRecords) {
        this.noOfRecords = noOfRecords;
    }

    public List<UsersData> getData() {
        return data;
    }

    public void setData(List<UsersData> data) {
        this.data = data;
    }
}
