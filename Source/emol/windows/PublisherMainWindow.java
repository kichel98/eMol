package emol.windows;

import emol.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PublisherMainWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    public ArrayList<Book> books = new ArrayList<Book>();

    public PublisherMainWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the PublisherMainWindow...");

        JLabel salesLabel = new JLabel("Sales: $100");
        salesLabel.setBackground(Color.BLACK);
        salesLabel.setForeground(Color.white);
        salesLabel.setOpaque(true);
        salesLabel.setHorizontalAlignment(JLabel.CENTER);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(7, 1, 20, 1));
        panel.setBounds(0,0,500 ,800);
        panel.setPreferredSize(new Dimension(700, 550));
        panel.setBackground(Color.DARK_GRAY);

        panel.add(salesLabel);

        JButton addBookBTN = new JButton("Add A Book");
        addBookBTN.addActionListener(actionListener);
        addBookBTN.setBackground(Color.BLACK);
        addBookBTN.setForeground(Color.white);
        panel.add(addBookBTN);

        for(int i=0; i<books.size(); i++)
        {
            JButton bookBTN = new JButton("Book"+i+": "+books.get(i).title+": "+books.get(i).subtitle);
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
}
