package com.example.e_boimela;
import com.google.firebase.database.IgnoreExtraProperties;

public class BookDetails {

    private String id;
    private String stallName;
    private String stallNumber;
    private String  bookName;
    private String  authorName;
    private String bookPrice;
    private String emailAddress;

    public BookDetails() {
       //
    }

    public BookDetails(String emailAddress,String id, String stallName, String stallNumber, String bookName, String authorName, String bookPrice) {
        this.id = id;
        this.stallName = stallName;
        this.stallNumber = stallNumber;
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookPrice = bookPrice;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public String getStallName() {
        return stallName;
    }

    public String getStallNumber() {
        return stallNumber;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getBookPrice() {
        return bookPrice;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
