package AppUI;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WireUI implements ActionListener {
    private JFrame wireFrame;
    private JPanel describePanel;
    private JPanel transferPanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JTextField transferText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public WireUI(Account account, String customerId) {
        this.account = account;
        this.customerId = customerId;

        wireFrame = new JFrame("Wire");

        describePanel = new JPanel();
        describePanel.add(new JLabel("Please enter amount and account number!"));

        transferPanel = new JPanel();
        transferPanel.add(new JLabel("Account Id:"));
        transferText = new JTextField(20);
        transferPanel.add(transferText);

        amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        amountText = new JTextField(20);
        amountPanel.add(amountText);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        wireFrame.add(describePanel);
        wireFrame.add(transferPanel);
        wireFrame.add(amountPanel);
        wireFrame.add(confirmPanel);

        wireFrame.setLayout(new FlowLayout());
        wireFrame.setVisible(true);
        wireFrame.setBounds(300, 300, 350, 400);

        confirmButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
