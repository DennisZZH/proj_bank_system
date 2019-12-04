package atm;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CollectUI implements ActionListener {
    private JFrame collectFrame;
    private JPanel describePanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public CollectUI(Account account, String customerId){
        this.account=account;
        this.customerId=customerId;

        collectFrame = new JFrame("Collect");

        describePanel = new JPanel();
        describePanel.add(new JLabel("Please Enter Amount!"));

        amountPanel=new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        amountText = new JTextField(20);
        amountPanel.add(amountText);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        collectFrame.add(describePanel);
        collectFrame.add(amountPanel);
        collectFrame.add(confirmPanel);
        collectFrame.setLayout(new FlowLayout());
        collectFrame.setVisible(true);
        collectFrame.setBounds(300,300,350,400);

        confirmButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
