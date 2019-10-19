package brokerscircle.com.model.Owner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OwnerData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner_type_id")
    @Expose
    private String ownerTypeId;
    @SerializedName("owner_type_title")
    @Expose
    private String ownerTypeTitle;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("about")
    @Expose
    private Object about;
    @SerializedName("picture")
    @Expose
    private Object picture;
    @SerializedName("opening_balance")
    @Expose
    private Object openingBalance;
    @SerializedName("closing_balance")
    @Expose
    private Object closingBalance;
    @SerializedName("balance_total")
    @Expose
    private Object balanceTotal;
    @SerializedName("rating")
    @Expose
    private Object rating;
    @SerializedName("cnic")
    @Expose
    private Object cnic;
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

    public String getOwnerTypeId() {
        return ownerTypeId;
    }

    public void setOwnerTypeId(String ownerTypeId) {
        this.ownerTypeId = ownerTypeId;
    }

    public String getOwnerTypeTitle() {
        return ownerTypeTitle;
    }

    public void setOwnerTypeTitle(String ownerTypeTitle) {
        this.ownerTypeTitle = ownerTypeTitle;
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

    public Object getAbout() {
        return about;
    }

    public void setAbout(Object about) {
        this.about = about;
    }

    public Object getPicture() {
        return picture;
    }

    public void setPicture(Object picture) {
        this.picture = picture;
    }

    public Object getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Object openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Object getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(Object closingBalance) {
        this.closingBalance = closingBalance;
    }

    public Object getBalanceTotal() {
        return balanceTotal;
    }

    public void setBalanceTotal(Object balanceTotal) {
        this.balanceTotal = balanceTotal;
    }

    public Object getRating() {
        return rating;
    }

    public void setRating(Object rating) {
        this.rating = rating;
    }

    public Object getCnic() {
        return cnic;
    }

    public void setCnic(Object cnic) {
        this.cnic = cnic;
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
