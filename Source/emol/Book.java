package emol;

public class Book {
    public String title;
    public String subtitle;
    public String isbn;
    public double price;
    public String type;

    Book(String title, String subtitle,  String isbn, double price, String type){
        this.title = title;
        this.isbn = isbn;
        this.subtitle = subtitle;
        this.price = price;
        this.type = type;
    }
}
