package com.aldieemaulana.president.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by aldieemaulana on 7/27/17.
 */

public class President implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("birth_of_date")
    @Expose
    private String birthOfDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("period")
    @Expose
    private String period;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    private final static long serialVersionUID = -8274647460392051685L;

    /**
     * No args constructor for use in serialization
     *
     */
    public President() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param createdAt
     * @param description
     * @param name
     * @param birthOfDate
     * @param period
     * @param photo
     * @param country
     */
    public President(Integer id, String name, String country, String birthOfDate, String description, String period, String photo, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.name = name;
        this.country = country;
        this.birthOfDate = birthOfDate;
        this.description = description;
        this.period = period;
        this.photo = photo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(String birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}