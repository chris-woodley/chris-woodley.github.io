//Author Name: Christopher Woodley

//Date: 03/14/2023

//Description: This is the Main Menu form class. It displays the 3 options (Contact, Appointment, Task) for the user to select
//THe program will continue to run until this window is closed

package com.mongodb.taskscheduler;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainMenu extends JFrame {

    private Container cMain;
    private JLabel title;
    private JLabel label1;
    private JRadioButton contactBtn;
    private JRadioButton appointBtn;
    private JRadioButton taskBtn;
    private ButtonGroup optGrp;
    private JButton submitBtn;
    private JButton exitBtn;

    public MainMenu()
    {
        // Build the basic form outline and ensure it cannot be closed via the default close operator
        setTitle("Task Scheduler");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        cMain = getContentPane();
        cMain.setLayout(null);

        // Set title for Form as Main Name
        title = new JLabel("Main Menu");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        cMain.add(title);

        // Set header to inform user to select an option from below radio buttons
        label1 = new JLabel("Please select an option:");
        label1.setFont(new Font("Arial", Font.PLAIN, 20));
        label1.setSize(300, 30);
        label1.setLocation(300, 100);
        cMain.add(label1);

        // Creating radio buttons for users to select one option (Contact, Appointment, Task)
        contactBtn = new JRadioButton("Contact");
        contactBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        contactBtn.setSize(300, 30);
        contactBtn.setLocation(300, 150);
        cMain.add(contactBtn);

        appointBtn = new JRadioButton("Appointment");
        appointBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        appointBtn.setSize(300, 30);
        appointBtn.setLocation(300, 200);
        cMain.add(appointBtn);

        taskBtn = new JRadioButton("Task");
        taskBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        taskBtn.setSize(300, 30);
        taskBtn.setLocation(300, 250);
        cMain.add(taskBtn);

        // Adding all radio buttons into one ButtonGroup to ensure only one selection can be made
        optGrp = new ButtonGroup();
        optGrp.add(contactBtn);
        optGrp.add(appointBtn);
        optGrp.add(taskBtn);

        // Create submit button for user to run selected option
        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        submitBtn.setSize(100, 20);
        submitBtn.setLocation(225, 300);
        cMain.add(submitBtn);

        // Create exit button to exit out of application
        exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        exitBtn.setSize(100, 20);
        exitBtn.setLocation(350, 300);
        cMain.add(exitBtn);

        // Action Listener for Submit Button
        // Based on Radio Button selected will generate which menu will be opened
        // If no Radio Button is selected, error message will display prompting user to select an option to continue
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contactBtn.isSelected()) {
                    ContactMenu contactMenu = new ContactMenu();
                    contactMenu.setVisible(true);
                }
                else if (appointBtn.isSelected()) {
                    AppointmentMenu appointmentMenu = new AppointmentMenu();
                    appointmentMenu.setVisible(true);
                }
                else if (taskBtn.isSelected()) {
                    TaskMenu taskMenu = new TaskMenu();
                    taskMenu.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(MainMenu.this, "Please select one of the options to continue");
                }
            }
        });

        // Action Listener for Exit Button
        // Closes application once exit button has been selected
        exitBtn.addActionListener((event) -> System.exit(0));

        setVisible(true);
    }
}




