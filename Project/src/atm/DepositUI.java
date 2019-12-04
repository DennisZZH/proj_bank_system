package atm;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DepositUI implements ActionListener {
    private JFrame depositFrame;
    private JPanel describePanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public DepositUI(Account account, String customerId){
        this.account=account;
        this.customerId=customerId;

        depositFrame = new JFrame("Deposit");

        describePanel = new JPanel();
        describePanel.add(new JLabel("Please Enter Amount!"));

        amountPanel=new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        amountText = new JTextField(20);
        amountPanel.add(amountText);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        depositFrame.add(describePanel);
        depositFrame.add(amountPanel);
        depositFrame.add(confirmPanel);
        depositFrame.setLayout(new FlowLayout());
        depositFrame.setVisible(true);
        depositFrame.setBounds(300,300,350,400);

        confirmButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
