package uz.avaz.asus.saveit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Market {
    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("login")
    @Expose
    String login;
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("address")
    @Expose
    String address;
    @SerializedName("phone")
    @Expose
    String phone;
    @SerializedName("latitude")
    @Expose
    Double latitude;
    @SerializedName("longitude")
    @Expose
    Double longitude;
    @SerializedName("image")
    @Expose
    String image;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    Double getLatitude() {
        return latitude;
    }

    Double getLongitude() {
        return longitude;
    }

    Integer getId() {
        return id;
    }

    String getAddress() {
        return address;
    }

    String getName() {
        return name;
    }

    String getPhone() {
        return phone;
    }

    String getLogin() {
        return login;
    }

    String getPassword() {
        return password;
    }

    String getImage() {
        return image;
    }
}
