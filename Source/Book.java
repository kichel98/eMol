public class Book {
    String title;
    String subtitle;
    String isbn;
    double price;
    String type;

    Book(String title, String subtitle,  String isbn, double price, String type){
        this.title = title;
        this.isbn = isbn;
        this.subtitle = subtitle;
        this.price = price;
        this.type = type;
    }
}
