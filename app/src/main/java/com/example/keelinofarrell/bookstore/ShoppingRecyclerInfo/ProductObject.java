package com.example.keelinofarrell.bookstore.ShoppingRecyclerInfo;

/**
 * Created by keelin.ofarrell on 13/04/2018.
 */

public class ProductObject {

    private String title, author, category, price, bookId;
    private String profileImageUrl;

    public  ProductObject(){



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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public ProductObject(String bookId, String title, String author, String price, String profileImageUrl ){
        this.bookId = bookId;
        this.title = title;

        this.author = author;
        this.price = price;
        this.profileImageUrl = profileImageUrl;
    }
}
