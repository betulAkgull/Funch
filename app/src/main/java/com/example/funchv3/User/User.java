package com.example.funchv3.User;

public class User {
    public String name,surname,email,phoneNo,password, imageUrl;

    public User(String name, String surname, String email, String phoneNo, String password, String imageUrl){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.imageUrl = imageUrl;
    }
    public User(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
