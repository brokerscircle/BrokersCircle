package brokerscirlce.com.model.Currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrencyData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cur_know_base_id")
    @Expose
    private String curKnowBaseId;
    @SerializedName("currency_knowledge_base_title")
    @Expose
    private String currencyKnowledgeBaseTitle;
    @SerializedName("currency_knowledge_base_code")
    @Expose
    private String currencyKnowledgeBaseCode;
    @SerializedName("currency_knowledge_base_flag")
    @Expose
    private String currencyKnowledgeBaseFlag;
    @SerializedName("currency_knowledge_base_symbol")
    @Expose
    private String currencyKnowledgeBaseSymbol;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("exchange_type")
    @Expose
    private String exchangeType;
    @SerializedName("exchange_rate")
    @Expose
    private Object exchangeRate;
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

    public String getCurKnowBaseId() {
        return curKnowBaseId;
    }

    public void setCurKnowBaseId(String curKnowBaseId) {
        this.curKnowBaseId = curKnowBaseId;
    }

    public String getCurrencyKnowledgeBaseTitle() {
        return currencyKnowledgeBaseTitle;
    }

    public void setCurrencyKnowledgeBaseTitle(String currencyKnowledgeBaseTitle) {
        this.currencyKnowledgeBaseTitle = currencyKnowledgeBaseTitle;
    }

    public String getCurrencyKnowledgeBaseCode() {
        return currencyKnowledgeBaseCode;
    }

    public void setCurrencyKnowledgeBaseCode(String currencyKnowledgeBaseCode) {
        this.currencyKnowledgeBaseCode = currencyKnowledgeBaseCode;
    }

    public String getCurrencyKnowledgeBaseFlag() {
        return currencyKnowledgeBaseFlag;
    }

    public void setCurrencyKnowledgeBaseFlag(String currencyKnowledgeBaseFlag) {
        this.currencyKnowledgeBaseFlag = currencyKnowledgeBaseFlag;
    }

    public String getCurrencyKnowledgeBaseSymbol() {
        return currencyKnowledgeBaseSymbol;
    }

    public void setCurrencyKnowledgeBaseSymbol(String currencyKnowledgeBaseSymbol) {
        this.currencyKnowledgeBaseSymbol = currencyKnowledgeBaseSymbol;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExchangeType() {
        return exchangeType;
    }

    public void setExchangeType(String exchangeType) {
        this.exchangeType = exchangeType;
    }

    public Object getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Object exchangeRate) {
        this.exchangeRate = exchangeRate;
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
