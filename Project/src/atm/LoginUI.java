package atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs174a.CustomerDao;
import cs174a.PinDao;
import model.Customer;

//step1: username(cid) -> pin -> login -> choose which accounts



public class LoginUI implements ActionListener {
    private JFrame LoginFrame;
    private JPanel LoginPanel;
    private JPanel welcomePanel;
    //private JPanel accountPanel;

    private JPanel usernamePanel;
    private JTextField usernameTxt;
    private JPanel pinPanel;
    private JPasswordField pinTxt;

    private JButton LoginButton;

    public LoginUI(){
        LoginFrame = new JFrame("Login");
        LoginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //initial welcome page
        welcomePanel = new JPanel();
        welcomePanel.add(new JLabel("Welcome to ATM-App!!\t"));
        //initial username input
        usernamePanel = new JPanel();
        usernamePanel.add(new JLabel("Tax Identification Number: "));
        usernameTxt = new JTextField(20);
        usernamePanel.add(usernameTxt);
        //initial pin input
        pinPanel = new JPanel();
        pinPanel.add(new JLabel("PIN: "));
        pinTxt = new JPasswordField(20);
        pinPanel.add(pinTxt);
        //initial login button
        LoginButton = new JButton();
        LoginPanel = new JPanel();
        LoginPanel.add(LoginButton);

        //add every plane to login Frame
        LoginFrame.add(welcomePanel);
        LoginFrame.add(usernamePanel);
        LoginFrame.add(pinPanel);
        LoginFrame.add(LoginPanel);

        LoginFrame.setVisible(true);
        LoginFrame.setBounds(300,300,800,400);
        LoginFrame.setLayout(new GridLayout(5,1));

        LoginButton.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Login")){
            String input = usernameTxt.getText();
            String pin = pinTxt.getText();

            CustomerDao customer = new CustomerDao();
            Customer result = new Customer();
            result = customer.queryCustomerByTaxId(input);

            PinDao p = new PinDao();

            //1. check if customer already exist;
            if(result != null){
                //2. check pin
                if(p.verifyPin(input, pin)){
                    JOptionPane.showMessageDialog(LoginFrame,"Login Successfully!");
                    //ctn to next step: select account
                    AccountSelectUI selection = new AccountSelectUI(input);

                    LoginFrame.setVisible(false);
                }else{
                    JOptionPane.showMessageDialog(LoginFrame,"Wrong PIN");
                }

            }else{
                JOptionPane.showMessageDialog(LoginFrame,"User doesn't exit");
            }
        }

    }
}
