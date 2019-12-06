package atm;

import cs174a.AccountDao;
import cs174a.DateDao;
import cs174a.IDCreator;
import cs174a.TransactionDao;
import model.Account;
import model.Transaction;
import model.TransactionType;

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
        if(e.getActionCommand().equals("Confirm")) {
            AccountDao a = new AccountDao();
            Double money = Double.parseDouble(amountText.getText());
            Double currentBalance = account.getBalance();

            if (money < currentBalance) {
                currentBalance = currentBalance - money;
                a.updateBalance(account.getId(), currentBalance);
                account.setBalance(currentBalance);

                int trans_id = IDCreator.getNextId();

                Transaction t = new Transaction();
                t.setId(trans_id);
                t.setType(TransactionType.WITHDRAWAL);
                t.setTime(DateDao.getCurrentDate());
                t.setAmount(money);
                t.setCustomer_id(customerId);
                t.setFrom_id(account.getId());
                t.setTo_id(account.getId());
                t.setFee(0.00);
                t.setCheck_number(null);

                TransactionDao actionadd = new TransactionDao();
                actionadd.addTransaction(t);

                //check the final balance: if < 0.01, account close
                if (currentBalance < 0.01) {
                    a.updateState(account.getId(), 1);
                    account.setIsClosed(1);
                }
                JOptionPane.showMessageDialog(withdrawalFrame, "Withdrawal succeed! \n Your current balance: $" + currentBalance);
                withdrawalFrame.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(withdrawalFrame, "Withdrawal fail! Your balance is not enough. Balance: $" + currentBalance);
            }
        }

    }
}
