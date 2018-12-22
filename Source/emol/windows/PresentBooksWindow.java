package emol.windows;

import emol.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PresentBooksWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    public ArrayList<Book> books = new ArrayList<Book>();

    private JTextField searchBarTextField = new JTextField();
    private JTextField ebookTextField = new JTextField("ebook?");
    private JTextField paperbackTextField = new JTextField("paperback?");
    private JTextField audiobookTextField = new JTextField("audiobook?");
    private JTextField priceLowerThanTextField = new JTextField("price<");
    private JTextField priceHigherThanTextField = new JTextField("price>");

    public PresentBooksWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the PresentBooksWindow...");
        for(int i=0; i<books.size(); i++)
        {
            System.out.println("ISBN: "+books.get(i).isbn+ " Title: "+books.get(i).title);
        }

        //Creating the search bar
        JPanel searchBarPanel = new JPanel(new GridLayout(1, 7, 0, 1));
        searchBarPanel.setBounds(0,0,500 ,50);
        searchBarPanel.setPreferredSize(new Dimension(700, 50));
        searchBarPanel.setBackground(Color.DARK_GRAY);

        JButton searchBTN = new JButton("Search");
        searchBTN.addActionListener(actionListener);
        searchBTN.setBackground(Color.BLACK);
        searchBTN.setForeground(Color.white);

        searchBarPanel.add(searchBarTextField);
        searchBarPanel.add(ebookTextField);
        searchBarPanel.add(paperbackTextField);
        searchBarPanel.add(audiobookTextField);
        searchBarPanel.add(priceHigherThanTextField);
        searchBarPanel.add(priceLowerThanTextField);
        searchBarPanel.add(searchBTN);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(11, 1, 20, 1));
        panel.setBounds(0,0,500 ,800);
        panel.setPreferredSize(new Dimension(700, 750));
        panel.setBackground(Color.DARK_GRAY);


        panel.add(searchBarPanel);

        for(int i=0; i<books.size(); i++)
        {
            JButton bookBTN = new JButton("Book"+i+": "+books.get(i).title+": "+
                    books.get(i).subtitle+" Price: "+books.get(i).price + " Type: "+ books.get(i).type);
            bookBTN.addActionListener(actionListener);
            bookBTN.setBackground(Color.BLACK);
            bookBTN.setForeground(Color.white);
            panel.add(bookBTN);
        }

        JButton backBTN = new JButton("Logout");
        backBTN.addActionListener(actionListener);
        backBTN.setBackground(Color.BLACK);
        backBTN.setForeground(Color.white);
        panel.add(backBTN);

        //Creating the main (full) window
        JPanel main = new JPanel();
        main.setBounds(0,0,1000,800);
        main.setLayout(new BorderLayout());
        main.add(panel, BorderLayout.CENTER);

        mainFrame.add(main);

        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);

    }

    public KeyboardInput getInput()
    {
        KeyboardInput keyboardInput = new KeyboardInput();
        keyboardInput.keyword = searchBarTextField.getText();
        if( ebookTextField.getText().equals("1")) keyboardInput.ebook = true;
        if( paperbackTextField.getText().equals("1")) keyboardInput.paperback = true;
        if( audiobookTextField.getText().equals("1")) keyboardInput.audiobook = true;
        keyboardInput.priceHigherThan = Integer.parseInt(priceHigherThanTextField.getText());
        keyboardInput.priceLowerThan = Integer.parseInt(priceLowerThanTextField.getText());
        return keyboardInput;
    }
}
