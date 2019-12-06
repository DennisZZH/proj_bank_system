package bankteller;

import atm.MenuUI;
import cs174a.AccountDao;
import cs174a.CustomerDao;
import cs174a.PinDao;
import model.Account;
import model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReportUI  extends JPanel implements ActionListener {
    private JPanel usernamePanel;
    private JPanel passwordPanel;
    private JPanel loginPanel;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JPanel accountsPanel;
    private Account[] accounts;

    public ReportUI() {
        JOptionPane.showMessageDialog(this, "Please let customer login");
        // Initial the username input area
        usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Tax identification number"));
        usernameText = new JTextField(20);
        usernamePanel.add(usernameText);

        // Initial the password input area
        passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("PIN"));
        passwordText = new JPasswordField(20);
        passwordPanel.add(passwordText);

        // Initial the login button
        loginPanel = new JPanel();
        loginButton = new JButton("Login");
        loginPanel.add(loginButton);

        this.add(usernamePanel);
        this.add(passwordPanel);
        this.add(loginPanel);
        this.setLayout(new GridLayout(3, 1));
        this.setVisible(true);

        loginButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Login")){
            String input = usernameText.getText();
            String pin = passwordText.getText();

            CustomerDao customer = new CustomerDao();
            AccountDao accountDao = new AccountDao();
            Customer result = customer.queryCustomerByTaxId(input);

            PinDao p = new PinDao();

            //1. check if customer already exist;
            if(result != null){
                //2. check pin
                if(p.verifyPin(input, pin)){
                    JOptionPane.showMessageDialog(this,"Login Successfully!");
                    accountsPanel = new JPanel();
                    accounts = accountDao.queryAccountByCustomerId(input);
                    if(accounts != null && accounts.length != 0){
                        accountsPanel.add(new Label("ACCOUNT_ID"));
                        accountsPanel.add(new Label("ACCOUNT_TYPE"));
                        accountsPanel.add(new Label("BALANCE"));
                        accountsPanel.add(new Label("BRANCH_NAME"));
                        accountsPanel.add(new Label("ISCLOSED"));
                        int count =1;
                        for (int i = 0; i < accounts.length; i++) {
                            accountsPanel.add(new Label(String.valueOf(accounts[i].getId())));
                            accountsPanel.add(new Label(String.valueOf(accounts[i].getType())));
                            accountsPanel.add(new Label(Double.toString(accounts[i].getBalance())));
                            accountsPanel.add(new Label(accounts[i].getBranchName()));
                            accountsPanel.add(new Label(Integer.toString(accounts[i].isClosed())));
                            count++;
                        }
                        accountsPanel.setLayout(new GridLayout(count, 5));
                        accountsPanel.setVisible(true);

                        this.removeAll();
                        this.add(accountsPanel);
                        this.setVisible(true);
                        this.setLayout(new GridLayout(2, 1));
                        this.repaint();
                        this.revalidate();
                    }
                }else{
                    JOptionPane.showMessageDialog(this,"Wrong PIN");
                }

            }else{
                JOptionPane.showMessageDialog(this,"User doesn't exit");
            }
        }

    }
}
