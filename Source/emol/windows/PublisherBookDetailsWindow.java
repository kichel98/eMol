package emol.windows;

import emol.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PublisherBookDetailsWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    public Book book;
    public double royalty= 100;
    public double avgRating = 0;

    public PublisherBookDetailsWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the PublisherBookDetailsWindow...");

        JLabel salesLabel = new JLabel("Sales: $"+royalty);
        salesLabel.setBackground(Color.BLACK);
        salesLabel.setForeground(Color.white);
        salesLabel.setOpaque(true);
        salesLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel avgRatingLabel = new JLabel("Average rating: "+avgRating);
        avgRatingLabel.setBackground(Color.BLACK);
        avgRatingLabel.setForeground(Color.white);
        avgRatingLabel.setOpaque(true);
        avgRatingLabel.setHorizontalAlignment(JLabel.CENTER);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(7, 1, 20, 1));
        panel.setBounds(0,0,500 ,800);
        panel.setPreferredSize(new Dimension(700, 550));
        panel.setBackground(Color.DARK_GRAY);

        panel.add(salesLabel);
        panel.add(avgRatingLabel);

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
