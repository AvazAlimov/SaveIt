package uz.avaz.asus.saveit;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@SuppressWarnings("unused")
public class Category {
    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("image")
    @Expose
    String image;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
