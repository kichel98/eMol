package emol.windows;

import emol.Book;
import emol.Review;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    private JTextField amountTextField = new JTextField();
    private JTextField reviewTextField = new JTextField();
    private Integer[] ratingValues = {1, 2, 3, 4, 5};
    private JComboBox<Integer> ratingComboBox = new JComboBox<>(ratingValues);

    public Book book;
    public ArrayList<Review> reviews = new ArrayList<Review>();

    public BookWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the BookWindow...");

        JLabel titleLabel = new JLabel(book.title);
        titleLabel.setBackground(Color.BLACK);
        titleLabel.setForeground(Color.white);
        titleLabel.setOpaque(true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitleLabel = new JLabel(book.subtitle);
        subtitleLabel.setBackground(Color.BLACK);
        subtitleLabel.setForeground(Color.white);
        subtitleLabel.setOpaque(true);
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel amountLabel = new JLabel("Amount");
        amountLabel.setBackground(Color.BLACK);
        amountLabel.setForeground(Color.white);
        amountLabel.setOpaque(true);
        amountLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton buyBTN = new JButton("Buy");
        buyBTN.addActionListener(actionListener);
        buyBTN.setBackground(Color.BLACK);
        buyBTN.setForeground(Color.white);

        JButton backBTN = new JButton("Back");
        backBTN.addActionListener(actionListener);
        backBTN.setBackground(Color.BLACK);
        backBTN.setForeground(Color.white);

        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 20, 1));
        mainPanel.setPreferredSize(new Dimension(900, 750));
        mainPanel.setBackground(Color.DARK_GRAY);

        JPanel reviewsPanel = new JPanel(new GridLayout(10+reviews.size(), 1, 20, 1));
        reviewsPanel.setPreferredSize(new Dimension(500, 750));
        reviewsPanel.setBackground(Color.DARK_GRAY);

        JPanel leaveReviewPanel = new JPanel(new GridLayout(12, 1, 20, 1));
        leaveReviewPanel.setPreferredSize(new Dimension(500, 450));
        leaveReviewPanel.setBounds(new Rectangle(300, 450));
        leaveReviewPanel.setBackground(Color.DARK_GRAY);

        //Creating the Center Panel
        JPanel bookDetailsPanel = new JPanel(new GridLayout(12, 1, 20, 1));
        bookDetailsPanel.setPreferredSize(new Dimension(500, 750));
        bookDetailsPanel.setBackground(Color.DARK_GRAY);

        bookDetailsPanel.add(titleLabel);
        bookDetailsPanel.add(subtitleLabel);
        bookDetailsPanel.add(amountLabel);
        bookDetailsPanel.add(amountTextField);
        bookDetailsPanel.add(buyBTN);

        bookDetailsPanel.add(backBTN);

        mainPanel.add(bookDetailsPanel);

        //Reviews
        JLabel reviewslabel = new JLabel("Reviews");
        reviewslabel.setBackground(Color.BLACK);
        reviewslabel.setForeground(Color.white);
        reviewslabel.setOpaque(true);
        reviewslabel.setHorizontalAlignment(JLabel.CENTER);
        reviewsPanel.add(reviewslabel);

        for(int i=0; i<reviews.size(); i++)
        {
            JLabel reviewlabel = new JLabel("Rating:"+reviews.get(i).rating+". "+reviews.get(i).description);
            reviewlabel.setBackground(Color.BLACK);
            reviewlabel.setForeground(Color.white);
            reviewlabel.setOpaque(true);
            reviewlabel.setHorizontalAlignment(JLabel.CENTER);
            reviewsPanel.add(reviewlabel);
        }

        //Leave a review

        JLabel leaveAReviewLabel = new JLabel("Leave a review");
        leaveAReviewLabel.setBackground(Color.BLACK);
        leaveAReviewLabel.setForeground(Color.white);
        leaveAReviewLabel.setOpaque(true);
        leaveAReviewLabel.setHorizontalAlignment(JLabel.CENTER);
        leaveReviewPanel.add(leaveAReviewLabel);

        JLabel ratingLabel = new JLabel("Rating: 1 - 5");
        ratingLabel.setBackground(Color.BLACK);
        ratingLabel.setForeground(Color.white);
        ratingLabel.setOpaque(true);
        ratingLabel.setHorizontalAlignment(JLabel.CENTER);
        leaveReviewPanel.add(ratingLabel);
        leaveReviewPanel.add(ratingComboBox);

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setBackground(Color.BLACK);
        descriptionLabel.setForeground(Color.white);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);
        leaveReviewPanel.add(descriptionLabel);
        leaveReviewPanel.add(reviewTextField);

        JButton leaveReviewBTN = new JButton("Leave a Review");
        leaveReviewBTN.addActionListener(actionListener);
        leaveReviewBTN.setBackground(Color.BLACK);
        leaveReviewBTN.setForeground(Color.white);
        leaveReviewPanel.add(leaveReviewBTN);

        mainPanel.add(reviewsPanel);
        mainPanel.add(leaveReviewPanel);




        //Creating the main (full) window
        JPanel main = new JPanel();
        main.setBounds(0,0,1000,800);
        main.setLayout(new BorderLayout());
        main.add(mainPanel, BorderLayout.CENTER);

        mainFrame.add(main);

        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);

        //-----------------------
        //Reviews
        for(int i=0; i<reviews.size(); i++)
        {
            System.out.println("Rating: "+reviews.get(i).rating + ". "+reviews.get(i).description);
        }

    }

    public KeyboardInput getInput()
    {
        KeyboardInput ki = new KeyboardInput();
        if(!reviewTextField.getText().isEmpty())
            ki.review = new Review((int) ratingComboBox.getSelectedItem(), reviewTextField.getText());
        if(!amountTextField.getText().isEmpty()) ki.amount = Integer.parseInt(amountTextField.getText());
        return ki;
    }
}
