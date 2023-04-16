//Author Name: Christopher Woodley

//Date: 03/14/2023

//Description: This is the Appointment form class. It allows users to create, read, update and delete appointments
//Users will need to select the action they wish to handle to activate the required fields on the form
package com.mongodb.taskscheduler;


import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class AppointmentMenu extends JFrame {

    private Container cAppoint;
    private JLabel title;
    private JLabel label1;
    private JTextField txtAppoint;
    private JLabel label2;
    private JFormattedTextField txtApptDate;
    private JLabel label3;
    private JTextArea txtApptDesc;
    private JTextArea txtStatus;
    private JButton newBtn;
    private JButton srchBtn;
    private JButton updtBtn;
    private JButton submitBtn;
    private JButton delBtn;
    private JButton exitBtn;
    private String action;
    private Integer results;
    private AppointmentService appointmentSrvc;

    public AppointmentMenu()
    {
        // Build the basic form outline and ensure it cannot be closed via the default close operator
        setTitle("Appointment Form");
        setBounds(300, 500, 900, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        cAppoint = getContentPane();
        cAppoint.setLayout(null);

        // Set title for Form as Appointment Form
        title = new JLabel("Appointment Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        cAppoint.add(title);

        // set up all required fields for input
        // Contact requires Appointment ID, Appointment Date, and Appointment Description entries
        // All Text Fields are created as disabled
        // Users must select their action to enable required field for the action

        label1 = new JLabel("Appointment ID");
        label1.setFont(new Font("Arial", Font.PLAIN, 15));
        label1.setSize(200, 20);
        label1.setLocation(100, 100);
        cAppoint.add(label1);

        txtAppoint = new JTextField();
        txtAppoint.setFont(new Font("Arial", Font.PLAIN, 15));
        txtAppoint.setSize(75, 20);
        txtAppoint.setLocation(300, 100);
        txtAppoint.setEnabled(false);
        cAppoint.add(txtAppoint);

        label2 = new JLabel("Appointment Date");
        label2.setFont(new Font("Arial", Font.PLAIN, 15));
        label2.setSize(200, 20);
        label2.setLocation(100, 150);
        cAppoint.add(label2);

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        txtApptDate = new JFormattedTextField(format);
        txtApptDate.setFont(new Font("Arial", Font.PLAIN, 15));
        txtApptDate.setSize(100,20);
        txtApptDate.setLocation(300,150);
        txtApptDate.setEnabled(false);
        cAppoint.add(txtApptDate);

        label3 = new JLabel("Appointment Description");
        label3.setFont(new Font("Arial", Font.PLAIN, 15));
        label3.setSize(200, 20);
        label3.setLocation(100, 200);
        cAppoint.add(label3);

        txtApptDesc = new JTextArea();
        txtApptDesc.setDocument(new JTextFieldLimit(20));
        txtApptDesc.setFont(new Font("Arial", Font.PLAIN, 15));
        txtApptDesc.setSize(190, 100);
        txtApptDesc.setLocation(300, 200);
        txtApptDesc.setEnabled(false);
        cAppoint.add(txtApptDesc);

        // Create Status section which outputs changes being made to form and operations to be handled

        txtStatus = new JTextArea();
        txtStatus.setFont(new Font("Arial", Font.PLAIN, 15));
        txtStatus.setSize(300, 400);
        txtStatus.setLocation(500, 100);
        txtStatus.setLineWrap(true);
        txtStatus.setEditable(false);
        cAppoint.add(txtStatus);

        // Set up buttons to be accessed by user depending on their requirements
        // New will allow user to enter in Appointment Date and Appointment Description to create a new appointment

        newBtn = new JButton("New");
        newBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        newBtn.setSize(100, 20);
        newBtn.setLocation(75, 450);
        cAppoint.add(newBtn);

        // Search will allow user to enter Appointment ID to locate an existing appointment in database and return results
        // to status window

        srchBtn = new JButton("Search");
        srchBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        srchBtn.setSize(100, 20);
        srchBtn.setLocation(200, 450);
        cAppoint.add(srchBtn);

        // Update will allow user to locate an existing appointment using Appointment ID and make changes to data elements

        updtBtn = new JButton("Update");
        updtBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        updtBtn.setSize(100, 20);
        updtBtn.setLocation(325, 450);
        cAppoint.add(updtBtn);

        // Submit will be used when user to submit their query for New, Search, Update and Delete

        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        submitBtn.setSize(100, 20);
        submitBtn.setLocation(75, 500);
        cAppoint.add(submitBtn);

        // Delete will allow user to enter Appointment ID to delete an existing contact in database if found

        delBtn = new JButton("Delete");
        delBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        delBtn.setSize(100, 20);
        delBtn.setLocation(200, 500);
        cAppoint.add(delBtn);

        // Exit will close the current form and user will be returned to the Main Menu

        exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        exitBtn.setSize(100, 20);
        exitBtn.setLocation(325, 500);
        cAppoint.add(exitBtn);

        setVisible(true);

        // Adding Action Listener to New Button which enables the Appointment Date and Appointment Description as well as Submit Button
        // Adds status that New was selected
        newBtn.addActionListener(e -> {
            txtApptDate.setEnabled(true);
            txtApptDesc.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            action = "Create";
            txtStatus.append("New Button selected \n");
        });

        // Adding Action Listener to Search button which enables Appointment ID field and Submit Button
        // Adds status that Search wa selected
        srchBtn.addActionListener(e -> {
            txtAppoint.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            action = "Read";
            txtStatus.append("Search Button selected \n");
        });

        // Adding Action Listener to Update Button which enables the Appointment ID field and Submit Button
        // Adds status that Update was selected
        updtBtn.addActionListener(e -> {
            txtAppoint.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            action = "UpdateSearch";
            txtStatus.append("Update Button selected \n");
        });

        // Add Action Listener to Update Button which enables the Appointment ID field and Submit Button
        // Adds status that delete was selected
        delBtn.addActionListener(e -> {
            txtAppoint.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            action = "Delete";
            txtStatus.append("Delete Button selected \n");
        });

        // Adding Action Listener to Submit button to handle submission depending on action taken by user
        // If New is selected, will run through the Create process by creating a new Appointment item and append to the database
        // If Search is selected, will search for listed Appointment ID and return results to Status window
        // If Update is selected, will search for the listed Appointment ID and return results in Text fields for update
        // Once clicked again for Update, will update the data in the database with the fields entered
        // If Delete is selected, will search for the listed Appointment ID and delete record from database
        // Once completed, will clear text fields and set buttons back to original state
        // Added Appointment ID to the Status Window
        submitBtn.addActionListener(e -> {
            // Creates an instance of Class AppointmentService appointmentSrvc
            AppointmentService appointmentSrvc = new AppointmentService();
            // if the user action is to create or update, check for date that it is as least current day or later (do not allow earlier dates)
            if (action == "Create" || action == "UpdateSearch") {
                // Store data entered by user in Appointment Date to variable value
                String value = txtApptDate.getText();
                // Create Date Format under variable formatter using format of MM/dd/yyyy
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                // Create a new date variable date 1
                Date date1;
                try {
                    // Set date1 value to the date of the value user input in the format of MM/dd/yyyy
                    date1 = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                } catch (ParseException e1) {
                    throw new RuntimeException(e1);
                }
                // Set variable today to current date using LocalDate function
                LocalDate today = LocalDate.now();

                // Sets variable current to the value of today set to proper pattern of MM/dd/yyyy and set to a string
                String current = today.format(formatter);
                // Create new date date 2
                Date date2;
                try {
                    // Set date2 value to the current date  in the format of MM/dd/yyyy
                    date2 = new SimpleDateFormat("MM/dd/yyyy").parse(current);
                } catch (ParseException e2) {
                    throw new RuntimeException(e2);
                }
                // compares both user input date (date1) to current date (date2) to see if current date is greater than user input date
                // If so, will clear the data field and prompt user tha date has to be current date or future date only
                if (date1.compareTo(date2) < 0) {
                    txtApptDate.setEditable(false);
                    txtApptDate.setText("");
                    JOptionPane.showMessageDialog(AppointmentMenu.this, "Date has to be current date or future date only " + date1 + " & " + date2);
                    txtApptDate.setEditable(true);
                    action = "re-enter";
                }
            }
            switch (action)
            {
                // Run through Create action if New is selected
                case "Create":
                    // sets results to Appointment ID created in MongoDB for newly created record
                    results = Integer.valueOf(appointmentSrvc.addAppt(txtApptDate.getText(), txtApptDesc.getText()));
                    // outputs Appointment ID to Status output screen
                    txtStatus.append("Submitted new appointment under Appointment ID " + results + "\n");
                    // run clear data to set form back to start
                    clearData();
                    break;
                // Run through Read action if Search is selected
                case "Read":
                    // set results to hold data returned by function getAppt of AppointmentServices using user inputted Appointment ID
                    ArrayList<String> results = appointmentSrvc.getAppt(Integer.valueOf(txtAppoint.getText()));
                    // if no record found, output messages to user that no appointment could be found and exits further execution
                    // clears all data elements and ensures action is set to null
                    if (results == null) {
                        JOptionPane.showMessageDialog(AppointmentMenu.this, "No Appointment Found Under Appointment ID " + txtAppoint.getText());
                        clearData();
                        action = "";
                        break;
                        // else output to Status window the fields returned
                    } else {
                        txtStatus.append("Appointment Date: " + results.get(0) + "\n");
                        txtStatus.append("Appointment Description: " + results.get(1) + "\n");
                    }
                    // run clear data to set form back to start
                    clearData();
                    break;
                //Run through search portion of Update action if Update selected
                case "UpdateSearch":
                    // set results to hold data returned by function getAppt of AppointmentServices using user inputted Appointment ID
                    results = appointmentSrvc.getAppt(Integer.valueOf(txtAppoint.getText()));
                    // if no record found, output messages to user that no contact could be found and exits further execution
                    // Clears all data elements and ensures action is set to null
                    if (results == null) {
                        JOptionPane.showMessageDialog(AppointmentMenu.this, "No Appointment Found under Appointment ID " + txtAppoint.getText());
                        clearData();
                        action = "";
                        break;
                        // else output to appropriate textfield the fields returned, enabled the fields for updates
                    } else {
                        txtApptDate.setEnabled(true);
                        txtApptDate.setText(results.get(0));
                        txtApptDesc.setEnabled(true);
                        txtApptDesc.setText(results.get(1));
                        txtAppoint.setEnabled(false);
                    }
                    // set action to UpdateSubmit
                    action = "UpdateSubmit";
                    break;
                // Run through Update Submission action if Update is selected and a record is located via UpdateSearch
                case "UpdateSubmit":
                    //Runs the updateAppt function of AppointmentService, passing the textfields with updated information if applicable
                    appointmentSrvc.updateAppt(Integer.valueOf(txtAppoint.getText()), txtApptDate.getText(), txtApptDesc.getText());
                    //Outputs message that update is completed for Appointment ID given
                    txtStatus.append(txtAppoint.getText() + " updated \n");
                    // run clear data to set form back to start
                    clearData();
                    break;
                // Run through Delete action if Delete is selected
                case "Delete":
                    //Runs the deleteAppt function of AppointmentService for the Appointment ID given
                    appointmentSrvc.deleteAppt(Integer.valueOf(txtAppoint.getText()));
                    //Outputs message that delete is completed for Appointment ID given
                    txtStatus.append(txtAppoint.getText() + " deleted \n");
                    // run clear data to set form back to start
                    clearData();
                    break;
                // If no action selected, break and set data back to form start
                default:
                    clearData();
                    break;
            }
        });

        // Adding Action Listener for Exit Button
        // Closes the Appointment Form only
        exitBtn.addActionListener(e -> {
            this.dispose();
        });
    }

    // Function to clear all data elements from form to allow further input from user
    // Clears all textfields to null and disables them
    // Ensures only New, Search, Update, Delete and Exit are enabled for user to input
    private void clearData () {
        txtAppoint.setText("");
        txtAppoint.setEnabled(false);
        txtApptDate.setText("");
        txtApptDate.setEnabled(false);
        txtApptDesc.setText("");
        txtApptDesc.setEnabled(false);
        submitBtn.setEnabled(false);
        newBtn.setEnabled(true);
        srchBtn.setEnabled(true);
        updtBtn.setEnabled(true);
        delBtn.setEnabled(true);
    }

    // Class to implement a limit to text fields
    // Implementation of limit is done so on textfield set up based on document settings
    // Once character limit is met, no further data can be entered
    class JTextFieldLimit extends PlainDocument {
        private int limit;

        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        JTextFieldLimit(int limit, boolean upper) {
            super();
            this.limit = limit;
        }

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null)
                return;
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}
