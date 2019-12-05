package atm;

import cs174a.AccountDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//step2: choose which function to process
//app includes following functions:
//Deposit (c/s)
//top-up (p)
//Withdrawal (c/s)
//purchase (p)
//Transfer (between accounts with a common owner only) (c/s)
// collect (p)
// Wire (c/s)
// pay-friend (p)

public class MenuUI implements ActionListener {
    private JFrame appFrame;
    private JPanel appPanel;

    private JButton DepositButton;
    private JButton TopUpButtoon;
    private JButton WithDrawalButton;
    private JButton PurchaseButton;
    private JButton TransferButton;
    private JButton CollectButton;
    private JButton WireButton;
    private JButton PayFriendButton;

    private JButton BackButton;

    private AccountDao accountAction;
    //private Account account;
    private String cid;

    public MenuUI(String cid){
        this.cid=cid;
        //initial frame
        appFrame = new JFrame("ATM-App");
        appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //initial buttons
        DepositButton = new JButton("Deposit");
        TopUpButtoon = new JButton("Top Up");
        WithDrawalButton = new JButton("Withdrawal");
        PurchaseButton = new JButton("Purchase");
        TransferButton = new JButton("Transfer");
        CollectButton = new JButton("Collect");
        WireButton = new JButton("Wire");
        PayFriendButton = new JButton("Pay Friend");
        BackButton = new JButton("Back");


        //set layout
        appPanel.setLayout(new GridLayout(5,2,20,20));

        appFrame.add(appPanel);

        appFrame.setVisible(true);
        appFrame.setLayout(new FlowLayout());
        appFrame.setBounds(300,300,350,400);

        DepositButton.addActionListener(this);
        TopUpButtoon.addActionListener(this);
        WithDrawalButton.addActionListener(this);
        PurchaseButton.addActionListener(this);
        TransferButton.addActionListener(this);
        CollectButton.addActionListener(this);
        WireButton.addActionListener(this);
        PayFriendButton.addActionListener(this);
        BackButton.addActionListener(this);

        accountAction = new AccountDao();



    }



    @Override
    public void actionPerformed(ActionEvent e) {
//        String command = e.getActionCommand();
//        switch(command) {
//            case "Back":
//                return;
//            case "Deposit":
//                return;
//            case "Top Up":
//                return;
//            case "Withdrawal":
//                return;
//            case "Purchase":
//                return;
//            case "Transfer":
//                return;
//            case "Collect":
//                return;
//            case "Wire":
//                return;
//            case "Pay Friend":
//                return;
//
//        }

    }
}
