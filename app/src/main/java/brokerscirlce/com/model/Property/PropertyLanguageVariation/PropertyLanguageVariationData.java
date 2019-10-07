
package brokerscirlce.com.model.Property.PropertyLanguageVariation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PropertyLanguageVariationData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("property_id")
    @Expose
    private String propertyId;
    @SerializedName("language_id")
    @Expose
    private String languageId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
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

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
