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

public class WireUI implements ActionListener {
    private JFrame wireFrame;
    private JPanel describePanel;
    private JPanel transferPanel;
    private JPanel amountPanel;
    private JPanel confirmPanel;
    private JTextField amountText;
    private JTextField transferText;
    private JButton confirmButton;
    private Account account;
    private String customerId;

    public WireUI(Account account, String customerId) {
        this.account = account;
        this.customerId = customerId;

        wireFrame = new JFrame("Wire");

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

        wireFrame.add(describePanel);
        wireFrame.add(transferPanel);
        wireFrame.add(amountPanel);
        wireFrame.add(confirmPanel);

        wireFrame.setLayout(new FlowLayout());
        wireFrame.setVisible(true);
        wireFrame.setBounds(300, 300, 350, 400);

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
            //get from's balance
            Double from = account.getBalance();


            //check if target account is exist
            Account targetAccount = a.queryAccountById(to_id);
            if(targetAccount==null){
                JOptionPane.showMessageDialog(wireFrame, "Account does not exist!");
                return;
            }
            Double to=targetAccount.getBalance();

            //check if the other account is closed or not
            if (targetAccount.isClosed()==1){
                JOptionPane.showMessageDialog(wireFrame, "Wire failed! The other account is closed!");
                return;
            }

            //check if there is enough money
            if(from < money ){
                JOptionPane.showMessageDialog(wireFrame, "Wire failed! Your account does not have enough money!");
                return;
            }

            //check if two account has at least one common owner
//            Customer[] c1 = c.queryCustomerByAccountId(account.getId());
//            Customer[] c2 = c.queryCustomerByAccountId(to_id);
//            ArrayList<String> list1 =new ArrayList<String>();
//            ArrayList<String> list2 =new ArrayList<String>();
//            for(int i=0;i<c1.length;i++){
//                list1.add(c1[i].getId());
//            }
//            for(int i=0;i<c2.length;i++){
//                list2.add(c2[i].getId());
//            }
//
//            list1.retainAll(list2);
//            if(list1.size()==0){
//                JOptionPane.showMessageDialog(wireFrame, "Transfer failed! There is no common owner in these two accounts!");
//                return;
//            }


            //update balance in both account
            from = from + money*0.98;
            to = to - money;
            a.updateBalance(account.getId(),from);
            account.setBalance(from);
            a.updateBalance(to_id,to);
            targetAccount.setBalance(to);

            //add transaction
            Transaction t = new Transaction();
            t.setTime(DateDao.getCurrentDate());
            t.setType(TransactionType.WIRE);
            t.setCustomer_id(customerId);
            t.setFrom_id(account.getId());
            t.setTo_id(to_id);
            t.setAmount(money);
            t.setFee(money*0.02);
            t.setCheck_number(null);
            transactionDao.addTransaction(t);

            //check from's current balance, below 0.01 closed
            if(from<0.01){
                a.updateState(account.getId(),1);
                account.setIsClosed(1);
            }


            JOptionPane.showMessageDialog(wireFrame,"Wire succeed! Your current balance is : $"+ from);
            wireFrame.setVisible(false);

        }
    }
}
