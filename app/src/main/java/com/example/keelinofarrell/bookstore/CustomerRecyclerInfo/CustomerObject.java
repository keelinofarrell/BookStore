package com.example.keelinofarrell.bookstore.CustomerRecyclerInfo;

/**
 * Created by keelin.ofarrell on 31/03/2018.
 */

public class CustomerObject {

    private String firstname, lastname, email, address, profileImageUrl, customerId;

    public  CustomerObject(){

    }

    public CustomerObject(String customerId, String firstname, String lastname, String email, String profileImageUrl){
        this.customerId = customerId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
