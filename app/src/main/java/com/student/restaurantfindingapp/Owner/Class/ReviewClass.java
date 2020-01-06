package com.student.restaurantfindingapp.Owner.Class;

public class ReviewClass {

    private String comment;
    private String customerid;
    private String ownerid;
    private String rate;
    private String rname;

    public ReviewClass(){
        //this constructor is required
    }

    public ReviewClass(String comment, String customerid, String ownerid, String rate, String rname) {
        this.comment = comment;
        this.customerid = customerid;
        this.ownerid = ownerid;
        this.rate = rate;
        this.ownerid = ownerid;
        this.rname = rname;
    }

    public String getOwnerid() {
        return ownerid;
    }

    public String getComment() {
        return comment;
    }

    public String getRname() {
        return rname;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getRate() {
        return rate;
    }
}
