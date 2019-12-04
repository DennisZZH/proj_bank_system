package AppUI;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayFriendUI implements ActionListener {
    private JFrame payFriendFrame;
    private JPanel describePanel;
    private JPanel friendPanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;

    private JTextField amountText;
    private JTextField friendText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public PayFriendUI(Account account, String customerId){
        this.account = account;
        this.customerId = customerId;

        // Set the title of this frame
        payFriendFrame = new JFrame("Pay-Friend");

        // Initial describe area
        describePanel = new JPanel();
        describePanel.add(new JLabel("Please enter amount and your friend`s account number!"));

        friendPanel = new JPanel();
        friendPanel.add(new JLabel("Account Id:"));
        friendText = new JTextField(20);
        friendPanel.add(friendText);


        // Initial input area
        amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        amountText = new JTextField(20);
        amountPanel.add(amountText);

        // Initial confirm area
        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        payFriendFrame.add(describePanel);
        payFriendFrame.add(friendPanel);
        payFriendFrame.add(amountPanel);
        payFriendFrame.add(confirmPanel);

        payFriendFrame.setLayout(new FlowLayout());
        payFriendFrame.setVisible(true);
        payFriendFrame.setBounds(300, 300, 350, 400);

        confirmButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
