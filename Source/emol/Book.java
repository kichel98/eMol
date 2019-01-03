package emol;

public class Book {
    public int book_id;
    public String title;
    public String subtitle;
    public String isbn;
    public double price;
    public String type;
    public String description;
    public String author;
    public String date;
    public String language;
    public int pagesEbook;
    public int fileSizeEbook;
    public int pagesPaperback;
    public double lengthAudiobook;
    public int fileSizeAudiobook;
    public String narratorAudiobook;

    Book(String title, String subtitle,  String isbn, double price, String type, int book_id, String description,
         String author, String date, String language){
        this.title = title;
        this.isbn = isbn;
        this.subtitle = subtitle;
        this.price = price;
        this.type = type;
        this.book_id = book_id;
        this.description = description;
        this.author = author;
        this.date = date;
        this.language = language;
        System.out.println("title: " + title + " type: " + type);
    }

    public void fillEbook(int pagesEbook, int fileSizeEbook) {
        this.pagesEbook = pagesEbook;
        this.fileSizeEbook = fileSizeEbook;
    }

    public void fillPaperback(int pagesPaperback) {
        this.pagesPaperback = pagesPaperback;
    }

    public void fillAudiobook(double lengthAudiobook, int fileSizeAudiobook, String narratorAudiobook) {
        this.lengthAudiobook = lengthAudiobook;
        this.fileSizeAudiobook = fileSizeAudiobook;
        this.narratorAudiobook = narratorAudiobook;
    }
}
