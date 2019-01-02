package emol.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SupportMainWindow {
    private ActionListener actionListener;
    private JFrame mainFrame;
    //private GameInfo gameInfo = new GameInfo();

    private JTextField deleteBookTextField = new JTextField();
    private JTextField deleteReviewTextField = new JTextField();
    private JTextField warehouseShipmentISBN = new JTextField("ISBN");
    private JTextField warehouseShipmentAmount = new JTextField("amount");

    public SupportMainWindow(ActionListener actionListener, JFrame frame)
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

        //Creating the WarehouseShipment Button
        JButton warehouseShipmentBTN = new JButton("Warehouse Shipment");
        warehouseShipmentBTN.addActionListener(actionListener);
        warehouseShipmentBTN.setBackground(Color.BLACK);
        warehouseShipmentBTN.setForeground(Color.white);

        //Creating Backup Button
        JButton backupBTN = new JButton("Backup");
        backupBTN.addActionListener(actionListener);
        backupBTN.setBackground(Color.BLACK);
        backupBTN.setForeground(Color.white);

        //Creating Restore Button
        JButton restoreBTN = new JButton("Restore");
        restoreBTN.addActionListener(actionListener);
        restoreBTN.setBackground(Color.BLACK);
        restoreBTN.setForeground(Color.white);


        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(10, 1, 20, 1));
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
        panel.add(warehouseShipmentBTN);
        panel.add(warehouseShipmentISBN);
        panel.add(warehouseShipmentAmount);
        panel.add(backupBTN);
        panel.add(restoreBTN);
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
    public KeyboardInput getInput() throws NumberFormatException
    {
        KeyboardInput keyboardInput = new KeyboardInput();
        keyboardInput.toDeleteISBN = deleteBookTextField.getText();
        try {
            if(deleteReviewTextField.getText().equals("") == false)
                keyboardInput.reviewID = Integer.parseInt(deleteReviewTextField.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("You need to enter review ID (number) to delete it");
        }

        if(warehouseShipmentISBN.getText().equals("ISBN") == false)
            keyboardInput.toShipmentISBN = warehouseShipmentISBN.getText();

        try {
            if(warehouseShipmentAmount.getText().equals("amount") == false)
                keyboardInput.toShipmentAmount = Integer.parseInt(warehouseShipmentAmount.getText());
        }
        catch (NumberFormatException ex) {
            System.out.println("You need to enter amount of books (number) to make shipment");
        }


        return keyboardInput;
    }
}
