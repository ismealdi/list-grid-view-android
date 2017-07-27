package com.aldieemaulana.president.response;

import com.aldieemaulana.president.model.President;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by aldieemaulana on 7/27/17.
 */

public class PresidentResponse implements Serializable
{

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("data")
    @Expose
    private List<President> president = null;
    private final static long serialVersionUID = -8520022947078899072L;

    /**
     * No args constructor for use in serialization
     *
     */
    public PresidentResponse() {
    }

    /**
     *
     * @param total
     * @param president
     * @param status
     */
    public PresidentResponse(Integer status, Integer total, List<President> president) {
        super();
        this.status = status;
        this.total = total;
        this.president = president;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<President> getPresident() {
        return president;
    }

    public void setPresident(List<President> president) {
        this.president = president;
    }

}
