//Author Name: Christopher Woodley

//Date: 03/14/2023

//Description: This is the Contact form class. It allows users to create, read, update and delete contacts
//Users will need to select the action they wish to handle to activate the required fields on the form
package com.mongodb.taskscheduler;


import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ContactMenu extends JFrame {

    private Container cContact;
    private JLabel title;
    private JLabel label1;
    private JTextField txtContact;
    private JLabel label2;
    private JTextField txtFName;
    private JLabel label3;
    private JTextField txtLName;
    private JLabel label4;
    private JTextField txtPhNum;
    private JLabel label5;
    private JTextArea txtAddr;
    private JTextArea txtStatus;
    private JButton newBtn;
    private JButton srchBtn;
    private JButton updtBtn;
    private JButton submitBtn;
    private JButton delBtn;
    private JButton exitBtn;
    private String action;
    private Integer results;
    private ContactService contactSrvc;
    ArrayList<String> contactResults;


    public ContactMenu()
    {
        // Build the basic form outline and ensure it cannot be closed via the default close operator
        setTitle("Contact Form");
        setBounds(300, 500, 900, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        cContact = getContentPane();
        cContact.setLayout(null);

        // Set title for Form as Contact Form
        title = new JLabel("Contact Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        cContact.add(title);

        // set up all required fields for input
        // Contact requires Contact ID, First Name, Last Name, Address, and Phone Number entries
        // All Text Fields are created as disabled
        // Users must select their action to enable required field for the action

        label1 = new JLabel("Contact ID");
        label1.setFont(new Font("Arial", Font.PLAIN, 15));
        label1.setSize(100, 20);
        label1.setLocation(100, 100);
        cContact.add(label1);

        txtContact = new JTextField();
        txtContact.setFont(new Font("Arial", Font.PLAIN, 15));
        txtContact.setSize(75, 20);
        txtContact.setLocation(200, 100);
        txtContact.setEnabled(false);
        cContact.add(txtContact);

        label2 = new JLabel("First Name");
        label2.setFont(new Font("Arial", Font.PLAIN, 15));
        label2.setSize(150, 20);
        label2.setLocation(100, 150);
        cContact.add(label2);

        txtFName = new JTextField();
        txtFName.setDocument(new JTextFieldLimit(10));
        txtFName.setFont(new Font("Arial", Font.PLAIN, 15));
        txtFName.setSize(190, 20);
        txtFName.setLocation(200, 150);
        txtFName.setEnabled(false);
        cContact.add(txtFName);

        label3 = new JLabel("Last Name");
        label3.setFont(new Font("Arial", Font.PLAIN, 15));
        label3.setSize(150, 20);
        label3.setLocation(100, 200);
        cContact.add(label3);

        txtLName = new JTextField();
        txtLName.setDocument(new JTextFieldLimit(10));
        txtLName.setFont(new Font("Arial", Font.PLAIN, 15));
        txtLName.setSize(190, 20);
        txtLName.setLocation(200, 200);
        txtLName.setEnabled(false);
        cContact.add(txtLName);

        label4 = new JLabel("Phone Number");
        label4.setFont(new Font("Arial", Font.PLAIN, 15));
        label4.setSize(150, 20);
        label4.setLocation(100, 250);
        cContact.add(label4);

        txtPhNum = new JTextField();
        txtPhNum.setDocument(new JTextFieldLimit(10));
        txtPhNum.setFont(new Font("Arial", Font.PLAIN, 15));
        txtPhNum.setSize(150, 20);
        txtPhNum.setLocation(200, 250);
        txtPhNum.setEnabled(false);
        cContact.add(txtPhNum);

        label5 = new JLabel("Address");
        label5.setFont(new Font("Arial", Font.PLAIN, 15));
        label5.setSize(150, 20);
        label5.setLocation(100, 300);
        cContact.add(label5);

        txtAddr = new JTextArea();
        txtAddr.setDocument(new JTextFieldLimit(30));
        txtAddr.setFont(new Font("Arial", Font.PLAIN, 15));
        txtAddr.setSize(200,100);
        txtAddr.setLocation(200, 300);
        txtAddr.setEnabled(false);
        cContact.add(txtAddr);

        // Create Status section which outputs changes being made to form and operations to be handled

        txtStatus = new JTextArea();
        txtStatus.setFont(new Font("Arial", Font.PLAIN, 15));
        txtStatus.setSize(300, 400);
        txtStatus.setLocation(500, 100);
        txtStatus.setLineWrap(true);
        txtStatus.setEditable(false);
        cContact.add(txtStatus);

        // Set up buttons to be accessed by user depending on their requirements
        // New will allow user to enter in First Name, Last Name, Address and Phone Number to create a new contact

        newBtn = new JButton("New");
        newBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        newBtn.setSize(100, 20);
        newBtn.setLocation(75, 450);
        cContact.add(newBtn);

        // Search will allow user to enter Contact ID to locate an existing contact in database and return results
        // to status window
        srchBtn = new JButton("Search");
        srchBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        srchBtn.setSize(100, 20);
        srchBtn.setLocation(200, 450);
        cContact.add(srchBtn);

        // Update will allow user to locate an existing contact using Contact ID and make changes to data elements
        updtBtn = new JButton("Update");
        updtBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        updtBtn.setSize(100, 20);
        updtBtn.setLocation(325, 450);
        cContact.add(updtBtn);

        // Submit will be used when user to submit their query for New, Search, Update and Delete
        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        submitBtn.setSize(100, 20);
        submitBtn.setLocation(75, 500);
        submitBtn.setEnabled(false);
        cContact.add(submitBtn);

        // Delete will allow user to enter Contact ID to delete an existing contact in database if found
        delBtn = new JButton("Delete");
        delBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        delBtn.setSize(100, 20);
        delBtn.setLocation(200, 500);
        delBtn.setEnabled(true);
        cContact.add(delBtn);

        // Exit will close the current form and user will be returned to the Main Menu
        exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        exitBtn.setSize(100, 20);
        exitBtn.setLocation(325, 500);
        cContact.add(exitBtn);

        setVisible(true);

        // Key Listener added to Phone Number field to ensure only numeric input can be entered, else it will pop-up error and clear existing data from field
        txtPhNum.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = txtPhNum.getText();
                int i = value.length();
                if ((ke.getKeyChar() >= '0') && (ke.getKeyChar() <= '9')) {
                    txtPhNum.setEditable(true);
                } else {
                    txtPhNum.setEditable(false);
                    txtPhNum.setText("");
                    JOptionPane.showMessageDialog(ContactMenu.this, "Please input numbers only");
                    txtPhNum.setEditable(true);
                }
            }
        });

        // Adding Action Listener to New Button which enables the First Name, Last Name, Phone Number and Address Fields as well as Submit Button
        // Disables all other buttons except for Submit and Exit until action is completed
        // Adds status that New was selected
        newBtn.addActionListener(e -> {
            txtFName.setEnabled(true);
            txtLName.setEnabled(true);
            txtPhNum.setEnabled(true);
            txtAddr.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            //JOptionPane.showMessageDialog(ContactMenu.this, "New functionality to be added in next release");
            action = "Create";
            txtStatus.append("New Button selected \n");
        });

        // Adding Action Listener to Search Button which enables the Contact ID field and Submit Button
        // // Disables all other buttons except for Submit and Exit until action is completed
        // Adds status that Search was selected
        srchBtn.addActionListener(e -> {
            txtContact.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            //JOptionPane.showMessageDialog(ContactMenu.this, "Search functionality to be added in next release");
            action = "Read";
            txtStatus.append("Search Button selected \n");
        });

        // Adding Action Listener to Update Button which enables the Contact ID field and Submit Button
        // Disables all other buttons except for Submit and Exit until action is completed
        // Adds status that Search was selected
        updtBtn.addActionListener(e -> {
            txtContact.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            //JOptionPane.showMessageDialog(ContactMenu.this, "Update functionality to be added in next release");
            action = "UpdateSearch";
            txtStatus.append("Update Button selected \n");
        });

        delBtn.addActionListener(e -> {
            txtContact.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            //JOptionPane.showMessageDialog(ContactMenu.this, "Delete functionality to be added in next release");
            action = "Delete";
            txtStatus.append("Delete Button selected \n");
        });

        // Adding Action Listener to Submit button to handle submission depending on action taken by user
        // If New is selected, will run through the Create process by creating a new Contact item and append to the database
        // If Search is selected, will allow user to input Contact ID and submit to return results if found, else show no result
        // If Update is selected, will allow user to input Contact ID and submit to return results to textfields and allow user to input changes to database
        // If Delete is selected, will allow user to input Contact ID and submit to delete record from database if found, else will output error message
        // Once completes, will clear text fields and sets buttons back to original state
        submitBtn.addActionListener(e -> {
            // Create new instance of ContactService class
            ContactService contactSrvc = new ContactService();
            switch(action)
            {
                // Run through Create action if New is selected
                case "Create":
                    // sets results to Contact ID created in MongoDB for newly created record
                    results = Integer.valueOf(contactSrvc.addContact(txtFName.getText(), txtLName.getText(), txtPhNum.getText(), txtAddr.getText()));
                    // outputs Contact ID to Status output screen
                    txtStatus.append("Submitted new contact under Contact ID " + results + "\n");
                    // run clear data to set form back to start
                    clearData();
                    break;
                // Run through Read action if Search is selected
                case "Read":
                    // set contactResults to hold data returned by function getContact of ContactServices using user inputted Contact ID
                    contactResults = contactSrvc.getContact(Integer.valueOf(txtContact.getText()));
                    // if no record found, output messages to user that no contact could be found and exits further execution
                    // clears all data elements and ensures action is set to null
                    if (contactResults == null) {
                        JOptionPane.showMessageDialog(ContactMenu.this, "No Contact Found Under Contact ID " + txtContact.getText());
                        clearData();
                        action = "";
                        break;
                        // else output to Status window the fields returned
                    } else {
                        txtStatus.append("First Name: " + contactResults.get(0) + "\n");
                        txtStatus.append("Last Name: " + contactResults.get(1) + "\n");
                        txtStatus.append("Phone Number: " + contactResults.get(2) + "\n");
                        txtStatus.append("Address: " + contactResults.get(3) + "\n");
                    }
                    // run clear data to set form back to start
                    clearData();
                    break;
                    //Run through search portion of Update action if Update selected
                case "UpdateSearch":
                    // set contactResults to hold data returned by function getContact of ContactServices using user inputted Contact ID
                    contactResults = contactSrvc.getContact(Integer.valueOf(txtContact.getText()));
                    // if no record found, output messages to user that no contact could be found and exits further execution
                    // Clears all data elements and ensures action is set to null
                    if (contactResults == null) {
                        JOptionPane.showMessageDialog(ContactMenu.this, "No Contact Found Under Contact ID " + txtContact.getText());
                        clearData();
                        action = "";
                        break;
                        // else output to appropriate textfield the fields returned, enabled the fields for updates
                    } else {
                        txtFName.setEnabled(true);
                        txtFName.setText(contactResults.get(0));
                        txtLName.setEnabled(true);
                        txtLName.setText(contactResults.get(1));
                        txtPhNum.setEnabled(true);
                        txtPhNum.setText(contactResults.get(2));
                        txtAddr.setEnabled(true);
                        txtAddr.setText(contactResults.get(3));
                        txtContact.setEnabled(false);
                    }
                    // set UpdateSubmit
                    action = "UpdateSubmit";
                    break;
                // Run through Update Submission action if Update is selected and a record is located via UpdateSearch
                case "UpdateSubmit":
                    //Runs the updateContact function of ContactService, passing the textfields with updated information if applicable
                    contactSrvc.updateContact(Integer.valueOf(txtContact.getText()), txtFName.getText(), txtLName.getText(), txtAddr.getText(), txtPhNum.getText());
                    //Outputs message that update is completed for Contact ID given
                    txtStatus.append(txtContact.getText() + " updated \n");
                    // run clear data to set form back to start
                    clearData();
                    break;
                // Run through Delete action if Delete is selected
                case "Delete":
                    //Runs the deleteContact function of ContactService for the Contact ID given
                    contactSrvc.deleteContact(Integer.valueOf(txtContact.getText()));
                    //Outputs message that delete is completed for Contact ID given
                    txtStatus.append(txtContact.getText() + " deleted \n");
                    // run clear data to set form back to start
                    clearData();
                    break;
            }

        });

        // Adding Action Listener for Exit Button
        // Closes the Contact Form only
        exitBtn.addActionListener(e -> {
            this.dispose();
        });
    }

    // Function to clear all data elements from form to allow further input from user
    // Clears all textfields to null and disables them
    // Ensures only New, Search, Update, Delete and Exit are enabled for user to input
    private void clearData() {
        txtContact.setText("");
        txtContact.setEnabled(false);
        txtFName.setText("");
        txtFName.setEnabled(false);
        txtLName.setText("");
        txtLName.setEnabled(false);
        txtAddr.setText("");
        txtAddr.setEnabled(false);
        txtPhNum.setText("");
        txtPhNum.setEnabled(false);
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
