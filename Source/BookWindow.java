import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BookWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    private JTextField amountTextField = new JTextField();

    BookWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the LoginWindow...");

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

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(6, 1, 20, 1));
        panel.setPreferredSize(new Dimension(300, 250));
        panel.setBackground(Color.DARK_GRAY);

        panel.add(titleLabel);
        panel.add(subtitleLabel);
        panel.add(amountLabel);
        panel.add(amountTextField);
        panel.add(buyBTN);
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
