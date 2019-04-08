package com.assignment.radius.radiusassignment;

public class User {
    private String name;
    private String age;
    private String image;
    private String large;
    private String user;
    private String gender;
    private String email;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;
    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }


    public User(){

    }
    public User(String name,String age, String image, String large, String user, String gender, String email, String phone){
        this.name=name;
        this.age=age;
        this.image=image;
        this.large=large;
        this.user=user;
        this.gender=gender;
        this.email=email;
        this.phone=phone;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
