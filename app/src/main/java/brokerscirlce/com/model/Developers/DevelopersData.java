package brokerscirlce.com.model.Developers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DevelopersData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("property_developer_type_id")
    @Expose
    private String propertyDeveloperTypeId;
    @SerializedName("property_developer_title")
    @Expose
    private String propertyDeveloperTitle;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("no_of_project_completed")
    @Expose
    private Object noOfProjectCompleted;
    @SerializedName("no_of_project_in_progress")
    @Expose
    private Object noOfProjectInProgress;
    @SerializedName("no_of_project_canceled")
    @Expose
    private Object noOfProjectCanceled;
    @SerializedName("no_of_project_off_plan")
    @Expose
    private Object noOfProjectOffPlan;
    @SerializedName("average_rating")
    @Expose
    private Object averageRating;
    @SerializedName("no_of_reviews")
    @Expose
    private Object noOfReviews;
    @SerializedName("no_of_followers")
    @Expose
    private Object noOfFollowers;
    @SerializedName("no_of_brokers")
    @Expose
    private Object noOfBrokers;
    @SerializedName("since")
    @Expose
    private Object since;
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

    public String getPropertyDeveloperTypeId() {
        return propertyDeveloperTypeId;
    }

    public void setPropertyDeveloperTypeId(String propertyDeveloperTypeId) {
        this.propertyDeveloperTypeId = propertyDeveloperTypeId;
    }

    public String getPropertyDeveloperTitle() {
        return propertyDeveloperTitle;
    }

    public void setPropertyDeveloperTitle(String propertyDeveloperTitle) {
        this.propertyDeveloperTitle = propertyDeveloperTitle;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Object getNoOfProjectCompleted() {
        return noOfProjectCompleted;
    }

    public void setNoOfProjectCompleted(Object noOfProjectCompleted) {
        this.noOfProjectCompleted = noOfProjectCompleted;
    }

    public Object getNoOfProjectInProgress() {
        return noOfProjectInProgress;
    }

    public void setNoOfProjectInProgress(Object noOfProjectInProgress) {
        this.noOfProjectInProgress = noOfProjectInProgress;
    }

    public Object getNoOfProjectCanceled() {
        return noOfProjectCanceled;
    }

    public void setNoOfProjectCanceled(Object noOfProjectCanceled) {
        this.noOfProjectCanceled = noOfProjectCanceled;
    }

    public Object getNoOfProjectOffPlan() {
        return noOfProjectOffPlan;
    }

    public void setNoOfProjectOffPlan(Object noOfProjectOffPlan) {
        this.noOfProjectOffPlan = noOfProjectOffPlan;
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

    public Object getNoOfFollowers() {
        return noOfFollowers;
    }

    public void setNoOfFollowers(Object noOfFollowers) {
        this.noOfFollowers = noOfFollowers;
    }

    public Object getNoOfBrokers() {
        return noOfBrokers;
    }

    public void setNoOfBrokers(Object noOfBrokers) {
        this.noOfBrokers = noOfBrokers;
    }

    public Object getSince() {
        return since;
    }

    public void setSince(Object since) {
        this.since = since;
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
