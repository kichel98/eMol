package emol;

import emol.enums.SortType;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    private Connection connection;
    private Statement statement;
    private PreparedStatement pStatement;

    long start_time;
    long end_time;

    //connect and login
    public User connect(String username, String password)
    {
        String port = "3306";
        String IP = "localhost";
        String databaseName = "emol";

        String timezone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        String query = "SELECT user.id, user_type.name FROM user JOIN user_type " +
                "ON user.user_type_id=user_type.id WHERE username = ? AND password = ?";



        System.out.println("QUERY: "+query);
        User user = new User();

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + IP + ":" + port + "/" + databaseName+timezone,
                    username, password);
            statement = connection.createStatement();
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, username);
            pStatement.setString(2, password);
            ResultSet resultSet = pStatement.executeQuery();
            resultSet.next();
            user.ID = resultSet.getInt("id");
            user.type = resultSet.getString("name");
            user.username = username;
            user.password = password;
            if(user.type.equals("Publisher"))
            {
                String query2 = "SELECT publisher.id FROM publisher JOIN user ON user.id = publisher.user_id"+
                        " WHERE publisher.user_id="+user.ID;
                ResultSet resultSet2 = statement.executeQuery(query2);
                resultSet2.next();
                user.publisherID = Integer.parseInt(resultSet2.getString("id"));
            }
            return user;
        }
        catch (SQLException e) { System.out.println("ERROR: "+e.getMessage()); return null; }

    }

    //Customer-------------------------------------------------------------------------------------
    //Done
    public ArrayList<Book> downloadBooks(String keyword, boolean eBook, boolean pBack, boolean aBook,
                                         int priceHigherThan, int priceLowerThan, SortType sortType, String sortBy, int page)
    {
        int amountOfTypes = 0;
        if(!eBook && !pBack && aBook) amountOfTypes=1;
        if(!eBook && pBack && !aBook) amountOfTypes=1;
        if(eBook && !pBack && !aBook) amountOfTypes=1;
        if(eBook && pBack && !aBook) amountOfTypes=2;
        if(eBook && !pBack && aBook) amountOfTypes=2;
        if(!eBook && pBack && aBook) amountOfTypes=2;
        if(eBook && pBack && aBook) amountOfTypes=3;

        String query = "SELECT book.id, title, subtitle, isbn, price, book_type.name, description, author," +
                " date, language.name,";
        if(amountOfTypes == 3)
            query += " ebook.pages, ebook.file_size, paperback.pages, audiobook.length," +
                    " audiobook.file_size, audiobook.narrator";
        else if(amountOfTypes == 2) {
            if(eBook && pBack)
                query += " ebook.pages, ebook.file_size, paperback.pages";
            else if(eBook && aBook)
                query += " ebook.pages, ebook.file_size, audiobook.length, audiobook.file_size, audiobook.narrator";
            else if(pBack && aBook)
                query += " paperback.pages, audiobook.length, audiobook.file_size, audiobook.narrator";
        }
        else if(amountOfTypes == 1) {
            if(eBook)
                query += " ebook.pages, ebook.file_size";
            else if(pBack)
                query += " paperback.pages";
            else if(aBook)
                query += " audiobook.length, audiobook.file_size, audiobook.narrator";
        }
        query += " FROM book JOIN language ON book.language_id = language.id ";
        if(eBook) query += "LEFT JOIN ebook ON ebook.book_id = book.id ";
        if(pBack) query += "LEFT JOIN paperback ON paperback.book_id = book.id ";
        if(aBook) query += "LEFT JOIN audiobook ON audiobook.book_id = book.id ";
        query += "LEFT JOIN book_type ON book.type = book_type.id";
        query += " WHERE ";
        query += "title LIKE '%" + keyword + "%' AND ";
        if(amountOfTypes==3) query+= "( book_type.name='ebook' OR book_type.name='paperback' OR book_type.name='audiobook' )";
        else if(amountOfTypes==2)
        {

            if(eBook) { query+= "( book_type.name='ebook' OR"; eBook = false;}
            else if(pBack) { query+= "( book_type.name='paperback' OR"; pBack = false;}
            else if(aBook) { query+= "( book_type.name='audiobook' OR"; aBook = false;}
            if(eBook) query+= " book_type.name='ebook' )";
            else if(pBack) query+= " book_type.name='paperback' )";
            else if(aBook) query+= " book_type.name='audiobook' )";
        }
        else if(amountOfTypes==1)
        {
            if(eBook) query+= "book_type.name='ebook'";
            if(pBack) query+= "book_type.name='paperback'";
            if(aBook) query+= "book_type.name='audiobook'";
        }

        query+= " AND ";
        query+= "price>"+priceHigherThan + " AND price<" + priceLowerThan + " ";
        if(sortBy != null && sortType != null)
            query += "ORDER BY " + sortBy + " " + sortType.toString()+" ";
        query+= "LIMIT "+page*10;

        if(amountOfTypes == 0) //dummy query
            query = "SELECT book.id, title, subtitle, isbn, price, book_type.name, description, author, date, language.name " +
                    "FROM book JOIN language ON book.language_id = language.id LEFT JOIN book_type ON book.type = book_type.id " +
                    "WHERE title LIKE '%%' AND price>0 AND price<1000 ORDER BY price ASC LIMIT 0";
        System.out.println("Downloading books...");
        System.out.println("QUERY: "+query);

        ArrayList<Book> bookData = new ArrayList<Book>();
        start_time = System.nanoTime();

        int counter = 1;
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                Book book = new Book(resultSet.getString("title"),
                        resultSet.getString("subtitle"),
                        resultSet.getString("isbn"),
                        resultSet.getDouble("price"),
                        resultSet.getString("name"),
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getString("author"),
                        resultSet.getString("date"),
                        resultSet.getString("language.name"));

                if(resultSet.getString("name").equals("ebook"))
                    book.fillEbook(resultSet.getInt("ebook.pages"),
                            resultSet.getInt("ebook.file_size"));

                else if(resultSet.getString("name").equals("paperback"))
                    book.fillPaperback(resultSet.getInt("paperback.pages"));

                else if(resultSet.getString("name").equals("audiobook"))
                    book.fillAudiobook(resultSet.getDouble("audiobook.length"),
                            resultSet.getInt("audiobook.file_size"),
                            resultSet.getString("audiobook.narrator"));

                if(counter>page*10-10) bookData.add(book);
                counter++;
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return bookData;
    }

    //Done
    public ArrayList<String> downloadBookTypes()
    {
        ArrayList<String> bookTypes = new ArrayList<String>();
        String query = "SELECT * FROM book_type";

        System.out.println("Downloading book types...");
        System.out.println(query);
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                bookTypes.add(resultSet.getString("name"));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return bookTypes;
    }

    //Done
    public ArrayList<String> downloadLanguages()
    {

        ArrayList<String> languages = new ArrayList<String>();
        String query = "SELECT * FROM language";

        System.out.println("Downloading languages...");
        System.out.println("QUERY: " +query);
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                languages.add(resultSet.getString("name"));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return languages;

    }

    //Done
    public ArrayList<Review> downloadReviews(String ISBN)
    {
        ArrayList<Review> reviews = new ArrayList<Review>();
        String query = "SELECT review.description, rating FROM review " +
                "JOIN book ON book.id=review.book_id WHERE book.isbn='" + ISBN + "' LIMIT 10";

        System.out.println("Downloading reviews for a book...");
        System.out.println("QUERY: " + query);
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                reviews.add(new Review(resultSet.getInt("rating"),
                        resultSet.getString("description")));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return reviews;

    }

    //Done - to prevent ISBNs from repeating
    public boolean checkIfISBNAlreadyExists(String ISBN)
    {
        String query = "SELECT * FROM book WHERE isbn='" + ISBN + "'";

        System.out.println("Checking if ISBN in the database...");
        System.out.println(query);

        boolean itDoes = false;
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                itDoes = true;
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return itDoes;
    }

    //Done
    public void buyBook(Book book, int amount, int userID) {
        start_time = System.nanoTime();
        try {
            //Assume a valid connection object conn
            connection.setAutoCommit(false);

            String query0 = "SELECT publisher.id FROM publisher JOIN book ON publisher.id = book.publisher_id " +
                    "WHERE book.id='" + book.book_id + "'";
            System.out.println("QUERY: " + query0);
            ResultSet rs = statement.executeQuery(query0);
            rs.next();
            int publisher_id = rs.getInt("id");

            double royalty = amount * book.price;

            String query1 = "UPDATE publisher SET royalty = royalty + " + royalty +
                    " WHERE publisher.id=" + publisher_id;
            System.out.println("QUERY: " + query1);
            statement.executeUpdate(query1);

            String query2 = "UPDATE customer SET money = money - " + royalty +
                    " WHERE user_id=" + userID;
            System.out.println("QUERY: " + query2);
            statement.executeUpdate(query2);

            String query3 = "UPDATE inventory SET quantity = quantity - " + amount +
                    " WHERE book_id = " + book.book_id;
            System.out.println("QUERY: " + query3);
            statement.executeUpdate(query3);

            String query4 = "INSERT INTO sale (book_id, quantity, customer_id, time)" + " " +
                    "VALUES(" + book.book_id + ", " + amount + ", " + userID + ", '" +
                    new Timestamp(System.currentTimeMillis())+"')";
            System.out.println("QUERY: " + query4);
            statement.executeUpdate(query4);

            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("You bought a book");
        } catch (SQLException se) {
            System.out.println("ERROR: " + se.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
    }

    //Done
    public void leaveReview(int book_id, Review review)
    {
        String query = "INSERT INTO review (book_id, rating, description) VALUES(" +
                book_id + ", "+ review.rating +", '" + review.description + "')";

        System.out.println("Uploading a review for a book...");
        System.out.println("QUERY: " + query);
        start_time = System.nanoTime();
        try {
            statement.executeUpdate(query);
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
    }

    //Publisher---------------------------------------------------------------------------------
    //Done
    public void addEBook(String ISBN, String title, String subTitle, double price, String author,
                         int publisherID, int languageID, String description, int fileSize, int pages)
    {
        start_time = System.nanoTime();
        try {
            connection.setAutoCommit(false);
            int bookID = addBook(ISBN, title, subTitle, price, author, publisherID, languageID, description, 1);

            String eBookQuery = "INSERT INTO ebook (book_id, pages, file_size) VALUES (" + bookID + "," +
                    pages + "," + fileSize + ")";
            System.out.println("QUERY: " + eBookQuery);
            statement.executeUpdate(eBookQuery);
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("An eBook has been published");
        }
        catch (SQLException se) {
            System.out.println("ERROR: " + se.getMessage());
            try {
               connection.rollback();
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
    }
    //Done
    public void addPBackBook(String ISBN, String title, String subTitle, double price, String author,
                         int publisherID, int languageID, String description, int pages)
    {
        start_time = System.nanoTime();
        try {
            connection.setAutoCommit(false);
            int bookID = addBook(ISBN, title, subTitle, price, author, publisherID, languageID, description, 2);

            String pBackBookQuery = "INSERT INTO paperback (book_id, pages) VALUES (" + bookID + "," +
                    pages + ")";
            System.out.println("QUERY: " + pBackBookQuery);
            statement.executeUpdate(pBackBookQuery);
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("A paperback has been published");
        }
        catch (SQLException se) {
            System.out.println("ERROR: " + se.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
    }
    //Done
    public void addABook(String ISBN, String title, String subTitle, double price, String author,
                             int publisherID, int languageID, String description,
                         double length, String narrator, int fileSize)
    {
        start_time = System.nanoTime();
        try {
            connection.setAutoCommit(false);
            int bookID = addBook(ISBN, title, subTitle, price, author, publisherID, languageID, description, 3);

            String audiobookQuery = "INSERT INTO audiobook (book_id, length, file_size, narrator) VALUES ("
                    + bookID + "," + length + "," + fileSize + ",'" + narrator + "')";
            System.out.println("QUERY: " + audiobookQuery);
            statement.executeUpdate(audiobookQuery);
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("An audiobook has been published");
        }
        catch (SQLException se) {
            System.out.println("ERROR: " + se.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");

    }
    private int addBook(String ISBN, String title, String subTitle, double price, String author,
                        int publisherID, int languageID, String description, int type) throws SQLException
    {
        int bookID = 0;

        //Date - current
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        String bookQuery = "INSERT INTO book (isbn, title, subtitle, description, author, price, publisher_id, " +
                "date, type, language_id) VALUES ('" + ISBN + "','" + title + "','" + subTitle + "','" + description +
                "','" + author + "'," + price + "," + publisherID + ",'" + dateFormat.format(date) + "'," + type +
                "," + languageID + ")";

        String bookIdQuery = "SELECT id FROM book WHERE isbn = '" + ISBN + "'";

        if(checkIfISBNAlreadyExists(ISBN))
            throw new SQLException("This ISBN already exists.");

        System.out.println("QUERY: " + bookQuery);

        start_time = System.nanoTime();
        statement.executeUpdate(bookQuery);

        System.out.println("QUERY: " + bookIdQuery);
        ResultSet resultSet2 = statement.executeQuery(bookIdQuery);
        resultSet2.next();
        bookID = resultSet2.getInt("id");

        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");

        System.out.println("A Book has been published");

        return bookID;
    }

    //Done
    public ArrayList<Book> downloadPublishersBooks(int publisherID)
    {
        String query = "SELECT book.id, title, subtitle, isbn, price, book_type.name, description, author," +
                " date, language.name FROM book JOIN language ON book.language_id = language.id ";
        query += "LEFT JOIN ebook ON ebook.book_id = book.id ";
        query += "LEFT JOIN paperback ON paperback.book_id = book.id ";
        query += "LEFT JOIN audiobook ON audiobook.book_id = book.id ";
        query += "JOIN publisher ON book.publisher_id = publisher.id ";
        query += "LEFT JOIN book_type ON book.type = book_type.id";


        query += " WHERE ";
        query += "book.publisher_id=" + publisherID;

        System.out.println("Downloading books for publisher...");
        System.out.println("QUERY: "+query);

        ArrayList<Book> bookData = new ArrayList<Book>();
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                bookData.add(new Book(resultSet.getString("title"),
                        resultSet.getString("subtitle"),
                        resultSet.getString("isbn"),
                        resultSet.getDouble("price"),
                        resultSet.getString("name"),
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getString("author"),
                        resultSet.getString("date"),
                        resultSet.getString("language.name")));
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return bookData;
    }

    public double DownloadRoyalty(int publisherID)
    {
        String query = "SELECT royalty FROM publisher WHERE id="+publisherID;

        System.out.println("Downloading royalties for publisher...");
        System.out.println("QUERY: "+query);

        double royalty = 0;
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                royalty = resultSet.getDouble("royalty");
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return royalty;
    }

    public double downloadBookRoyalties(int bookID)
    {
        String query = "SELECT SUM(quantity*price) AS sm FROM sale JOIN book ON sale.book_id = book.id WHERE book.id=" +
                bookID;

        System.out.println("Downloading book royalties...");
        System.out.println("QUERY: "+query);

        double royalty = 0;
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                royalty = resultSet.getDouble("sm");
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return royalty;
    }

    public double downloadAverageRating(int bookID)
    {
        String query = "SELECT AVG(rating) AS rt FROM review WHERE book_id=" +
                bookID;

        System.out.println("Downloading book average rating..");
        System.out.println("QUERY: "+query);

        double avgRating = 0;
        start_time = System.nanoTime();
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                avgRating = resultSet.getDouble("rt");
            }
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
        return avgRating;
    }

    //Support------------------------------------------------------------------------------------
    //Done
    public void deleteBook(String bookID)
    {
        String query = "DELETE FROM book WHERE isbn = " + bookID;
        System.out.println("QUERY: " + query);
        start_time = System.nanoTime();
        try {
            statement.executeUpdate(query);
            System.out.println("A book has been deleted");
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");

    }
    //Done
    public void deleteReview(int reviewID)
    {
        String query = "DELETE FROM review WHERE id = " + reviewID;
        System.out.println("QUERY: " + query);
        start_time = System.nanoTime();
        try {
            statement.executeUpdate(query);
            System.out.println("A review has been deleted");
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
    }
    //dostawa książek
    //Done
    public void warehouseShipment(String ISBN, int amount)
    {
        String query = "UPDATE inventory JOIN book ON inventory.book_id = book.id SET quantity = quantity + " +
                amount + " WHERE isbn = '" + ISBN + "'";
        System.out.println("QUERY: " + query);
        start_time = System.nanoTime();
        try {
            statement.executeUpdate(query);
            System.out.println("An inventory has been updated");
        }
        catch(SQLException e) { System.out.println("ERROR: "+e.getMessage()); }
        end_time = System.nanoTime();
        System.out.println("Elapsed time: "+(end_time-start_time)/1000000+"ms");
    }

}
