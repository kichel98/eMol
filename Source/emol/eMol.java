package emol;

import emol.enums.SortType;
import emol.windows.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class eMol {
    public static void main( String[] args ) {
        JFrame frame = new JFrame("eMol");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize(new Dimension(1000,800));
        frame.setResizable(false);


        MainWindow mainWindow = new MainWindow(frame);
        mainWindow.start();
    }

    public static class MainWindow extends JFrame implements ActionListener {
        JFrame frame;
        User user = new User();
        Database database = new Database();

        LoginWindow loginWindow;
        PresentBooksWindow presentBooksWindow;
        BookWindow bookWindow;
        PublisherMainWindow publisherMainWindow;
        SupportMainWindow supportMainWindow;
        AddingBookWindow addingBookWindow;

        AddingBookWindowEbookNextPage addingBookWindowEbookNextPage;
        AddingBookWindowPaperbackNextPage addingBookWindowPaperbackNextPage;
        AddingBookWindowAudiobookNextPage addingBookWindowAudiobookNextPage;

        MainWindow(JFrame frame)
        {
            this.frame = frame;

            loginWindow = new LoginWindow(this, frame);
            presentBooksWindow = new PresentBooksWindow(this, frame);
            bookWindow = new BookWindow(this, frame);
            publisherMainWindow = new PublisherMainWindow(this, frame);
            supportMainWindow = new SupportMainWindow(this, frame);
            addingBookWindow = new AddingBookWindow(this, frame);

            addingBookWindowEbookNextPage = new AddingBookWindowEbookNextPage(this, frame);
            addingBookWindowPaperbackNextPage = new AddingBookWindowPaperbackNextPage(this, frame);
            addingBookWindowAudiobookNextPage = new AddingBookWindowAudiobookNextPage(this, frame);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if( command.equals( "Log In" ))  {
                System.out.println("Logging into the system...");
                KeyboardInput keyboardInput = loginWindow.getInput();

                //Do debugowania - żeby szybciej logowanie szło
                user = database.connect(keyboardInput.username, keyboardInput.password);
                //user = database.connect("publisher", "1234");
                //user = database.connect("customer", "1234");
                //user = database.connect("support", "1234");
                if(user != null && user.type.equals("Customer")) presentBooksWindow.display();
                else if(user != null && user.type.equals("Publisher")) {
                    publisherMainWindow.royalty = database.DownloadRoyalty(user.publisherID);
                    publisherMainWindow.books = database.downloadPublishersBooks(user.publisherID);
                    publisherMainWindow.display();
                }
                else if(user != null && user.type.equals("Support")) supportMainWindow.display();

            } else if (command.equals( "Logout" )) {
                System.out.println("Logging out of the system...");
                loginWindow.display();

            } else if (command.equals( "Back" )) {
                System.out.println("Going back...");
                System.out.println(user.type);
                if(user.type.equals("Customer")) presentBooksWindow.display();
                else if(user.type.equals("Publisher")) publisherMainWindow.display();

            } else if( command.startsWith( "Book" ) )  {
                int i = new Scanner(command).useDelimiter("\\D+").nextInt();
                System.out.println("Showing book details");
                if(user.type.equals("Customer")) {
                    bookWindow.book = presentBooksWindow.books.get(i);
                    bookWindow.reviews = database.downloadReviews(bookWindow.book.isbn);
                    bookWindow.display();
                }
                else if(user.type.equals("Publisher"))
                {
                    //TODO: displaying book info for publisher
                }

            } else if( command.equals("Buy") )  {
                Book book = bookWindow.book;
                KeyboardInput KI = bookWindow.getInput();
                database.buyBook(book, KI.amount, user.ID);

            } else if( command.equals( "Search" ) )  {
                System.out.println("Presenting books...");
                if(user.type.equals("Customer")) {
                    KeyboardInput keyboardInput = presentBooksWindow.getInput();
                    SortType sortType = null;
                    String sortBy = null;
                    if(keyboardInput.sortingBy != null) {
                        if(keyboardInput.sortingBy.endsWith("↑"))
                            sortType = SortType.ASC;
                        else if(keyboardInput.sortingBy.endsWith("↓"))
                            sortType = SortType.DESC;
                        if(keyboardInput.sortingBy.startsWith("price"))
                            sortBy = "price";
                        else if(keyboardInput.sortingBy.startsWith("title"))
                            sortBy = "title";
                    }
                    presentBooksWindow.books = database.downloadBooks(keyboardInput.keyword,
                            keyboardInput.ebook, keyboardInput.paperback, keyboardInput.audiobook,
                            keyboardInput.priceHigherThan,  keyboardInput.priceLowerThan, sortType, sortBy, 1);
                    System.out.println("Number of books: "+presentBooksWindow.books.size());
                    presentBooksWindow.display();
                }

            } else if( command.equals( "Leave a Review" ) )  {
                System.out.println("Leaving a Review...");
                if(user.type.equals("Customer")) {
                    KeyboardInput keyboardInput = bookWindow.getInput();
                    database.leaveReview(bookWindow.book.book_id, keyboardInput.review);

                    bookWindow.reviews = database.downloadReviews(bookWindow.book.isbn);
                    bookWindow.display();
                }

            } else if( command.equals( "Add A Book" ) )  {
                addingBookWindow.display();

            } else if( command.equals( "Next Page" ) )  {
                try {
                    KeyboardInput ki = addingBookWindow.getInput();
                    if(ki.book_type.equals("ebook")) addingBookWindowEbookNextPage.display();
                    else if(ki.book_type.equals("paperback")) addingBookWindowPaperbackNextPage.display();
                    else if(ki.book_type.equals("audiobook")) addingBookWindowAudiobookNextPage.display();
                }
                catch(NumberFormatException ex)
                {
                    System.out.println("Price must be a number");
                }

            } else if( command.equals( "Publish" ) )  {
                KeyboardInput k1 = addingBookWindow.getInput();
                KeyboardInput k2 = new KeyboardInput();
                if(k1.book_type.equals("ebook")) k2 = addingBookWindowEbookNextPage.getInput();
                else if(k1.book_type.equals("paperback")) k2 = addingBookWindowPaperbackNextPage.getInput();
                else if(k1.book_type.equals("audiobook")) k2 = addingBookWindowAudiobookNextPage.getInput();

                if(k1.book_type.equals("ebook"))
                    database.addEBook(k1.ISBN, k1.title, k1.subtitle,
                        k1.price, k1.author, user.publisherID, k1.languageID,
                        k1.description, k2.fileSizeEbook, k2.pagesEbook);
                else if(k1.book_type.equals("paperback"))
                    database.addPBackBook(k1.ISBN, k1.title, k1.subtitle,
                        k1.price, k1.author, user.publisherID, k1.languageID,
                            k1.description, k2.pagesPaperback);
                else if(k1.book_type.equals("audiobook"))
                    database.addABook(k1.ISBN, k1.title, k1.subtitle,
                            k1.price, k1.author, user.publisherID, k1.languageID,
                            k1.description, k2.length, k2.narrator, k2.fileSizeAudiobook);

            } else if( command.equals("Back to details") )  {
                addingBookWindow.display();

            } else if( command.equals( "Exit" ) )  {
                System.out.println("Exiting...");
                System.exit(0);

            } else if( command.equals( "Get languages" ) )  {
                ArrayList<String> languagesArrayList = database.downloadLanguages();
                String[] languagesArray = languagesArrayList.toArray(new String[0]);
                addingBookWindow.languageComboBox = new JComboBox<String>(languagesArray);

            } else if( command.equals( "Delete Book" ) ) {
                KeyboardInput ki = supportMainWindow.getInput();
                database.deleteBook(ki.toDeleteISBN);

            } else if( command.equals( "Delete Review") ) {
                KeyboardInput ki = supportMainWindow.getInput();
                database.deleteReview(ki.reviewID);

            } else if( command.equals( "Warehouse Shipment") ) {
                KeyboardInput ki = supportMainWindow.getInput();
                database.warehouseShipment(ki.toShipmentISBN, ki.toShipmentAmount);

            } else if( command.equals( "Backup" )) {
                Process process = null;
                Runtime runtime = Runtime.getRuntime();
                // it may be a problem if somebody has different path to mysqldump :/
                String path = "C:/Program Files/MySQL/MySQL Server 8.0/bin/mysqldump.exe";
                String executeCmd = path + " -u " + user.username + " -p" + user.password + " -h localhost " +
                        "--add-drop-database --routines --databases eMol -r backup.sql";
                System.out.println(executeCmd);
                try {
                    process = runtime.exec(executeCmd);
                    int processComplete = process.waitFor();
                    if(processComplete == 0)
                        System.out.println("Backup created successfully");
                    else
                        System.out.println("Error during creating backup");
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else if( command.equals( "Restore" )) {
                Process process = null;
                Runtime runtime = Runtime.getRuntime();
                String path = "C:/Program Files/MySQL/MySQL Server 8.0/bin/mysql.exe";
                String[] executeCmd = {path, "--user=" + user.username, "--password=" + user.password,
                        "-e","source " + "backup.sql"};
                System.out.println(Arrays.toString(executeCmd));
                try {
                    process = runtime.exec(executeCmd);
                    int processComplete = process.waitFor();
                    if(processComplete == 0)
                        System.out.println("Restored successfully");
                    else
                        System.out.println("Error during restoring");
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            else {
                System.out.println("Clicked unknown button");
            }

        }

        void start()
        {
            loginWindow.display();
        }

    }
}
