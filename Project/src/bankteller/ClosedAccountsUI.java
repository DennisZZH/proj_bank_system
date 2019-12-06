package bankteller;

import cs174a.AccountDao;
import cs174a.CustomerDao;
import model.Account;
import model.Customer;

import javax.swing.*;
import java.awt.*;

public class ClosedAccountsUI extends JPanel {

    private JPanel accountPanel;

    public ClosedAccountsUI(){
        accountPanel = new JPanel();

        accountPanel.add(new JLabel("Account ID"));
        accountPanel.add(new JLabel("Account Type"));
        accountPanel.add(new JLabel("Primary Owner Name"));
        accountPanel.add(new JLabel("Bank Branch Name"));
        int line = 1;
        AccountDao accountDao = new AccountDao();
        Account[] accounts = accountDao.queryClosedAccount();
        CustomerDao customerDao = new CustomerDao();
        if(accounts != null && accounts.length != 0){
            for (int i = 0; i < accounts.length; i++) {
//                System.out.println(accounts[i].getId());
//                System.out.println(accounts[i].getType().toString());
//                System.out.println((customerDao.queryCustomerByTaxId(accounts[i].getPrimaryOwner())).getName());
//                System.out.println(accounts[i].getBranchName());
                accountPanel.add(new JLabel(accounts[i].getId()));
                String account_type;
                if(accounts[i].getType() == null){
                    account_type = "null";
                }else{
                    account_type = accounts[i].getType().toString();
                }
                accountPanel.add(new JLabel(account_type));
                accountPanel.add(new JLabel((customerDao.queryCustomerByTaxId(accounts[i].getPrimaryOwner())).getName()));
                accountPanel.add(new JLabel(accounts[i].getBranchName()));
                line++;
            }
        }
        accountPanel.setLayout(new GridLayout(line,4));

        this.add(accountPanel);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
    }
}
