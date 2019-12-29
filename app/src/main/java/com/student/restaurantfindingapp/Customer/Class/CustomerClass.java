package com.student.restaurantfindingapp.Customer.Class;

public class CustomerClass {

    private String email;
    private String name;
    private String password;

    public CustomerClass(){
        //this constructor is required
    }

    public CustomerClass(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
