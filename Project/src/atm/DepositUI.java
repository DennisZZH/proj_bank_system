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
        if(e.getActionCommand().equals("Confirm")){
            AccountDao a = new AccountDao();
            Double money = Double.parseDouble(amountText.getText());
            //need convertion
            Double currentBalance = account.getBalance();
            currentBalance = currentBalance + money;
            a.updateBalance(account.getId(),currentBalance);
            account.setBalance(currentBalance);

            Transaction t = new Transaction();
            t.setTime(DateDao.getCurrentTime());
            t.setType(TransactionType.DEPOSIT);
            t.setCustomer_id(customerId);
            t.setFrom_id(account.getId());
            t.setTo_id(account.getId());
            t.setAmount(money);
            t.setFee(0.00);
            t.setCheck_number(null);

            TransactionDao actionadd = new TransactionDao();
            actionadd.addTransaction(t);

            JOptionPane.showMessageDialog(depositFrame,"Deposit succeed! Your current balance is : $"+currentBalance);
            depositFrame.setVisible(false);

        }

    }
}
