import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SupportMainWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    //private GameInfo gameInfo = new GameInfo();

    private JTextField deleteBookTextField = new JTextField();
    private JTextField deleteReviewTextField = new JTextField();

    SupportMainWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the SupportMainWindow...");

        //Creating the Login Button
        JButton deleteBookBTN = new JButton("Delete Book");
        deleteBookBTN.addActionListener(actionListener);
        deleteBookBTN.setBackground(Color.BLACK);
        deleteBookBTN.setForeground(Color.white);

        //Creating the Login Button
        JButton deleteReviewBTN = new JButton("Delete Review");
        deleteReviewBTN.addActionListener(actionListener);
        deleteReviewBTN.setBackground(Color.BLACK);
        deleteReviewBTN.setForeground(Color.white);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(6, 1, 20, 1));
        panel.setPreferredSize(new Dimension(300, 250));
        panel.setBackground(Color.DARK_GRAY);

        JButton backBTN = new JButton("Logout");
        backBTN.addActionListener(actionListener);
        backBTN.setBackground(Color.BLACK);
        backBTN.setForeground(Color.white);


        panel.add(deleteBookBTN);
        panel.add(deleteBookTextField);
        panel.add(deleteReviewBTN);
        panel.add(deleteReviewTextField);
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
