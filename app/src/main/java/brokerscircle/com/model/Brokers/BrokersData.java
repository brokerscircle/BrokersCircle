package brokerscircle.com.model.Brokers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BrokersData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("broker_type_id")
    @Expose
    private String brokerTypeId;
    @SerializedName("broker_type_title")
    @Expose
    private String brokerTypeTitle;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("picture")
    @Expose
    private String picture;
    @SerializedName("opening_balance")
    @Expose
    private Object openingBalance;
    @SerializedName("closing_balance")
    @Expose
    private Object closingBalance;
    @SerializedName("balance_total")
    @Expose
    private Object balanceTotal;
    @SerializedName("average_rating")
    @Expose
    private Object averageRating;
    @SerializedName("no_of_reviews")
    @Expose
    private Object noOfReviews;
    @SerializedName("no_of_buy")
    @Expose
    private Object noOfBuy;
    @SerializedName("no_of_sell")
    @Expose
    private Object noOfSell;
    @SerializedName("no_of_rent")
    @Expose
    private Object noOfRent;
    @SerializedName("no_of_deal")
    @Expose
    private Object noOfDeal;
    @SerializedName("no_of_deal_cancel")
    @Expose
    private Object noOfDealCancel;
    @SerializedName("cnic")
    @Expose
    private Object cnic;
    @SerializedName("no_of_followers")
    @Expose
    private Object noOfFollowers;
    @SerializedName("experience")
    @Expose
    private Object experience;
    @SerializedName("license_no")
    @Expose
    private Object licenseNo;
    @SerializedName("license_registration_date")
    @Expose
    private Object licenseRegistrationDate;
    @SerializedName("license_expiry_date")
    @Expose
    private Object licenseExpiryDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_by")
    @Expose
    private Object orderBy;
    @SerializedName("created_by_comp_id")
    @Expose
    private String createdByCompId;
    @SerializedName("real_estate_name")
    @Expose
    private String realEstateName;
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

    public String getBrokerTypeId() {
        return brokerTypeId;
    }

    public void setBrokerTypeId(String brokerTypeId) {
        this.brokerTypeId = brokerTypeId;
    }

    public String getBrokerTypeTitle() {
        return brokerTypeTitle;
    }

    public void setBrokerTypeTitle(String brokerTypeTitle) {
        this.brokerTypeTitle = brokerTypeTitle;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
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

    public Object getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Object averageRating) {
        this.averageRating = averageRating;
    }

    public Object getNoOfReviews() {
        return noOfReviews;
    }

    public void setNoOfReviews(Object noOfReviews) {
        this.noOfReviews = noOfReviews;
    }

    public Object getNoOfBuy() {
        return noOfBuy;
    }

    public void setNoOfBuy(Object noOfBuy) {
        this.noOfBuy = noOfBuy;
    }

    public Object getNoOfSell() {
        return noOfSell;
    }

    public void setNoOfSell(Object noOfSell) {
        this.noOfSell = noOfSell;
    }

    public Object getNoOfRent() {
        return noOfRent;
    }

    public void setNoOfRent(Object noOfRent) {
        this.noOfRent = noOfRent;
    }

    public Object getNoOfDeal() {
        return noOfDeal;
    }

    public void setNoOfDeal(Object noOfDeal) {
        this.noOfDeal = noOfDeal;
    }

    public Object getNoOfDealCancel() {
        return noOfDealCancel;
    }

    public void setNoOfDealCancel(Object noOfDealCancel) {
        this.noOfDealCancel = noOfDealCancel;
    }

    public Object getCnic() {
        return cnic;
    }

    public void setCnic(Object cnic) {
        this.cnic = cnic;
    }

    public Object getNoOfFollowers() {
        return noOfFollowers;
    }

    public void setNoOfFollowers(Object noOfFollowers) {
        this.noOfFollowers = noOfFollowers;
    }

    public Object getExperience() {
        return experience;
    }

    public void setExperience(Object experience) {
        this.experience = experience;
    }

    public Object getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(Object licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Object getLicenseRegistrationDate() {
        return licenseRegistrationDate;
    }

    public void setLicenseRegistrationDate(Object licenseRegistrationDate) {
        this.licenseRegistrationDate = licenseRegistrationDate;
    }

    public Object getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public void setLicenseExpiryDate(Object licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
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

    public String getRealEstateName() {
        return realEstateName;
    }

    public void setRealEstateName(String realEstateName) {
        this.realEstateName = realEstateName;
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
