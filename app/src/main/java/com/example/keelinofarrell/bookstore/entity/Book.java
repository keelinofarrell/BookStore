package com.example.keelinofarrell.bookstore.entity;

/**
 * Created by keelin.ofarrell on 23/03/2018.
 */

public class Book {

    private String isbn, title, author, category, price, profileImageUrl;

    public Book(){


    }

    public Book(String isbn, String title, String author, String category, String price){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;

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
}
