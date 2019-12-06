package bankteller;

import cs174a.App;
import cs174a.DateDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetDateUI extends JPanel implements ActionListener {

    private JPanel yearPanel;
    private JPanel monthPanel;
    private JPanel dayPanel;
    private JPanel confirmPanel;

    private JTextField yearField;
    private JTextField monthField;
    private JTextField dayField;

    private JButton confirmButton;

    private JLabel timeLabel;
    private JPanel background;

    public SetDateUI(){

        yearPanel = new JPanel();
        yearField = new JTextField(20);
        yearPanel.add(new JLabel("Please input Year that you continue want to set:"));
        yearPanel.add(yearField);

        monthPanel = new JPanel();
        monthField = new JTextField(20);
        monthPanel.add(new JLabel("Please input Month that you continue want to set:"));
        monthPanel.add(monthField);

        dayPanel = new JPanel();
        dayField = new JTextField(20);
        dayPanel.add(new JLabel("Please input Day that you continue want to set:"));
        dayPanel.add(dayField);

        confirmPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmPanel.add(confirmButton);

        timeLabel = new JLabel();
        background = new JPanel();
        background.add(timeLabel);

        this.add(yearPanel);
        this.add(monthPanel);
        this.add(dayPanel);
        this.add(confirmPanel);
        this.add(background);

        this.setLayout(new GridLayout(3,1));
        this.setVisible(true);

        confirmButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int year = Integer.valueOf(yearField.getText());
        int month = Integer.valueOf(monthField.getText());
        int day = Integer.valueOf(dayField.getText());

        App app = new App();
        app.initializeSystem();
        String result = app.setDate(year,month,day);
        System.out.println(result);
        String[] arr = result.split(" ");

        timeLabel.setText(" Now current time is set to : " + arr[1]);
        timeLabel.repaint();
        timeLabel.revalidate();

    }
}
