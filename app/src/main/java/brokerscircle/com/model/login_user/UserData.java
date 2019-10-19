
package brokerscircle.com.model.login_user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("comp_id")
    @Expose
    private String compId;
    @SerializedName("user_level_id")
    @Expose
    private String userLevelId;
    @SerializedName("user_rul_gp_nam_id")
    @Expose
    private String userRulGpNamId;
    @SerializedName("user_full_name")
    @Expose
    private String userFullName;
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
    private String cnicNo;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("signature_image")
    @Expose
    private Object signatureImage;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("user_primary_email_status")
    @Expose
    private String userPrimaryEmailStatus;
    @SerializedName("user_primary_number_status")
    @Expose
    private String userPrimaryNumberStatus;
    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    @SerializedName("points")
    @Expose
    private String points;
    @SerializedName("about")
    @Expose
    private Object about;
    @SerializedName("valid_from")
    @Expose
    private Object validFrom;
    @SerializedName("valid_till")
    @Expose
    private Object validTill;
    @SerializedName("opening_balance")
    @Expose
    private String openingBalance;
    @SerializedName("closing_balance")
    @Expose
    private String closingBalance;
    @SerializedName("balance_total")
    @Expose
    private String balanceTotal;
    @SerializedName("user_status")
    @Expose
    private String userStatus;
    @SerializedName("user_level_value")
    @Expose
    private String userLevelValue;
    @SerializedName("user_level_type")
    @Expose
    private String userLevelType;
    @SerializedName("user_level_status")
    @Expose
    private String userLevelStatus;
    @SerializedName("comp_rul_gp_nam_id")
    @Expose
    private String compRulGpNamId;
    @SerializedName("comp_name")
    @Expose
    private String compName;
    @SerializedName("comp_verification_status")
    @Expose
    private String compVerificationStatus;
    @SerializedName("comp_valid_from")
    @Expose
    private String compValidFrom;
    @SerializedName("comp_valid_till")
    @Expose
    private String compValidTill;
    @SerializedName("comp_status")
    @Expose
    private String compStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getUserLevelId() {
        return userLevelId;
    }

    public void setUserLevelId(String userLevelId) {
        this.userLevelId = userLevelId;
    }

    public String getUserRulGpNamId() {
        return userRulGpNamId;
    }

    public void setUserRulGpNamId(String userRulGpNamId) {
        this.userRulGpNamId = userRulGpNamId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
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

    public String getCnicNo() {
        return cnicNo;
    }

    public void setCnicNo(String cnicNo) {
        this.cnicNo = cnicNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
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

    public String getUserPrimaryEmailStatus() {
        return userPrimaryEmailStatus;
    }

    public void setUserPrimaryEmailStatus(String userPrimaryEmailStatus) {
        this.userPrimaryEmailStatus = userPrimaryEmailStatus;
    }

    public String getUserPrimaryNumberStatus() {
        return userPrimaryNumberStatus;
    }

    public void setUserPrimaryNumberStatus(String userPrimaryNumberStatus) {
        this.userPrimaryNumberStatus = userPrimaryNumberStatus;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
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

    public Object getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Object validFrom) {
        this.validFrom = validFrom;
    }

    public Object getValidTill() {
        return validTill;
    }

    public void setValidTill(Object validTill) {
        this.validTill = validTill;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(String openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(String closingBalance) {
        this.closingBalance = closingBalance;
    }

    public String getBalanceTotal() {
        return balanceTotal;
    }

    public void setBalanceTotal(String balanceTotal) {
        this.balanceTotal = balanceTotal;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserLevelValue() {
        return userLevelValue;
    }

    public void setUserLevelValue(String userLevelValue) {
        this.userLevelValue = userLevelValue;
    }

    public String getUserLevelType() {
        return userLevelType;
    }

    public void setUserLevelType(String userLevelType) {
        this.userLevelType = userLevelType;
    }

    public String getUserLevelStatus() {
        return userLevelStatus;
    }

    public void setUserLevelStatus(String userLevelStatus) {
        this.userLevelStatus = userLevelStatus;
    }

    public String getCompRulGpNamId() {
        return compRulGpNamId;
    }

    public void setCompRulGpNamId(String compRulGpNamId) {
        this.compRulGpNamId = compRulGpNamId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCompVerificationStatus() {
        return compVerificationStatus;
    }

    public void setCompVerificationStatus(String compVerificationStatus) {
        this.compVerificationStatus = compVerificationStatus;
    }

    public String getCompValidFrom() {
        return compValidFrom;
    }

    public void setCompValidFrom(String compValidFrom) {
        this.compValidFrom = compValidFrom;
    }

    public String getCompValidTill() {
        return compValidTill;
    }

    public void setCompValidTill(String compValidTill) {
        this.compValidTill = compValidTill;
    }

    public String getCompStatus() {
        return compStatus;
    }

    public void setCompStatus(String compStatus) {
        this.compStatus = compStatus;
    }

}
