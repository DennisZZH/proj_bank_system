package atm;

import cs174a.AccountDao;
import cs174a.CustomerDao;
import cs174a.DateDao;
import cs174a.TransactionDao;
import model.Account;
import model.Customer;
import model.Transaction;
import model.TransactionType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

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
        if(e.getActionCommand().equals("Confirm")){
            CustomerDao c= new CustomerDao();
            AccountDao a = new AccountDao();
            TransactionDao transactionDao = new TransactionDao();
            //get amount and target cid
            Double money = Double.parseDouble(amountText.getText());
            String to_id = transferText.getText();
            //the amount to be moved should not exceed $2000
            if(money>2000.00){
                JOptionPane.showMessageDialog(transferFrame, "The amount to be moved should not exceed $2,000 per transfer.");
                return;
            }
            //get from's balance
            Double from = account.getBalance();


            //check if target account is exist
            Account targetAccount = a.queryAccountById(to_id);
            if(targetAccount==null){
                JOptionPane.showMessageDialog(transferFrame, "Account does not exist!");
                return;
            }
            Double to=targetAccount.getBalance();

            //check if the other account is closed or not
            if (targetAccount.isClosed()==1){
                JOptionPane.showMessageDialog(transferFrame, "Transfer failed! The other account is closed!");
                return;
            }

            //check if there is enough money
            if(from < money ){
                JOptionPane.showMessageDialog(transferFrame, "Transfer failed! Your account does not have enough money!");
                return;
            }

            //check if two account has at least one common owner
            Customer[] c1 = c.queryCustomerByAccountId(account.getId());
            Customer[] c2 = c.queryCustomerByAccountId(to_id);
            ArrayList<String> list1 =new ArrayList<String>();
            ArrayList<String> list2 =new ArrayList<String>();
            for(int i=0;i<c1.length;i++){
                list1.add(c1[i].getId());
            }
            for(int i=0;i<c2.length;i++){
                list2.add(c2[i].getId());
            }

            list1.retainAll(list2);
            if(list1.size()==0){
                JOptionPane.showMessageDialog(transferFrame, "Transfer failed! There is no common owner in these two accounts!");
                return;
            }


            //update balance in both account
            from = from -money;
            to = to + money;
            a.updateBalance(account.getId(),from);
            account.setBalance(from);
            a.updateBalance(to_id,to);
            targetAccount.setBalance(to);

            //add transaction
            Transaction t = new Transaction();
            t.setTime(DateDao.getCurrentDate());
            t.setType(TransactionType.TRANSFER);
            t.setCustomer_id(customerId);
            t.setFrom_id(account.getId());
            t.setTo_id(to_id);
            t.setAmount(money);
            t.setFee(0.00);
            t.setCheck_number(null);
            transactionDao.addTransaction(t);

            //check from's current balance, below 0.01 closed
            if(from<0.01){
                a.updateState(account.getId(),1);
                account.setIsClosed(1);
            }


            JOptionPane.showMessageDialog(transferFrame,"Transfer succeed! Your current balance is : $"+ from);
            transferFrame.setVisible(false);

        }
    }
}
