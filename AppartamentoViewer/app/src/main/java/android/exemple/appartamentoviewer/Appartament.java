package android.exemple.appartamentoviewer;

import java.util.Arrays;

public class Appartament {
    private int id;                 //id
    private String title;           //title
    private int coast;              //prices day
    private int numberOfRooms;      //rooms
    private String address;         //address
    private String info;            //description
    private String mainPhoto;       //photo_default url
    private String[] photos;        //photos url
    private String phone;           //contacts phones phone
    private String name;            //contacts name
    private String coordinate1;     //coordinates  lat
    private String coordinate2;     //coordinates  lon
    private String description;     //description_full

    public Appartament() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinate1() {
        return coordinate1;
    }

    public void setCoordinate1(String coordinate1) {
        this.coordinate1 = coordinate1;
    }

    public String getCoordinate2() {
        return coordinate2;
    }

    public void setCoordinate2(String coordinate2) {
        this.coordinate2 = coordinate2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Appartament{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", coast=" + coast +
                ", numberOfRooms=" + numberOfRooms +
                ", address='" + address + '\'' +
                ", info='" + info + '\'' +
                ", mainPhoto='" + mainPhoto + '\'' +
                ", photos=" + Arrays.toString(photos) +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", coordinate1='" + coordinate1 + '\'' +
                ", coordinate2='" + coordinate2 + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
