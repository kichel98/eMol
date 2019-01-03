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

        JLabel descriptionLabel = new JLabel(book.description);
        descriptionLabel.setBackground(Color.BLACK);
        descriptionLabel.setForeground(Color.white);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel authorLabel = new JLabel("Author: " + book.author);
        authorLabel.setBackground(Color.BLACK);
        authorLabel.setForeground(Color.white);
        authorLabel.setOpaque(true);
        authorLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel dateLabel = new JLabel("Date: " + book.date);
        dateLabel.setBackground(Color.BLACK);
        dateLabel.setForeground(Color.white);
        dateLabel.setOpaque(true);
        dateLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel languageLabel = new JLabel("Language: " + book.language);
        languageLabel.setBackground(Color.BLACK);
        languageLabel.setForeground(Color.white);
        languageLabel.setOpaque(true);
        languageLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel pagesEbookLabel = null;
        JLabel fileSizeEbookLabel = null;
        JLabel pagesPaperbackLabel = null;
        JLabel lengthAudiobookLabel = null;
        JLabel fileSizeAudiobookLabel = null;
        JLabel narratorAudiobookLabel = null;

        if(book.type.equals("ebook")) {
            pagesEbookLabel = new JLabel("Pages: " + Integer.toString(book.pagesEbook));
            pagesEbookLabel.setBackground(Color.BLACK);
            pagesEbookLabel.setForeground(Color.white);
            pagesEbookLabel.setOpaque(true);
            pagesEbookLabel.setHorizontalAlignment(JLabel.CENTER);

            fileSizeEbookLabel = new JLabel("File size: " + Integer.toString(book.fileSizeEbook));
            fileSizeEbookLabel.setBackground(Color.BLACK);
            fileSizeEbookLabel.setForeground(Color.white);
            fileSizeEbookLabel.setOpaque(true);
            fileSizeEbookLabel.setHorizontalAlignment(JLabel.CENTER);

        } else if(book.type.equals("paperback")) {
            pagesPaperbackLabel = new JLabel("Pages: " + Integer.toString(book.pagesPaperback));
            pagesPaperbackLabel.setBackground(Color.BLACK);
            pagesPaperbackLabel.setForeground(Color.white);
            pagesPaperbackLabel.setOpaque(true);
            pagesPaperbackLabel.setHorizontalAlignment(JLabel.CENTER);


        } else if(book.type.equals("audiobook")) {
            lengthAudiobookLabel = new JLabel("Length: " + Double.toString(book.lengthAudiobook));
            lengthAudiobookLabel.setBackground(Color.BLACK);
            lengthAudiobookLabel.setForeground(Color.white);
            lengthAudiobookLabel.setOpaque(true);
            lengthAudiobookLabel.setHorizontalAlignment(JLabel.CENTER);

            fileSizeAudiobookLabel = new JLabel("File size: " + Integer.toString(book.fileSizeAudiobook));
            fileSizeAudiobookLabel.setBackground(Color.BLACK);
            fileSizeAudiobookLabel.setForeground(Color.white);
            fileSizeAudiobookLabel.setOpaque(true);
            fileSizeAudiobookLabel.setHorizontalAlignment(JLabel.CENTER);

            narratorAudiobookLabel = new JLabel("Narrator: " + book.narratorAudiobook);
            narratorAudiobookLabel.setBackground(Color.BLACK);
            narratorAudiobookLabel.setForeground(Color.white);
            narratorAudiobookLabel.setOpaque(true);
            narratorAudiobookLabel.setHorizontalAlignment(JLabel.CENTER);
        }

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
        JPanel bookDetailsPanel = new JPanel(new GridLayout(13, 1, 20, 1));
        bookDetailsPanel.setPreferredSize(new Dimension(500, 750));
        bookDetailsPanel.setBackground(Color.DARK_GRAY);

        bookDetailsPanel.add(titleLabel);
        bookDetailsPanel.add(subtitleLabel);
        bookDetailsPanel.add(descriptionLabel);
        bookDetailsPanel.add(authorLabel);
        bookDetailsPanel.add(dateLabel);
        bookDetailsPanel.add(languageLabel);
        if(book.type.equals("ebook")) {
            bookDetailsPanel.add(pagesEbookLabel);
            bookDetailsPanel.add(fileSizeEbookLabel);

        } else if(book.type.equals("paperback")) {
            bookDetailsPanel.add(pagesPaperbackLabel);

        } else if(book.type.equals("audiobook")) {
            bookDetailsPanel.add(lengthAudiobookLabel);
            bookDetailsPanel.add(fileSizeAudiobookLabel);
            bookDetailsPanel.add(narratorAudiobookLabel);
        }
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

        JLabel descriptionReviewLabel = new JLabel("Description");
        descriptionReviewLabel.setBackground(Color.BLACK);
        descriptionReviewLabel.setForeground(Color.white);
        descriptionReviewLabel.setOpaque(true);
        descriptionReviewLabel.setHorizontalAlignment(JLabel.CENTER);
        leaveReviewPanel.add(descriptionReviewLabel);
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
        else
            ki.review = new Review((int) ratingComboBox.getSelectedItem(), null);
        try {
            ki.amount = Integer.parseInt(amountTextField.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid argument: amount must be a number");
            ki.amount = -1;
        }
        return ki;
    }
}
