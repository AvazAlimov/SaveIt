package uz.avaz.asus.saveit.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("market")
    @Expose
    private Market market;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Integer getStatus() {
        return status;
    }

    public Market getMarket() {
        return market;
    }
}
