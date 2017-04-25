package ke.co.ewaste.rstar4.data;

/**
 * Created by  on 19/04/2017.
 */

public class Waste {
    int id;
    String name;
    String wastetype;
    String location;
    String phone;
    String price;
    String photo;
    String photo_dir;

    public String getWastetype() {
        return wastetype;
    }

    public void setWastetype(String wastetype) {
        this.wastetype = wastetype;
    }

    public Waste() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoto_dir() {
        return photo_dir;
    }

    public void setPhoto_dir(String photo_dir) {
        this.photo_dir = photo_dir;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
