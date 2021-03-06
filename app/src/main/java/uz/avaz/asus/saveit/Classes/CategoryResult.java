package uz.avaz.asus.saveit.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class CategoryResult {
    @SerializedName("data")
    @Expose
    private List<Category> data;
    @SerializedName("status")
    @Expose
    private Integer status;

    public void setData(List<Category> data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public List<Category> getData() {
        return data;
    }
}
