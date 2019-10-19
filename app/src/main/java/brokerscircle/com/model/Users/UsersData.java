package brokerscircle.com.model.Users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("comp_id")
    @Expose
    private String compId;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("user_level_id")
    @Expose
    private String userLevelId;
    @SerializedName("user_level_title")
    @Expose
    private String userLevelTitle;
    @SerializedName("user_rul_gp_nam_id")
    @Expose
    private String userRulGpNamId;
    @SerializedName("user_rul_gp_nam_title")
    @Expose
    private String userRulGpNamTitle;
    @SerializedName("membership_id")
    @Expose
    private String membershipId;
    @SerializedName("membership_name")
    @Expose
    private String membershipName;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("display_name")
    @Expose
    private String displayName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("date_of_birth")
    @Expose
    private String dateOfBirth;
    @SerializedName("cnic_no")
    @Expose
    private Object cnicNo;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("img")
    @Expose
    private Object img;
    @SerializedName("signature_image")
    @Expose
    private Object signatureImage;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("primary_email_code")
    @Expose
    private String primaryEmailCode;
    @SerializedName("primary_email_status")
    @Expose
    private String primaryEmailStatus;
    @SerializedName("primary_number")
    @Expose
    private String primaryNumber;
    @SerializedName("primary_number_code")
    @Expose
    private String primaryNumberCode;
    @SerializedName("primary_number_status")
    @Expose
    private String primaryNumberStatus;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    @SerializedName("live_status")
    @Expose
    private String liveStatus;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("about")
    @Expose
    private Object about;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("opening_balance")
    @Expose
    private Object openingBalance;
    @SerializedName("closing_balance")
    @Expose
    private Object closingBalance;
    @SerializedName("balance_total")
    @Expose
    private Object balanceTotal;
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

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(String userLevelId) {
        this.userLevelId = userLevelId;
    }

    public String getUserLevelTitle() {
        return userLevelTitle;
    }

    public void setUserLevelTitle(String userLevelTitle) {
        this.userLevelTitle = userLevelTitle;
    }

    public String getUserRulGpNamId() {
        return userRulGpNamId;
    }

    public void setUserRulGpNamId(String userRulGpNamId) {
        this.userRulGpNamId = userRulGpNamId;
    }

    public String getUserRulGpNamTitle() {
        return userRulGpNamTitle;
    }

    public void setUserRulGpNamTitle(String userRulGpNamTitle) {
        this.userRulGpNamTitle = userRulGpNamTitle;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getCnicNo() {
        return cnicNo;
    }

    public void setCnicNo(Object cnicNo) {
        this.cnicNo = cnicNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getImg() {
        return img;
    }

    public void setImg(Object img) {
        this.img = img;
    }

    public Object getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(Object signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryEmailCode() {
        return primaryEmailCode;
    }

    public void setPrimaryEmailCode(String primaryEmailCode) {
        this.primaryEmailCode = primaryEmailCode;
    }

    public String getPrimaryEmailStatus() {
        return primaryEmailStatus;
    }

    public void setPrimaryEmailStatus(String primaryEmailStatus) {
        this.primaryEmailStatus = primaryEmailStatus;
    }

    public String getPrimaryNumber() {
        return primaryNumber;
    }

    public void setPrimaryNumber(String primaryNumber) {
        this.primaryNumber = primaryNumber;
    }

    public String getPrimaryNumberCode() {
        return primaryNumberCode;
    }

    public void setPrimaryNumberCode(String primaryNumberCode) {
        this.primaryNumberCode = primaryNumberCode;
    }

    public String getPrimaryNumberStatus() {
        return primaryNumberStatus;
    }

    public void setPrimaryNumberStatus(String primaryNumberStatus) {
        this.primaryNumberStatus = primaryNumberStatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public Object getAbout() {
        return about;
    }

    public void setAbout(Object about) {
        this.about = about;
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
