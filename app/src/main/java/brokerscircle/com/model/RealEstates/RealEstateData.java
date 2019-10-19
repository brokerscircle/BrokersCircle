package brokerscircle.com.model.RealEstates;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RealEstateData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("comp_type_id")
    @Expose
    private String compTypeId;
    @SerializedName("comp_type_title")
    @Expose
    private String compTypeTitle;
    @SerializedName("comp_rul_gp_nam_id")
    @Expose
    private String compRulGpNamId;
    @SerializedName("company_rules_group_title")
    @Expose
    private String companyRulesGroupTitle;
    @SerializedName("membership_id")
    @Expose
    private String membershipId;
    @SerializedName("membership_title")
    @Expose
    private String membershipTitle;
    @SerializedName("company_size_id")
    @Expose
    private String companySizeId;
    @SerializedName("company_size_title")
    @Expose
    private String companySizeTitle;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("logo")
    @Expose
    private Object logo;
    @SerializedName("reg_no")
    @Expose
    private String regNo;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("points")
    @Expose
    private Object points;
    @SerializedName("opening_balance")
    @Expose
    private Object openingBalance;
    @SerializedName("closing_balance")
    @Expose
    private Object closingBalance;
    @SerializedName("balance_total")
    @Expose
    private Object balanceTotal;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("verification_status")
    @Expose
    private String verificationStatus;
    @SerializedName("valid_from")
    @Expose
    private String validFrom;
    @SerializedName("valid_till")
    @Expose
    private String validTill;
    @SerializedName("average_rating")
    @Expose
    private Object averageRating;
    @SerializedName("short_terms_and_conditions")
    @Expose
    private Object shortTermsAndConditions;
    @SerializedName("detail_terms_and_conditions")
    @Expose
    private Object detailTermsAndConditions;
    @SerializedName("slogan")
    @Expose
    private Object slogan;
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
    @SerializedName("no_of_employee")
    @Expose
    private Object noOfEmployee;
    @SerializedName("no_of_deal_cancel")
    @Expose
    private Object noOfDealCancel;
    @SerializedName("no_of_followers")
    @Expose
    private Object noOfFollowers;
    @SerializedName("since")
    @Expose
    private Object since;
    @SerializedName("expertise")
    @Expose
    private Object expertise;
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

    public String getCompTypeId() {
        return compTypeId;
    }

    public void setCompTypeId(String compTypeId) {
        this.compTypeId = compTypeId;
    }

    public String getCompTypeTitle() {
        return compTypeTitle;
    }

    public void setCompTypeTitle(String compTypeTitle) {
        this.compTypeTitle = compTypeTitle;
    }

    public String getCompRulGpNamId() {
        return compRulGpNamId;
    }

    public void setCompRulGpNamId(String compRulGpNamId) {
        this.compRulGpNamId = compRulGpNamId;
    }

    public String getCompanyRulesGroupTitle() {
        return companyRulesGroupTitle;
    }

    public void setCompanyRulesGroupTitle(String companyRulesGroupTitle) {
        this.companyRulesGroupTitle = companyRulesGroupTitle;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipTitle() {
        return membershipTitle;
    }

    public void setMembershipTitle(String membershipTitle) {
        this.membershipTitle = membershipTitle;
    }

    public String getCompanySizeId() {
        return companySizeId;
    }

    public void setCompanySizeId(String companySizeId) {
        this.companySizeId = companySizeId;
    }

    public String getCompanySizeTitle() {
        return companySizeTitle;
    }

    public void setCompanySizeTitle(String companySizeTitle) {
        this.companySizeTitle = companySizeTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Object getLogo() {
        return logo;
    }

    public void setLogo(Object logo) {
        this.logo = logo;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Object getPoints() {
        return points;
    }

    public void setPoints(Object points) {
        this.points = points;
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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
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

    public Object getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Object averageRating) {
        this.averageRating = averageRating;
    }

    public Object getShortTermsAndConditions() {
        return shortTermsAndConditions;
    }

    public void setShortTermsAndConditions(Object shortTermsAndConditions) {
        this.shortTermsAndConditions = shortTermsAndConditions;
    }

    public Object getDetailTermsAndConditions() {
        return detailTermsAndConditions;
    }

    public void setDetailTermsAndConditions(Object detailTermsAndConditions) {
        this.detailTermsAndConditions = detailTermsAndConditions;
    }

    public Object getSlogan() {
        return slogan;
    }

    public void setSlogan(Object slogan) {
        this.slogan = slogan;
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

    public Object getNoOfEmployee() {
        return noOfEmployee;
    }

    public void setNoOfEmployee(Object noOfEmployee) {
        this.noOfEmployee = noOfEmployee;
    }

    public Object getNoOfDealCancel() {
        return noOfDealCancel;
    }

    public void setNoOfDealCancel(Object noOfDealCancel) {
        this.noOfDealCancel = noOfDealCancel;
    }

    public Object getNoOfFollowers() {
        return noOfFollowers;
    }

    public void setNoOfFollowers(Object noOfFollowers) {
        this.noOfFollowers = noOfFollowers;
    }

    public Object getSince() {
        return since;
    }

    public void setSince(Object since) {
        this.since = since;
    }

    public Object getExpertise() {
        return expertise;
    }

    public void setExpertise(Object expertise) {
        this.expertise = expertise;
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
