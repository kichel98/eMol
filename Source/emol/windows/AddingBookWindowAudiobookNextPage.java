package emol.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddingBookWindowAudiobookNextPage {
    private ActionListener actionListener;
    private JFrame mainFrame;
    private JTextField lengthTextField = new JTextField();
    private JTextField narratorTextField = new JTextField();
    private JTextField filesizeTextField = new JTextField();

    public  AddingBookWindowAudiobookNextPage(ActionListener actionListener, JFrame frame)
    {
        this.actionListener = actionListener;
        this.mainFrame = frame;
    }

    public void display() {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().setBackground( java.awt.Color.DARK_GRAY );
        mainFrame.setLayout(new GridBagLayout());

        System.out.println("Drawing the AddingBookWindow...");

        JLabel lengthLabel = new JLabel("Length");
        lengthLabel.setBackground(Color.BLACK);
        lengthLabel.setForeground(Color.white);
        lengthLabel.setOpaque(true);
        lengthLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel narratorLabel = new JLabel("Narrator");
        narratorLabel.setBackground(Color.BLACK);
        narratorLabel.setForeground(Color.white);
        narratorLabel.setOpaque(true);
        narratorLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel filesizeLabel = new JLabel("File size");
        filesizeLabel.setBackground(Color.BLACK);
        filesizeLabel.setForeground(Color.white);
        filesizeLabel.setOpaque(true);
        filesizeLabel.setHorizontalAlignment(JLabel.CENTER);

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(11, 1, 20, 1));
        panel.setBounds(0,0,500 ,800);
        panel.setPreferredSize(new Dimension(700, 550));
        panel.setBackground(Color.DARK_GRAY);


        panel.add(lengthLabel);
        panel.add(lengthTextField);
        panel.add(narratorLabel);
        panel.add(narratorTextField);
        panel.add(filesizeLabel);
        panel.add(filesizeTextField);

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
            ki.length = Double.parseDouble(lengthTextField.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid argument: length must be a number");
            ki.length = -1;
        }
        ki.narrator = narratorTextField.getText();
        if(ki.narrator.isEmpty())
            ki.narrator = null;
        try {
            ki.fileSizeAudiobook = Integer.parseInt(filesizeTextField.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid argument: file size must be a number");
            ki.fileSizeAudiobook = -1;
        }

        return ki;
    }
}
