package emol.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;

    private JTextField loginTextField = new JTextField();
    private JTextField passwordTextField = new JTextField();

    public LoginWindow(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the LoginWindow...");

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setBackground(Color.BLACK);
        loginLabel.setForeground(Color.white);
        loginLabel.setOpaque(true);
        loginLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBackground(Color.BLACK);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setOpaque(true);
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);

        //Creating the Login Button
        JButton loginBTN = new JButton("Log In");
        loginBTN.addActionListener(actionListener);
        loginBTN.setBackground(Color.BLACK);
        loginBTN.setForeground(Color.white);

        //Creating the Login Button
        JButton exitBTN = new JButton("Exit");
        exitBTN.addActionListener(actionListener);
        exitBTN.setBackground(Color.BLACK);
        exitBTN.setForeground(Color.white);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(6, 1, 20, 1));
        panel.setPreferredSize(new Dimension(400, 350));
        panel.setBackground(Color.DARK_GRAY);

        panel.add(loginLabel);
        panel.add(loginTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(loginBTN);
        panel.add(exitBTN);

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
        keyboardInput.username = loginTextField.getText();
        if(keyboardInput.username.isEmpty())
            keyboardInput.username = null;
        keyboardInput.password = passwordTextField.getText();
        if(keyboardInput.password.isEmpty())
            keyboardInput.password = null;

        return keyboardInput;
    }

}
