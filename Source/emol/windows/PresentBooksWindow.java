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
    public int currentPage = 1;

    private ButtonGroup buttonGroup;
    private JRadioButton sortingByPriceASCButton = new JRadioButton("price ↑");
    private JRadioButton sortingByPriceDESCButton = new JRadioButton("price ↓");
    private JRadioButton sortingByTitleASCButton = new JRadioButton("title ↑");
    private JRadioButton sortingByTitleDESCButton = new JRadioButton("title ↓");
    private JLabel searchLabel = new JLabel("Text");
    private JLabel ebookLabel = new JLabel("ebook");
    private JLabel paperbackLabel = new JLabel("paperback");
    private JLabel audiobookLabel = new JLabel("audiobook");
    private JLabel priceHigherThanLabel = new JLabel("price >");
    private JLabel priceLowerThanLabel = new JLabel("price <");
    private JLabel overSearchButtonLabel = new JLabel();
    private JTextField searchBarTextField = new JTextField();
    private JCheckBox ebookCheckBox = new JCheckBox("", true);
    private JCheckBox paperbackCheckBox = new JCheckBox("", true);
    private JCheckBox audiobookCheckBox = new JCheckBox("", true);
    private JTextField priceHigherThanTextField = new JTextField("0");
    private JTextField priceLowerThanTextField = new JTextField("1000");

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

        //Creating the sorting bar
        JPanel sortingPanel = new JPanel(new GridLayout(1, 5));
        sortingPanel.setBounds(0, 0, 500, 50);
        sortingPanel.setPreferredSize(new Dimension(700, 50));
        sortingPanel.setBackground(Color.BLACK);

        JLabel sortingLabel = new JLabel("Sort by: ");
        sortingLabel.setForeground(Color.WHITE);

        sortingByPriceASCButton.setActionCommand(sortingByPriceASCButton.getText());
        sortingByPriceDESCButton.setActionCommand(sortingByPriceDESCButton.getText());
        sortingByTitleASCButton.setActionCommand(sortingByTitleASCButton.getText());
        sortingByTitleDESCButton.setActionCommand(sortingByTitleDESCButton.getText());

        buttonGroup = new ButtonGroup();
        buttonGroup.add(sortingByPriceASCButton);
        buttonGroup.add(sortingByPriceDESCButton);
        buttonGroup.add(sortingByTitleASCButton);
        buttonGroup.add(sortingByTitleDESCButton);

        sortingPanel.add(sortingLabel);
        sortingPanel.add(sortingByPriceASCButton);
        sortingPanel.add(sortingByPriceDESCButton);
        sortingPanel.add(sortingByTitleASCButton);
        sortingPanel.add(sortingByTitleDESCButton);

        //Creating the label bar
        JPanel labelPanel = new JPanel(new GridLayout(1, 7));
        labelPanel.setBounds(0, 0, 500, 50);
        labelPanel.setPreferredSize(new Dimension(700, 50));
        labelPanel.setBackground(Color.BLACK);

        searchLabel.setForeground(Color.WHITE);
        ebookLabel.setForeground(Color.WHITE);
        paperbackLabel.setForeground(Color.WHITE);
        audiobookLabel.setForeground(Color.WHITE);
        priceLowerThanLabel.setForeground(Color.WHITE);
        priceHigherThanLabel.setForeground(Color.WHITE);

        labelPanel.add(searchLabel);
        labelPanel.add(ebookLabel);
        labelPanel.add(paperbackLabel);
        labelPanel.add(audiobookLabel);
        labelPanel.add(priceHigherThanLabel);
        labelPanel.add(priceLowerThanLabel);
        labelPanel.add(overSearchButtonLabel);

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
        searchBarPanel.add(ebookCheckBox);
        searchBarPanel.add(paperbackCheckBox);
        searchBarPanel.add(audiobookCheckBox);
        searchBarPanel.add(priceHigherThanTextField);
        searchBarPanel.add(priceLowerThanTextField);
        searchBarPanel.add(searchBTN);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(16, 1, 20, 1));
        panel.setBounds(0,0,500 ,800);
        panel.setPreferredSize(new Dimension(700, 750));
        panel.setBackground(Color.DARK_GRAY);

        panel.add(sortingPanel);
        panel.add(labelPanel);
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

        JButton nextPageBTN = new JButton("->");
        nextPageBTN.addActionListener(actionListener);
        nextPageBTN.setBackground(Color.BLACK);
        nextPageBTN.setForeground(Color.white);
        panel.add(nextPageBTN);

        JButton previousPageBTN = new JButton("<-");
        previousPageBTN.addActionListener(actionListener);
        previousPageBTN.setBackground(Color.BLACK);
        previousPageBTN.setForeground(Color.white);
        panel.add(previousPageBTN);

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
        if( ebookCheckBox.isSelected()) keyboardInput.ebook = true;
        if( paperbackCheckBox.isSelected()) keyboardInput.paperback = true;
        if( audiobookCheckBox.isSelected()) keyboardInput.audiobook = true;
        keyboardInput.priceHigherThan = Integer.parseInt(priceHigherThanTextField.getText());
        keyboardInput.priceLowerThan = Integer.parseInt(priceLowerThanTextField.getText());
        if(buttonGroup.getSelection() != null)
            keyboardInput.sortingBy = buttonGroup.getSelection().getActionCommand();
        return keyboardInput;
    }
}
