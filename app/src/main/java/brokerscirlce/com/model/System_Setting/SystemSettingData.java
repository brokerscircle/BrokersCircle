package brokerscirlce.com.model.System_Setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SystemSettingData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("api_id")
    @Expose
    private String apiId;
    @SerializedName("app_default_buy_id")
    @Expose
    private String appDefaultBuyId;
    @SerializedName("app_default_rent_id")
    @Expose
    private String appDefaultRentId;
    @SerializedName("app_default_wanted_id")
    @Expose
    private String appDefaultWantedId;
    @SerializedName("app_default_project_id")
    @Expose
    private String appDefaultProjectId;
    @SerializedName("app_logo_square")
    @Expose
    private Object appLogoSquare;
    @SerializedName("app_logo_vertical")
    @Expose
    private Object appLogoVertical;
    @SerializedName("app_logo_horizontal")
    @Expose
    private Object appLogoHorizontal;
    @SerializedName("app_title")
    @Expose
    private String appTitle;
    @SerializedName("app_slogan")
    @Expose
    private String appSlogan;
    @SerializedName("app_about")
    @Expose
    private Object appAbout;
    @SerializedName("app_api_base_url")
    @Expose
    private String appApiBaseUrl;
    @SerializedName("app_backup_time")
    @Expose
    private Object appBackupTime;
    @SerializedName("app_contact_email")
    @Expose
    private Object appContactEmail;
    @SerializedName("app_contact_address")
    @Expose
    private Object appContactAddress;
    @SerializedName("app_contact_cell_number")
    @Expose
    private Object appContactCellNumber;
    @SerializedName("app_contact_phone_number")
    @Expose
    private Object appContactPhoneNumber;
    @SerializedName("app_website_url")
    @Expose
    private Object appWebsiteUrl;
    @SerializedName("app_privacy_policy")
    @Expose
    private Object appPrivacyPolicy;
    @SerializedName("app_terms_of_service")
    @Expose
    private Object appTermsOfService;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_by")
    @Expose
    private Object orderBy;
    @SerializedName("created_by_comp_id")
    @Expose
    private String createdByCompId;
    @SerializedName("created_by_user_id")
    @Expose
    private String createdByUserId;
    @SerializedName("updated_by_user_id")
    @Expose
    private Object updatedByUserId;
    @SerializedName("deleted_by_user_id")
    @Expose
    private Object deletedByUserId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getAppDefaultBuyId() {
        return appDefaultBuyId;
    }

    public void setAppDefaultBuyId(String appDefaultBuyId) {
        this.appDefaultBuyId = appDefaultBuyId;
    }

    public String getAppDefaultRentId() {
        return appDefaultRentId;
    }

    public void setAppDefaultRentId(String appDefaultRentId) {
        this.appDefaultRentId = appDefaultRentId;
    }

    public String getAppDefaultWantedId() {
        return appDefaultWantedId;
    }

    public void setAppDefaultWantedId(String appDefaultWantedId) {
        this.appDefaultWantedId = appDefaultWantedId;
    }

    public String getAppDefaultProjectId() {
        return appDefaultProjectId;
    }

    public void setAppDefaultProjectId(String appDefaultProjectId) {
        this.appDefaultProjectId = appDefaultProjectId;
    }

    public Object getAppLogoSquare() {
        return appLogoSquare;
    }

    public void setAppLogoSquare(Object appLogoSquare) {
        this.appLogoSquare = appLogoSquare;
    }

    public Object getAppLogoVertical() {
        return appLogoVertical;
    }

    public void setAppLogoVertical(Object appLogoVertical) {
        this.appLogoVertical = appLogoVertical;
    }

    public Object getAppLogoHorizontal() {
        return appLogoHorizontal;
    }

    public void setAppLogoHorizontal(Object appLogoHorizontal) {
        this.appLogoHorizontal = appLogoHorizontal;
    }

    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public String getAppSlogan() {
        return appSlogan;
    }

    public void setAppSlogan(String appSlogan) {
        this.appSlogan = appSlogan;
    }

    public Object getAppAbout() {
        return appAbout;
    }

    public void setAppAbout(Object appAbout) {
        this.appAbout = appAbout;
    }

    public String getAppApiBaseUrl() {
        return appApiBaseUrl;
    }

    public void setAppApiBaseUrl(String appApiBaseUrl) {
        this.appApiBaseUrl = appApiBaseUrl;
    }

    public Object getAppBackupTime() {
        return appBackupTime;
    }

    public void setAppBackupTime(Object appBackupTime) {
        this.appBackupTime = appBackupTime;
    }

    public Object getAppContactEmail() {
        return appContactEmail;
    }

    public void setAppContactEmail(Object appContactEmail) {
        this.appContactEmail = appContactEmail;
    }

    public Object getAppContactAddress() {
        return appContactAddress;
    }

    public void setAppContactAddress(Object appContactAddress) {
        this.appContactAddress = appContactAddress;
    }

    public Object getAppContactCellNumber() {
        return appContactCellNumber;
    }

    public void setAppContactCellNumber(Object appContactCellNumber) {
        this.appContactCellNumber = appContactCellNumber;
    }

    public Object getAppContactPhoneNumber() {
        return appContactPhoneNumber;
    }

    public void setAppContactPhoneNumber(Object appContactPhoneNumber) {
        this.appContactPhoneNumber = appContactPhoneNumber;
    }

    public Object getAppWebsiteUrl() {
        return appWebsiteUrl;
    }

    public void setAppWebsiteUrl(Object appWebsiteUrl) {
        this.appWebsiteUrl = appWebsiteUrl;
    }

    public Object getAppPrivacyPolicy() {
        return appPrivacyPolicy;
    }

    public void setAppPrivacyPolicy(Object appPrivacyPolicy) {
        this.appPrivacyPolicy = appPrivacyPolicy;
    }

    public Object getAppTermsOfService() {
        return appTermsOfService;
    }

    public void setAppTermsOfService(Object appTermsOfService) {
        this.appTermsOfService = appTermsOfService;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Object orderBy) {
        this.orderBy = orderBy;
    }

    public String getCreatedByCompId() {
        return createdByCompId;
    }

    public void setCreatedByCompId(String createdByCompId) {
        this.createdByCompId = createdByCompId;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Object getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Object updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public Object getDeletedByUserId() {
        return deletedByUserId;
    }

    public void setDeletedByUserId(Object deletedByUserId) {
        this.deletedByUserId = deletedByUserId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

}
