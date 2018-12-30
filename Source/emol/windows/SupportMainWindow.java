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

        //Creating the Center Panel
        JPanel panel = new JPanel(new GridLayout(8, 1, 20, 1));
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
        KeyboardInput keyboardInput = new KeyboardInput();
        keyboardInput.toDeleteISBN = deleteBookTextField.getText();
        if(deleteReviewTextField.getText().equals("") == false) //now, later it will be try-catch
            keyboardInput.reviewID = Integer.parseInt(deleteReviewTextField.getText());
        if(warehouseShipmentISBN.getText().equals("ISBN") == false)
            keyboardInput.toShipmentISBN = warehouseShipmentISBN.getText();
        if(warehouseShipmentAmount.getText().equals("amount") == false) //now, later it will be try-catch
            keyboardInput.toShipmentAmount = Integer.parseInt(warehouseShipmentAmount.getText());
        return keyboardInput;
    }
}
