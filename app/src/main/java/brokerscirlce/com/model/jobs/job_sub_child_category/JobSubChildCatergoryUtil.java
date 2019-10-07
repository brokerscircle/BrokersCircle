package brokerscirlce.com.model.jobs.job_sub_child_category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JobSubChildCatergoryUtil {

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
    private List<JobSubChildCategoryData> data = null;

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

    public List<JobSubChildCategoryData> getData() {
        return data;
    }

    public void setData(List<JobSubChildCategoryData> data) {
        this.data = data;
    }

}
