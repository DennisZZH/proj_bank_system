package AppUI;

import model.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopUpUI implements ActionListener {
    private JFrame topupFrame;
    private JPanel describePanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public TopUpUI(Account account, String customerId){
        this.account = account;
        this.customerId = customerId;

        topupFrame = new JFrame("Top-up");

        describePanel = new JPanel();
        describePanel.add(new JLabel("Please enter amount!"));

        amountPanel = new JPanel();
        amountPanel.add(new JLabel("Amount:"));
        amountText = new JTextField(20);
        amountPanel.add(amountText);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        topupFrame.add(describePanel);
        topupFrame.add(amountPanel);
        topupFrame.add(confirmPanel);

        topupFrame.setLayout(new FlowLayout());
        topupFrame.setVisible(true);
        topupFrame.setBounds(300, 300, 350, 400);

        confirmButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
