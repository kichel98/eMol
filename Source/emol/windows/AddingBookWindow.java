package emol.windows;

import emol.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddingBookWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    private JTextField titleTextField = new JTextField();
    private JTextField subtitleTextField = new JTextField();
    private JTextField priceTextField = new JTextField();
    private JTextField authorTextField = new JTextField();
    private JTextField descriptionTextField = new JTextField();
    private JTextField ISBNTextField = new JTextField();
    public JComboBox<String> languageComboBox;

    private String book_types[] = {"ebook", "paperback", "audiobook"};
    private JComboBox<String> book_typesComboBox = new JComboBox<>(book_types);

    public AddingBookWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the AddingBookWindow...");

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.white);
        titleLabel.setOpaque(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitleLabel = new JLabel("Subtitle");
        subtitleLabel.setBackground(Color.BLACK);
        subtitleLabel.setForeground(Color.white);
        subtitleLabel.setOpaque(true);
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel priceLabel = new JLabel("Price");
        priceLabel.setBackground(Color.BLACK);
        priceLabel.setForeground(Color.white);
        priceLabel.setOpaque(true);
        priceLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel authorLabel = new JLabel("Author");
        authorLabel.setBackground(Color.BLACK);
        authorLabel.setForeground(Color.white);
        authorLabel.setOpaque(true);
        authorLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBackground(Color.BLACK);
        descriptionLabel.setForeground(Color.white);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel ISBNLabel = new JLabel("ISBN");
        ISBNLabel.setBackground(Color.BLACK);
        ISBNLabel.setForeground(Color.white);
        ISBNLabel.setOpaque(true);
        ISBNLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel languageLabel = new JLabel("Language");
        languageLabel.setBackground(Color.BLACK);
        languageLabel.setForeground(Color.white);
        languageLabel.setOpaque(true);
        languageLabel.setHorizontalAlignment(JLabel.CENTER);

        // languageComboBox has to connect with Database
        // here I manually call actionPerformed to get all languages
        actionListener.actionPerformed(new ActionEvent(this,
                ActionEvent.ACTION_PERFORMED, "Get languages"));

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(13, 1, 20, 1));
        panel.setBounds(0,0,500 ,800);
        panel.setPreferredSize(new Dimension(700, 550));
        panel.setBackground(Color.DARK_GRAY);


        panel.add(titleLabel);
        panel.add(titleTextField);
        panel.add(subtitleLabel);
        panel.add(subtitleTextField);
        panel.add(authorLabel);
        panel.add(authorTextField);
        panel.add(priceLabel);
        panel.add(priceTextField);
        panel.add(descriptionLabel);
        panel.add(descriptionTextField);
        panel.add(ISBNLabel);
        panel.add(ISBNTextField);
        panel.add(languageLabel);
        panel.add(languageComboBox);
        panel.add(book_typesComboBox);


        JButton nextPageBTN = new JButton("Next Page");
        nextPageBTN.addActionListener(actionListener);
        nextPageBTN.setBackground(Color.BLACK);
        nextPageBTN.setForeground(Color.white);
        panel.add(nextPageBTN);


        JButton backBTN = new JButton("Back");
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
        KeyboardInput ki = new KeyboardInput();
        ki.title = titleTextField.getText();
        if(ki.title.isEmpty())
            ki.title = null;
        ki.subtitle = subtitleTextField.getText();
        if(ki.subtitle.isEmpty())
            ki.subtitle = null;
        try {
            ki.price = Double.parseDouble(priceTextField.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid argument: price must be a number");
            ki.price = -1;
        }
        ki.author = authorTextField.getText();
        if(ki.author.isEmpty())
            ki.author = null;
        ki.description = descriptionTextField.getText();
        if(ki.description.isEmpty())
            ki.description = null;
        ki.book_type = book_types[book_typesComboBox.getSelectedIndex()];
        ki.ISBN = ISBNTextField.getText();
        if(ki.ISBN.isEmpty())
            ki.ISBN = null;
        ki.languageID = languageComboBox.getSelectedIndex() + 1;

        return ki;
    }
}
