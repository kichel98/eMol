package emol.windows;

import emol.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddingBookWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    private JTextField titleTextField = new JTextField();
    private JTextField subtitleTextField = new JTextField();
    private JTextField priceTextField = new JTextField();
    private JTextField authorTextField = new JTextField();
    private JTextField descriptionTextField = new JTextField();

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

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(11, 1, 20, 1));
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


        JButton addBookBTN = new JButton("Publish");
        addBookBTN.addActionListener(actionListener);
        addBookBTN.setBackground(Color.BLACK);
        addBookBTN.setForeground(Color.white);
        panel.add(addBookBTN);


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
}
