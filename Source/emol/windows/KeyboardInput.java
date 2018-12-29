package emol.windows;

import emol.Review;

//When you receive something from a window use this class, just add what you need
public class KeyboardInput {
    //From LoginWindow
    public String username;
    public String password;

    //From PresentBooksWindow
    public String keyword;
    public boolean ebook;
    public boolean paperback;
    public boolean audiobook;
    public int priceLowerThan;
    public int priceHigherThan;

    //FROM bookWindow
    public Review review;
    public int amount;

    //Adding Book Window
    public String title;
    public String subtitle;
    public double price;
    public String description;
    public String book_type;
    public String author;
    public String ISBN;
    public int languageID;

    //Adding audiobook
    public double length;
    public String narrator;
    public int fileSizeAudiobook;

    //Adding ebook
    public int pagesEbook;
    public int fileSizeEbook;

    //Adding paperback
    public int pagesPaperback;





}
