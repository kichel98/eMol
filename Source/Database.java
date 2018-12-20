import java.sql.*;
import java.util.ArrayList;

public class Database {
    Connection connection;
    Statement statement;

    public User connect(String username, String password)
    {
        String port = "3306";
        String IP = "localhost";
        String databaseName = "emol";

        String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        String query = "SELECT user.id, user_type.name FROM user JOIN user_type " +
                "ON user.user_type_id=user_type.id WHERE username='"+username+"' AND password='"+password+"'";

        System.out.println("QUERY: "+query);
        User user = new User();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + IP + ":" + port + "/" + databaseName+timezone,
                    username, password);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            user.ID = resultSet.getInt("id");
            user.type = resultSet.getString("name");
        }
        catch (SQLException e) { System.out.println("ERROR: "+e.getMessage()); }

        return user;
    }

    //Customer
    public ArrayList<Book> downloadBooks(String keyword, boolean eBook, boolean pBack, boolean aBook,
                                         int priceHigherThan, int priceLowerThan, SortType sortType, int page)
    {
        int amountOfTypes = 0;
        if(!eBook && !pBack && aBook) amountOfTypes=1;
        if(!eBook && pBack && !aBook) amountOfTypes=1;
        if(eBook && !pBack && !aBook) amountOfTypes=1;
        if(eBook && pBack && !aBook) amountOfTypes=2;
        if(eBook && !pBack && aBook) amountOfTypes=2;
        if(!eBook && pBack && aBook) amountOfTypes=2;
        if(eBook && pBack && aBook) amountOfTypes=3;

        String query = "SELECT title, subtitle, isbn, price, type FROM book ";
        if(eBook) query += "LEFT JOIN ebook ON ebook.book_id = book.id ";
        if(pBack) query += "LEFT JOIN paperback ON paperback.book_id = book.id ";
        if(aBook) query += "LEFT JOIN audiobook ON audiobook.book_id = book.id ";
        query += "WHERE ";
        query += "title LIKE '%" + keyword + "%' AND ";
        if(amountOfTypes==3) query+= "( type='ebook' OR type='paperback' OR type='audiobook' )";
        else if(amountOfTypes==2)
        {

            if(eBook) query+= "( type='ebook' OR";
            if(pBack) query+= "( type='paperback' OR";
            if(aBook) query+= "( type='audiobook' OR";
            if(aBook) query+= " type='ebook' )";
            if(pBack) query+= " type='paperback' )";
            if(aBook) query+= " type='audiobook' )";
        }
        else if(amountOfTypes==1)
        {
            if(eBook) query+= "type='ebook'";
            if(pBack) query+= "type='pBack'";
            if(aBook) query+= "type='audio'";
        }

        query+= " AND ";
        query+= "price>"+priceHigherThan + " AND price<" + priceLowerThan + " ";
        query+= "ORDER BY price " + sortType.toString()+" ";
        query+= "LIMIT 10";

        System.out.println("QUERY: "+query);

        ArrayList<Book> bookData = new ArrayList<Book>();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                bookData.add(new Book(resultSet.getString("title"),
                        resultSet.getString("subtitle"),
                        resultSet.getString("isbn"),
                        resultSet.getDouble("price"),
                        resultSet.getString("type")));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        return bookData;
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
