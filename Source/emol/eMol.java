package emol;

import emol.enums.SortType;
import emol.windows.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        MainWindow(JFrame frame)
        {
            this.frame = frame;

            loginWindow = new LoginWindow(this, frame);
            presentBooksWindow = new PresentBooksWindow(this, frame);
            bookWindow = new BookWindow(this, frame);
            publisherMainWindow = new PublisherMainWindow(this, frame);
            supportMainWindow = new SupportMainWindow(this, frame);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if( command.equals( "Log In" ))  {
                System.out.println("Logging to the system...");
                KeyboardInput keyboardInput = loginWindow.getInput();

                //Do debugowania - żeby szybciej logowanie szło
                //user = database.connect(keyboardInput.username, keyboardInput.password);
                //user = database.connect("publisher", "1234");
                user = database.connect("customer", "1234");
                //user = database.connect("support", "1234");

                if(user.type.equals("Customer")) presentBooksWindow.display();
                else if(user.type.equals("Publisher")) publisherMainWindow.display();
                else if(user.type.equals("Support")) supportMainWindow.display();

            } else if (command.equals( "Logout" )) {
                System.out.println("Logging out of the system...");
                loginWindow.display();

            } else if (command.equals( "Back" )) {
                System.out.println("Going back to the emol.windows.PresentBooksWindow...");
                presentBooksWindow.display();

            } else if( command.startsWith( "Book" ) )  {
                int i = new Scanner(command).useDelimiter("\\D+").nextInt();
                System.out.println("Showing book details");
                if(user.type.equals("Customer")) {
                    bookWindow.book = presentBooksWindow.books.get(i);
                    bookWindow.display();
                }

            } else if( command.startsWith( "Search" ) )  {
                System.out.println("Presenting books...");
                if(user.type.equals("Customer")) {
                    KeyboardInput keyboardInput = presentBooksWindow.getInput();
                    presentBooksWindow.books = database.downloadBooks(keyboardInput.keyword,
                            true, true, true,
                            0,  999999, SortType.ASC, 1);
                    presentBooksWindow.display();
                }


            } else if( command.startsWith( "Exit" ) )  {
                System.out.println("Exiting...");
                System.exit(0);
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