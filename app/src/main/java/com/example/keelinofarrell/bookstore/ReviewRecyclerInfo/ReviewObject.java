package com.example.keelinofarrell.bookstore.ReviewRecyclerInfo;

/**
 * Created by keelin.ofarrell on 16/04/2018.
 */

public class ReviewObject {

    String date;
    String comment;
    String user;
    String rating;
    String profileImageUrl;
    String firstname, lastname;

    public ReviewObject(String date, String comment, String firstname, String lastname, String rating, String profileImageUrl) {
        this.date = date;
        this.comment = comment;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rating = rating;
        this.profileImageUrl= profileImageUrl;
    }

    public ReviewObject(){

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
