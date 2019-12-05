package bankteller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI implements ActionListener {
    private JFrame mainFrame;

    private JPanel buttonPanel;

    private JPanel showPanel;

    private JPanel backPanel;

    private JButton writeCheckButton;

    private JButton enterCheckButton;

    private JButton monthlyStatementButton;

    private JButton listClosedButton;

    private JButton dterButton;

    private JButton reportButton;

    private JButton addInterestButton;

    private JButton createAccountButton;

    private JButton deleteAccountsButton;

    private JButton deleteTransactionsButton;

    private JButton addTimeButton;

    private JButton backButton;

    public MainUI() {
        mainFrame = new JFrame("Bank Teller");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buttonPanel = new JPanel();

        writeCheckButton = new JButton("Write Check");
        enterCheckButton = new JButton("Enter Check Transaction");
        monthlyStatementButton = new JButton("Generate Monthly Statement");
        listClosedButton = new JButton("List Closed Accounts");
        dterButton = new JButton("DTER");
        reportButton = new JButton("Customer Report");
        addInterestButton = new JButton("Add Interest");
        createAccountButton = new JButton("Create Account");
        deleteAccountsButton = new JButton("Delete Closed Accounts & Customers");
        deleteTransactionsButton = new JButton("Delete Transactions");
        addTimeButton = new JButton("Add Time");

        buttonPanel.add(writeCheckButton);
        buttonPanel.add(enterCheckButton);
        buttonPanel.add(monthlyStatementButton);
        buttonPanel.add(listClosedButton);
        buttonPanel.add(dterButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(addInterestButton);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(deleteAccountsButton);
        buttonPanel.add(deleteTransactionsButton);
        buttonPanel.add(addTimeButton);

        buttonPanel.setLayout(new GridLayout(3, 4, 20, 20));

        showPanel = new JPanel();
        showPanel.add(new JLabel("Welcome!"));

        backPanel = new JPanel();
        backButton = new JButton("Back");
        backPanel.add(backButton);
        backPanel.setLayout(new FlowLayout());

        GridBagLayout gridBagLayout = new GridBagLayout();
        mainFrame.setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints=new GridBagConstraints();
        gridBagConstraints.fill=GridBagConstraints.BOTH;
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=0;
        gridBagConstraints.gridwidth=4;
        gridBagConstraints.gridheight=1;
        gridBagLayout.setConstraints(buttonPanel, gridBagConstraints);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=1;
        gridBagConstraints.gridwidth=4;
        gridBagConstraints.gridheight=5;
        gridBagLayout.setConstraints(showPanel, gridBagConstraints);
        gridBagConstraints.gridx=0;
        gridBagConstraints.gridy=6;
        gridBagConstraints.gridwidth=4;
        gridBagConstraints.gridheight=1;
        gridBagLayout.setConstraints(backPanel, gridBagConstraints);

        mainFrame.add(buttonPanel);
        mainFrame.add(showPanel);
        mainFrame.add(backPanel);
        mainFrame.setVisible(true);

        //mainFrame.setLayout(new GridLayout(3,1));
        mainFrame.setBounds(300, 300, 1200, 600);

        writeCheckButton.addActionListener(this);
        enterCheckButton.addActionListener(this);
        monthlyStatementButton.addActionListener(this);
        listClosedButton.addActionListener(this);
        dterButton.addActionListener(this);
        reportButton.addActionListener(this);
        addInterestButton.addActionListener(this);
        createAccountButton.addActionListener(this);
        deleteAccountsButton.addActionListener(this);
        deleteTransactionsButton.addActionListener(this);
        addTimeButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Write Check")){
            repaint(new WriteCheckUI());
        }
        else if(e.getActionCommand().equals("Enter Check Transaction")){
            repaint(new EnterCheckUI());
        }
        else if(e.getActionCommand().equals("Generate Monthly Statement")){
            repaint(new MonthlyUI());
        }
        else if(e.getActionCommand().equals("List Closed Accounts")){
            repaint(new ClosedAccountsUI());
        }
        else if(e.getActionCommand().equals("DTER")){
            repaint(new DTERUI());
        }
        else if(e.getActionCommand().equals("Customer Report")){
            repaint(new ReportUI());
        }
        else if(e.getActionCommand().equals("Add Interest")){
            repaint(new AddInterestUI());
        }
        else if(e.getActionCommand().equals("Create Account")){
            repaint(new CreateAccountUI());
        }
        else if(e.getActionCommand().equals("Delete Closed Accounts & Customers")){
            repaint(new DeleteAccountUI());
        }
        else if(e.getActionCommand().equals("Delete Transactions")){
            repaint(new DeleteTransactionsUI());
        }
        else if(e.getActionCommand().equals("Add Time")){
            repaint(new AddTimeUI());
        }
        else if(e.getActionCommand().equals("Back")){
            buttonPanel.setVisible(true);
            buttonPanel.repaint();
            showPanel.removeAll();
            showPanel.add(new JLabel("Welcome!"));
            showPanel.repaint();
            showPanel.revalidate();
        }
    }

    private void repaint(JPanel jPanel){
        buttonPanel.setVisible(false);
        buttonPanel.repaint();
        showPanel.removeAll();
        showPanel.add(jPanel);
        showPanel.repaint();
        showPanel.revalidate();
    }
}
