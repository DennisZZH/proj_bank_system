package AppUI;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WithDrawalUI implements ActionListener {
    private JFrame withdrawalFrame;
    private JPanel describePanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public WithDrawalUI(Account account, String customerId){
        this.account = account;
        this.customerId = customerId;

        withdrawalFrame = new JFrame("Withdrawal");

        describePanel = new JPanel();
        describePanel.add(new JLabel("Please enter amount!"));

        amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        amountText = new JTextField(20);
        amountPanel.add(amountText);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        withdrawalFrame.add(describePanel);
        withdrawalFrame.add(amountPanel);
        withdrawalFrame.add(confirmPanel);

        withdrawalFrame.setLayout(new FlowLayout());
        withdrawalFrame.setVisible(true);
        withdrawalFrame.setBounds(300, 300, 350, 400);

        confirmButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
