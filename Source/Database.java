public class Database {
    public void connect()
    {

    }

    //Customer
    public void downloadBooks(String keyword, boolean eBook, boolean pBack, boolean aBook,
                              int priceHigherThan, int priceLowerThan, SortType sortType, int page)
    {

    }
    public void downloadBookTypes()
    {

    }
    public void downloadLanguages()
    {

    }
    public void downloadReviews(String ISBN)
    {

    }
    public void logIn(String username, String password)
    {

    }
    public void buyBook(String ISBN, int amount, int userID)
    {

    }
    public void leaveReview(String ISBN, int stars, String review)
    {

    }

    //Publisher
    public void addEBook(String ISBN, String Title, String subTitle, double price, String author,
                         int publisherID, String language, String date, String Description, int fileSize)
    {

    }
    public void addPBackBook(String ISBN, String Title, String subTitle, double price, String author,
                         int publisherID, String language, String date, String Description, int numberOfPages)
    {

    }
    public void addABook(String ISBN, String Title, String subTitle, double price, String author,
                             int publisherID, String language, String date, String Description, int length)
    {

    }
    public void downloadPublishersBooks(int publishedID)
    {

    }
    public void downloadSales(String ISBN)
    {

    }
    public void DownloadRoyalty(int publisherID)
    {

    }

    //Support
    public void deleteBook(String ISBN)
    {

    }
    public void deleteReview(int reviewID)
    {

    }
    //dostawa książek
    public void warehouseShipment(String ISBN, int amount)
    {

    }

}
