package com.example.keelinofarrell.bookstore.BookRecyclerInfo;

import java.io.Serializable;

/**
 * Created by keelin.ofarrell on 28/03/2018.
 */

public class BookObject implements Serializable {

    private String isbn;
    public String title;
    private String author;
    private String category;
    private String price;
    private String bookId;
    private String profileImageUrl;

    public BookObject(){


    }

    public BookObject(String bookId, String title, String author,String price, String profileImageUrl){
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.price = price;
        this.profileImageUrl = profileImageUrl;

    }


    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}
