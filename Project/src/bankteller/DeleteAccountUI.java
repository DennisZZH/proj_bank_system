package bankteller;

import model.Account;
import model.AccountType;
import model.Customer;
import cs174a.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteAccountUI  extends JPanel  {
    public DeleteAccountUI() {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure?");
        if (choice == 0) {
            // Firstly find all closed account;
            AccountDao accountDao = new AccountDao();
            Account[] accounts = accountDao.queryClosedAccount();
            if (accounts == null || accounts.length == 0) {
                JOptionPane.showMessageDialog(this, "No closed account!");
                return;
            }
            // For each account, delete the relation between accounts and customers
            OwnDao ownDao = new OwnDao();
            for (int i = 0; i < accounts.length; i++) {
                ownDao.deleteRelationByAccountId(accounts[i].getId());
            }
            // Then delete all closed accounts;
            accountDao.deleteAllClosedAccount();
            // Then find if there is a customer without accounts
            CustomerDao customerDao = new CustomerDao();
            TransactionDao transactionDao = new TransactionDao();
            Customer[] customers = customerDao.queryCustomerWithoutAccount();
            if (customers != null && customers.length != 0) {
                for (int i = 0; i < customers.length; i++) {
                    transactionDao.deleteTransactionsByCustomerId(customers[i].getId());
                }
            }
            customerDao.deleteCustomerWithouAccount();
            JOptionPane.showMessageDialog(this, "Closed Accounts and Customers is successfully deleted!");
        }
    }
}
