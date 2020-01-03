package com.student.restaurantfindingapp.Customer.Class;

public class RestaurantClass {

    private String rname;
    private String raddress;
    private String rmenu;
    private String rmeals;
    private String ownerid;
    private String gambar1;
    private String gambar2;

    public RestaurantClass(){
        //this constructor is required
    }

    public RestaurantClass(String rname, String raddress, String rmenu, String rmeals, String ownerid,String gambar1, String gambar2) {
        this.rname = rname;
        this.raddress = raddress;
        this.rmenu = rmenu;
        this.rmeals = rmeals;
        this.ownerid = ownerid;
        this.gambar1 = gambar1;
        this.gambar2 = gambar2;
    }

    public String getGambar1() {
        return gambar1;
    }

    public String getGambar2() {
        return gambar2;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public String getRaddress() {
        return raddress;
    }

    public String getRmeals() {
        return rmeals;
    }

    public String getRmenu() {
        return rmenu;
    }

    public String getRname() {
        return rname;
    }
}
