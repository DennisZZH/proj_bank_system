package atm;

import cs174a.AccountDao;
import cs174a.DateDao;
import cs174a.TransactionDao;
import model.Account;
import model.Transaction;
import model.TransactionType;

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
        if(e.getActionCommand().equals("Confirm")) {
            AccountDao a = new AccountDao();
            Double money = Double.parseDouble(amountText.getText());
            Double currentBalance = account.getBalance();

            if (money < currentBalance) {
                TransactionDao actionadd = new TransactionDao();

//                if(actionadd.queryTransactionsDuringCurrentMonthWithAccountId(account.getId())==null ){
//                    if(currentBalance < 5.00)
//                        JOptionPane.showMessageDialog(purchaseFrame,"Purchase failed! Not enough money to pay fro monthly fee!");
//                    currentBalance = currentBalance - 5.00;
//                }

                currentBalance = currentBalance - money;
                a.updateBalance(account.getId(), currentBalance);
                account.setBalance(currentBalance);

                Transaction t = new Transaction();
                t.setTime(DateDao.getCurrentDate());
                t.setType(TransactionType.PURCHASE);
                t.setCustomer_id(customerId);
                t.setFrom_id(account.getId());
                t.setTo_id(account.getId());
                t.setAmount(money);
                t.setFee(0.00);
                t.setCheck_number(null);


                actionadd.addTransaction(t);

                //check the final balance: if < 0.01, account close
                if (currentBalance < 0.01) {
                    a.updateState(account.getId(), 1);
                    account.setIsClosed(1);
                }
                JOptionPane.showMessageDialog(purchaseFrame, "Purchase succeed! \n Your current balance: $" + currentBalance);
                purchaseFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(purchaseFrame, "Purchase fail! Your balance is not enough. Balance: $" + currentBalance);
            }
        }

    }
}
