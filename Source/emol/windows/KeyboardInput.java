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


}
