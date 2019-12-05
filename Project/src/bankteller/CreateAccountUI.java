package bankteller;

import atm.DepositUI;
import cs174a.*;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CreateAccountUI  extends JPanel implements ActionListener {

    private JPanel customerPanel;

    private JPanel accountPanel;

    private JPanel accountTypePanel;

    private JPanel linkAccountPanel;

    private JPanel bankBranchPanel;

    private JPanel moneyPanel;

    private JTextField customerField;
    private JTextField nameField;
    private JTextField addressField;

    private JTextField accountField;

    private JComboBox typeField;

    private JTextField linkAccountField;

    private JTextField bankBranchField;

    private JTextField moneyField;

    private JPanel confirmPanel;

    private JButton confirmButton;

    private int isPrimary;

    private int isPocket;

    public CreateAccountUI() {
        // Choose to be a primary owner or co-owner
        isPrimary = JOptionPane.showConfirmDialog(this, "Click yes if you want to be a primary owner of a new account.\r\nClick no if you want to be a co-owner of an existed account");
        isPocket = 1;
        if (isPrimary == 0) {
            // If create a pocket account
            isPocket = JOptionPane.showConfirmDialog(this, "Do you want to create a pocket account?");
        }

        if (isPocket == 2 || isPrimary == 2)
            return;

        customerPanel = new JPanel();
        customerPanel.add(new JLabel("Tax identification number"));
        customerField = new JTextField(20);
        customerPanel.add(customerField);

        accountPanel = new JPanel();
        accountPanel.add(new JLabel("Account id"));
        accountField = new JTextField(20);
        accountPanel.add(accountField);

        accountTypePanel = new JPanel();
        String[] choice = {"STUDENT_CHECKING_ACCOUNT", "INTEREST_CHECKING_ACCOUNT", "SAVING_ACCOUNT"};
        accountTypePanel.add(new JLabel("Account Type"));
        typeField = new JComboBox(choice);
        accountTypePanel.add(typeField);

        linkAccountPanel = new JPanel();
        linkAccountPanel.add(new JLabel("Linked Account id"));
        linkAccountField = new JTextField(20);
        linkAccountPanel.add(linkAccountField);

        bankBranchPanel = new JPanel();
        bankBranchPanel.add(new JLabel("Bank Branch Name"));
        bankBranchField = new JTextField(20);
        bankBranchPanel.add(bankBranchField);

        moneyPanel = new JPanel();
        moneyPanel.add(new JLabel("Money Amount"));
        moneyField = new JTextField(20);
        moneyPanel.add(moneyField);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        int line = 3;

        // If create with primary account
        if (isPrimary == 0) {
            this.add(customerPanel);
            this.add(accountPanel);
            // If create a pocket account
            if (isPocket == 0) {
                this.add(linkAccountPanel);
            } else {
                this.add(accountTypePanel);
            }
            this.add(bankBranchPanel);
            this.add(confirmPanel);
            this.add(moneyPanel);
            line = 6;
        } else if (isPrimary == 1) {
            this.add(customerPanel);
            this.add(accountPanel);
            this.add(confirmPanel);
        }

        this.setLayout(new GridLayout(line, 1));
        confirmButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Confirm")){

            if (isPrimary == 0) {

                AccountDao accountDao = new AccountDao();
                if (accountDao.queryAccountById(accountField.getText()) != null) {
                    JOptionPane.showMessageDialog(this, "Repeated Account Id!");
                    return;
                }

                CustomerDao customerDao = new CustomerDao();
                String customerId = customerField.getText();
                Customer customer = customerDao.queryCustomerByTaxId(customerId);
                // Customer dose not exist
                if (customer == null) {
                    String name = JOptionPane.showInputDialog("You are new in our system! Please input your name");
                    String address = JOptionPane.showInputDialog("You are new in our system! Please input your address");
                    String pin = JOptionPane.showInputDialog("Reset your PIN");
                    customer = new Customer(customerId, name, address);
                    customerDao.addCustomer(customer);
                    // Reset Pin
                    PinDao pinDao = new PinDao();
                    pinDao.setPin(customerId, "1717", pin);
                }
                String id = accountField.getText();
                String linked_account_id = null;
                Testable.AccountType type = null;
                if (isPocket == 0) {
                    type =  Testable.AccountType.POCKET;
                    linked_account_id = linkAccountField.getText();
                } else {
                    switch (typeField.getSelectedIndex()) {
                        case 0:
                            type =  Testable.AccountType.STUDENT_CHECKING;
                            break;
                        case 1:
                            type =  Testable.AccountType.INTEREST_CHECKING;
                            break;
                        case 2:
                            type =  Testable.AccountType.SAVINGS;
                    }
                }
                String bank_branch_name = bankBranchField.getText();
                double balance = Double.valueOf(moneyField.getText());

                App app = new App();
                app.initializeSystem();
                String result;
                if(isPocket == 0){
                    accountDao.updateBranchName(bank_branch_name, id);
                    result = app.createCheckingSavingsAccount(type, id, balance, customerId, "known", "known");
                }else{
                    result = app.createPocketAccount(id, linked_account_id, balance, customerId);
                }
                if(result.charAt(0) == '0'){
                    JOptionPane.showMessageDialog(this,"Primary account created successfully!");
                }else{
                    JOptionPane.showMessageDialog(this,"Primary account created failed!");
                }


                while (JOptionPane.showConfirmDialog(this, "Do you want to create a Co-owner Account?") == 0){
                    String tax_id = JOptionPane.showInputDialog("Please input your tax id");
                    customer = customerDao.queryCustomerByTaxId(tax_id);
                    if (customer == null) {
                        String name = JOptionPane.showInputDialog("You are new in our system. Please input your name");
                        String address = JOptionPane.showInputDialog("You are new in our system. Please input your address");
                        String pin = JOptionPane.showInputDialog("Reset your pin");
                        customer = new Customer(tax_id, name, address);
                        customerDao.addCustomer(customer);
                        // Reset Pin
                        PinDao pinDao = new PinDao();
                        pinDao.setPin(tax_id, "1717", pin);
                    }
                    OwnDao ownDao = new OwnDao();
;                   ownDao.addRelation(tax_id, id, 0);
                    JOptionPane.showMessageDialog(this,"Co-owner account set up successfully!");
                }

                this.setVisible(false);
                this.repaint();
                this.revalidate();

            } else {
                AccountDao accountDao = new AccountDao();
                Account account = accountDao.queryAccountById(accountField.getText());
                if (account == null) {
                    JOptionPane.showMessageDialog(this, "No such account!");
                    this.setVisible(false);
                    this.repaint();
                    this.revalidate();
                    return;
                } else {
                    CustomerDao customerDao = new CustomerDao();
                    String customerId = customerField.getText();
                    Customer customer = customerDao.queryCustomerByTaxId(customerId);
                    if (customer == null) {
                        String name = JOptionPane.showInputDialog("You are new in our system. Please input your name");
                        String address = JOptionPane.showInputDialog("You are new in our system. Please input your address");
                        String pin = JOptionPane.showInputDialog("Reset your pin");
                        customer = new Customer(customerId, name, address);
                        customerDao.addCustomer(customer);
                        // Reset Pin
                        PinDao pinDao = new PinDao();
                        pinDao.setPin(customerId, "1717", pin);
                    }
                    OwnDao custAccoDao = new OwnDao();
                    custAccoDao.addRelation(customerId, account.getId(), 0);
                    JOptionPane.showMessageDialog(this,"Co-owner account set up successfully!");
                }
            }
        }
    }

}
