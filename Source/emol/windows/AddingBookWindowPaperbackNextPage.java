package emol.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddingBookWindowPaperbackNextPage {
    private ActionListener actionListener;
    private JFrame mainFrame;
    private JTextField pagesTextField = new JTextField();

    public  AddingBookWindowPaperbackNextPage(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the AddingBookWindow...");

        JLabel pagesLabel = new JLabel("Pages");
        pagesLabel.setBackground(Color.BLACK);
        pagesLabel.setForeground(Color.white);
        pagesLabel.setOpaque(true);
        pagesLabel.setHorizontalAlignment(JLabel.CENTER);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(11, 1, 20, 1));
        panel.setBounds(0,0,500 ,800);
        panel.setPreferredSize(new Dimension(700, 550));
        panel.setBackground(Color.DARK_GRAY);


        panel.add(pagesLabel);
        panel.add(pagesTextField);

        JButton backToDetailsBTN = new JButton("Back to details");
        backToDetailsBTN.addActionListener(actionListener);
        backToDetailsBTN.setBackground(Color.BLACK);
        backToDetailsBTN.setForeground(Color.white);
        panel.add(backToDetailsBTN);

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

    public KeyboardInput getInput()
    {
        KeyboardInput ki = new KeyboardInput();
        try {
            ki.pagesPaperback = Integer.parseInt(pagesTextField.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid argument: pages must be a number");
            ki.pagesPaperback = -1;
        }

        return ki;
    }
}
