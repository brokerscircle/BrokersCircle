package brokerscircle.com.model.Property;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("property_listing_type_id")
    @Expose
    private String propertyListingTypeId;
    @SerializedName("property_purpose_id")
    @Expose
    private String propertyPurposeId;
    @SerializedName("property_parent_category_id")
    @Expose
    private String propertyParentCategoryId;
    @SerializedName("property_child_category_id")
    @Expose
    private String propertyChildCategoryId;
    @SerializedName("property_sub_child_category_id")
    @Expose
    private Object propertySubChildCategoryId;
    @SerializedName("unit_id")
    @Expose
    private String unitId;
    @SerializedName("currency_id")
    @Expose
    private String currencyId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("audio_recording")
    @Expose
    private Object audioRecording;
    @SerializedName("thumbnail")
    @Expose
    private Object thumbnail;
    @SerializedName("published_date")
    @Expose
    private Object publishedDate;
    @SerializedName("expired_date")
    @Expose
    private Object expiredDate;
    @SerializedName("last_refreshed_date")
    @Expose
    private String lastRefreshedDate;
    @SerializedName("refresh_count")
    @Expose
    private String refreshCount;
    @SerializedName("visitor")
    @Expose
    private Object visitor;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_by")
    @Expose
    private String orderBy;
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

    public String getPropertyListingTypeId() {
        return propertyListingTypeId;
    }

    public void setPropertyListingTypeId(String propertyListingTypeId) {
        this.propertyListingTypeId = propertyListingTypeId;
    }

    public String getPropertyPurposeId() {
        return propertyPurposeId;
    }

    public void setPropertyPurposeId(String propertyPurposeId) {
        this.propertyPurposeId = propertyPurposeId;
    }

    public String getPropertyParentCategoryId() {
        return propertyParentCategoryId;
    }

    public void setPropertyParentCategoryId(String propertyParentCategoryId) {
        this.propertyParentCategoryId = propertyParentCategoryId;
    }

    public String getPropertyChildCategoryId() {
        return propertyChildCategoryId;
    }

    public void setPropertyChildCategoryId(String propertyChildCategoryId) {
        this.propertyChildCategoryId = propertyChildCategoryId;
    }

    public Object getPropertySubChildCategoryId() {
        return propertySubChildCategoryId;
    }

    public void setPropertySubChildCategoryId(Object propertySubChildCategoryId) {
        this.propertySubChildCategoryId = propertySubChildCategoryId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getAudioRecording() {
        return audioRecording;
    }

    public void setAudioRecording(Object audioRecording) {
        this.audioRecording = audioRecording;
    }

    public Object getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Object thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Object getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Object publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Object getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Object expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getLastRefreshedDate() {
        return lastRefreshedDate;
    }

    public void setLastRefreshedDate(String lastRefreshedDate) {
        this.lastRefreshedDate = lastRefreshedDate;
    }

    public String getRefreshCount() {
        return refreshCount;
    }

    public void setRefreshCount(String refreshCount) {
        this.refreshCount = refreshCount;
    }

    public Object getVisitor() {
        return visitor;
    }

    public void setVisitor(Object visitor) {
        this.visitor = visitor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
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
