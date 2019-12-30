package com.student.restaurantfindingapp.Owner.Class;

public class RestaurantClass {

    private String rname;
    private String raddress;
    private String rmenu;
    private String rmeals;
    private String ownerid;

    public RestaurantClass(){
        //this constructor is required
    }

    public RestaurantClass(String rname, String raddress, String rmenu, String rmeals, String ownerid) {
        this.rname = rname;
        this.raddress = raddress;
        this.rmenu = rmenu;
        this.rmeals = rmeals;
        this.ownerid = ownerid;
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
