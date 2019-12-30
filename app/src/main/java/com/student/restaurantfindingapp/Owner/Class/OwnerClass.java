package com.student.restaurantfindingapp.Owner.Class;

public class OwnerClass {

    private String email;
    private String name;
    private String password;

    public OwnerClass(){
        //this constructor is required
    }

    public OwnerClass(String email, String name, String password) {
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
