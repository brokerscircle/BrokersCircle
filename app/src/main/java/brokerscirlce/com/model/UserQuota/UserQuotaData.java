package brokerscirlce.com.model.UserQuota;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserQuotaData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("company_id")
    @Expose
    private String companyId;
    @SerializedName("company_quota_id")
    @Expose
    private String companyQuotaId;
    @SerializedName("setting_quota_id")
    @Expose
    private String settingQuotaId;
    @SerializedName("setting_quota_title")
    @Expose
    private String settingQuotaTitle;
    @SerializedName("setting_quota_type_id")
    @Expose
    private String settingQuotaTypeId;
    @SerializedName("setting_quota_type_title")
    @Expose
    private String settingQuotaTypeTitle;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyQuotaId() {
        return companyQuotaId;
    }

    public void setCompanyQuotaId(String companyQuotaId) {
        this.companyQuotaId = companyQuotaId;
    }

    public String getSettingQuotaId() {
        return settingQuotaId;
    }

    public void setSettingQuotaId(String settingQuotaId) {
        this.settingQuotaId = settingQuotaId;
    }

    public String getSettingQuotaTitle() {
        return settingQuotaTitle;
    }

    public void setSettingQuotaTitle(String settingQuotaTitle) {
        this.settingQuotaTitle = settingQuotaTitle;
    }

    public String getSettingQuotaTypeId() {
        return settingQuotaTypeId;
    }

    public void setSettingQuotaTypeId(String settingQuotaTypeId) {
        this.settingQuotaTypeId = settingQuotaTypeId;
    }

    public String getSettingQuotaTypeTitle() {
        return settingQuotaTypeTitle;
    }

    public void setSettingQuotaTypeTitle(String settingQuotaTypeTitle) {
        this.settingQuotaTypeTitle = settingQuotaTypeTitle;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTill() {
        return validTill;
    }

    public void setValidTill(String validTill) {
        this.validTill = validTill;
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
