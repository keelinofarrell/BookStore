package com.example.keelinofarrell.bookstore.PurchasesRecyclerInfo;

/**
 * Created by keelin.ofarrell on 16/04/2018.
 */

public class PurchaseObject {

    private String isbn;
    private String title;
    private String author;
    private String price;
    private String bookId;
    private String date;
    private String profileImageUrl;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
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



    public PurchaseObject(){


    }

    public PurchaseObject(String bookId, String title, String author,String price, String profileImageUrl, String date){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.profileImageUrl = profileImageUrl;
        this.date = date;

    }
}
