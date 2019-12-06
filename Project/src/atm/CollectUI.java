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
        if(e.getActionCommand().equals("Confirm")){
            Double fee = 0.00;
            AccountDao a = new AccountDao();
            Double money = Double.parseDouble(amountText.getText());
            Double from = account.getBalance();
            String linkedID=account.getLinkedID();

            //check if linked account is closed
            Account linkAccount = a.queryAccountById(linkedID);
            Double to=linkAccount.getBalance();
            if (linkAccount.isClosed()==1){
                JOptionPane.showMessageDialog(collectFrame, "Collect failed! Your linked account is closed!");
                return;
            }

            //check if there is enough money
            if(from < money ){
                JOptionPane.showMessageDialog(collectFrame, "Collect failed! Your pocket account does not have enough money!");
                return;
            }


            TransactionDao transactionDao = new TransactionDao();
            //check if it is first transaction in the month
//            if(transactionDao.queryTransactionsDuringCurrentMonthWithAccountId(account.getId())== null){
//                if(from < 5.00) {
//                    JOptionPane.showMessageDialog(collectFrame, "Collect failed! Not enough money to pay fro monthly fee!");
//                    return;
//                }
//                from = from - 5.00;
//                fee += 5.00;
//            }

            //add/subtract money, cal fee
            fee += money*0.03;
            from = from - money *0.97;
            to = to + money;
            a.updateBalance(account.getId(),from);
            account.setBalance(from);
            a.updateBalance(linkedID,to);
            linkAccount.setBalance(to);

            //add transaction
            Transaction t = new Transaction();
            t.setTime(DateDao.getCurrentDate());
            t.setType(TransactionType.COLLECT);
            t.setCustomer_id(customerId);
            t.setFrom_id(account.getId());
            t.setTo_id(linkedID);
            t.setAmount(money);
            t.setFee(fee);
            t.setCheck_number(null);
            transactionDao.addTransaction(t);

            //check from's current balance, below 0.01 closed
            if(from<0.01){
                a.updateState(account.getId(),1);
                account.setIsClosed(1);
            }


            JOptionPane.showMessageDialog(collectFrame,"Collect succeed! Your current balance is : $"+ from);
            collectFrame.setVisible(false);

        }
    }
}
