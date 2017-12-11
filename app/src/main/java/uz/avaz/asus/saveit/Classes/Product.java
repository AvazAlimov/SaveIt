package uz.avaz.asus.saveit.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Product {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("market")
    @Expose
    private Integer market;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("new_price")
    @Expose
    private Double new_price;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("image")
    @Expose
    private String image;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public void setMarket(Integer market) {
        this.market = market;
    }

    public void setNew_price(Double new_price) {
        this.new_price = new_price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getDiscount() {
        return discount;
    }

    public Double getNew_price() {
        return new_price;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getCategory() {
        return category;
    }

    public Integer getMarket() {
        return market;
    }

    public String getDate() {
        return date;
    }

    public String getUnit() {
        return unit;
    }
}
