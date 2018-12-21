package emol;

import emol.enums.SortType;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection connection;
    private Statement statement;

    //connect and login
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

    //Customer-------------------------------------------------------------------------------------
    //Done
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

        String query = "SELECT book.id, title, subtitle, isbn, price, type FROM book ";
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

        System.out.println("Downloading books...");
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
                        resultSet.getString("type"),
                        resultSet.getInt("id")));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        return bookData;
    }

    //Done
    public ArrayList<String> downloadBookTypes()
    {
        ArrayList<String> bookTypes = new ArrayList<String>();
        String query = "SELECT * FROM book_type";

        System.out.println("Downloading book types...");
        System.out.println(query);

        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                bookTypes.add(resultSet.getString("name"));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        return bookTypes;
    }

    //Done
    public ArrayList<String> downloadLanguages()
    {

        ArrayList<String> languages = new ArrayList<String>();
        String query = "SELECT * FROM languages";

        System.out.println("Downloading languages...");
        System.out.println(query);

        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                languages.add(resultSet.getString("name"));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        return languages;

    }

    //Done
    public ArrayList<Review> downloadReviews(String ISBN)
    {
        ArrayList<Review> reviews = new ArrayList<Review>();
        String query = "SELECT review.description, rating FROM review " +
                "JOIN book ON book.id=review.book_id WHERE book.isbn='" + ISBN + "' LIMIT 10";

        System.out.println("Downloading reviews for a book...");
        System.out.println(query);

        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                reviews.add(new Review(resultSet.getInt("rating"),
                        resultSet.getString("description")));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        return reviews;

    }

    //Done - to prevent ISBNs from repeating
    public boolean checkIfISBNAlreadyExists(String ISBN)
    {
        String query = "SELECT * FROM book WHERE isbn='" + ISBN + "'";

        System.out.println("Checking if ISBN in the database...");
        System.out.println(query);

        boolean itDoes = false;

        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                itDoes = true;
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        return itDoes;
    }

    public void buyBook(String ISBN, int amount, int userID)
    {

    }
    public void leaveReview(int book_id, Review review)
    {
        String query = "INSERT INTO review (book_id, rating, description) VALUES(" +
                book_id + ", "+ review.rating +", '" + review.description + "')";

        System.out.println("Uploading a review for a book...");
        System.out.println(query);

        try {
            statement.executeUpdate(query);
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
    }

    //Publisher---------------------------------------------------------------------------------
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
    public ArrayList<Book> downloadPublishersBooks(int publisherID)
    {
        String query = "SELECT book.id, title, subtitle, isbn, price, type FROM book ";
        query += "LEFT JOIN ebook ON ebook.book_id = book.id ";
        query += "LEFT JOIN paperback ON paperback.book_id = book.id ";
        query += "LEFT JOIN audiobook ON audiobook.book_id = book.id ";
        query += "JOIN publisher ON book.publisher_id = publisher.id ";
        query += "WHERE ";
        query += "book.publisher_id=" + publisherID;

        System.out.println("Downloading books for publisher...");
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
                        resultSet.getString("type"),
                        resultSet.getInt("id")));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        return bookData;
    }

    public void downloadSales(String ISBN)
    {

    }
    public void DownloadRoyalty(int publisherID)
    {

    }

    //Support------------------------------------------------------------------------------------
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
