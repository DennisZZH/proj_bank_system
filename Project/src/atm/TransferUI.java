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
//        if(e.getActionCommand().equals("Confirm")) {
//            AccountDao a = new AccountDao();
//            Double money = Double.parseDouble(amountText.getText());
//            String id = transferText.getText();
//
//            //check if input account exist or not
//            Account to_account = new Account();
//            to_account = a.queryAccountById(id);
//
//            if(to_account==null){
//                JOptionPane.showMessageDialog(transferFrame, "Account doesn't exist !");
//
//            }else{
//                Double to_balance = to_account.getBalance();
//                Double currentBalance = account.getBalance();
//
//
//            }
//
//
//
//
//
//            if (money < currentBalance) {
//                currentBalance = currentBalance - money;
//                a.updateBalance(account.getId(), currentBalance);
//                account.setBalance(currentBalance);
//
//                Transaction t = new Transaction();
//                t.setTime(DateDao.getCurrentTime());
//                t.setType(TransactionType.WITHDRAWAL);
//                t.setCustomer_id(customerId);
//                t.setFrom_id(account.getId());
//                t.setTo_id(account.getId());
//                t.setAmount(money);
//                t.setFee(0.00);
//                t.setCheck_number(null);
//
//                TransactionDao actionadd = new TransactionDao();
//                actionadd.addTransaction(t);
//
//                //check the final balance: if < 0.01, account close
//                if (currentBalance < 0.01) {
//                    a.updateState(account.getId(), 1);
//                    account.setIsClosed(1);
//                }
//                JOptionPane.showMessageDialog(withdrawalFrame, "Withdrawal succeed! \n Your current balance: $" + currentBalance);
//                withdrawalFrame.setVisible(false);
//            } else {
//                JOptionPane.showMessageDialog(withdrawalFrame, "Withdrawal fail! Your balance is not enough. Balance: $" + currentBalance);
//            }
//        }
//
    }
}
