package AppUI;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransferUI implements ActionListener {
    private JFrame transferFrame;
    private JPanel describePanel;
    private JPanel transferPanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JTextField transferText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public TransferUI(Account account, String customerId){
        this.account = account;
        this.customerId = customerId;

        transferFrame = new JFrame("Transfer");

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

        transferFrame.add(describePanel);
        transferFrame.add(transferPanel);
        transferFrame.add(amountPanel);
        transferFrame.add(confirmPanel);

        transferFrame.setLayout(new FlowLayout());
        transferFrame.setVisible(true);
        transferFrame.setBounds(300, 300, 350, 400);

        confirmButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
