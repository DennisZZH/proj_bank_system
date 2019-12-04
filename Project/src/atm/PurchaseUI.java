package AppUI;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseUI implements ActionListener {
    private JFrame purchaseFrame;
    private JPanel describePanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public PurchaseUI(Account account, String customerId){
        this.account = account;
        this.customerId = customerId;

        // Set the title of this frame
        purchaseFrame = new JFrame("Purchase");

        // Initial describe area
        describePanel = new JPanel();
        describePanel.add(new JLabel("Please enter amount!"));

        // Initial input area
        amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        amountText = new JTextField(20);
        amountPanel.add(amountText);

        // Initial confirm area
        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        purchaseFrame.add(describePanel);
        purchaseFrame.add(amountPanel);
        purchaseFrame.add(confirmPanel);

        purchaseFrame.setLayout(new FlowLayout());
        purchaseFrame.setVisible(true);
        purchaseFrame.setBounds(300, 300, 350, 400);

        confirmButton.addActionListener(this);
    }


    @Override
        public void actionPerformed(ActionEvent e) {

        }
}
