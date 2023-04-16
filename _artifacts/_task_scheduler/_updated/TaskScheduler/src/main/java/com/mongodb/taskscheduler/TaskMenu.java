//Author Name: Christopher Woodley

//Date: 03/14/2023

//Description: This is the Task form class. It allows users to create, read, update and delete tasks
//Users will need to select the action they wish to handle to activate the required fields on the form
package com.mongodb.taskscheduler;


import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.ArrayList;


public class TaskMenu extends JFrame {

    private Container cTask;
    private JLabel title;
    private JLabel label1;
    private JTextField txtTask;
    private JLabel label2;
    private JTextField txtTaskName;
    private JLabel label3;
    private JTextField txtTaskDesc;
    private JTextArea txtStatus;
    private JButton newBtn;
    private JButton srchBtn;
    private JButton updtBtn;
    private JButton submitBtn;
    private JButton delBtn;
    private JButton exitBtn;
    private String action;
    private Integer resultsID;
    private ArrayList<String> resultsArray;
    private TaskService taskSrvc;

    public TaskMenu()
    {
        // Build the basic form outline and ensure it cannot be closed via the default close operator
        setTitle("Task Form");
        setBounds(300, 500, 900, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);

        cTask = getContentPane();
        cTask.setLayout(null);

        // Set title for Form as Task Form
        title = new JLabel("Task Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        cTask.add(title);

        // set up all required fields for input
        // Task requires Task ID, Task Name, and Task Description entries
        // All Text Fields are created as disabled
        // Users must select their action to enable required field for the action
        label1 = new JLabel("Task ID");
        label1.setFont(new Font("Arial", Font.PLAIN, 15));
        label1.setSize(100, 20);
        label1.setLocation(100, 100);
        cTask.add(label1);

        txtTask = new JTextField();
        txtTask.setFont(new Font("Arial", Font.PLAIN, 15));
        txtTask.setSize(75, 20);
        txtTask.setLocation(250, 100);
        txtTask.setEnabled(false);
        cTask.add(txtTask);

        label2 = new JLabel("Task Name");
        label2.setFont(new Font("Arial", Font.PLAIN, 15));
        label2.setSize(150, 20);
        label2.setLocation(100, 150);
        cTask.add(label2);

        txtTaskName = new JTextField();
        txtTaskName.setDocument(new JTextFieldLimit(20));
        txtTaskName.setFont(new Font("Arial", Font.PLAIN, 15));
        txtTaskName.setSize(190, 20);
        txtTaskName.setLocation(250, 150);
        txtTaskName.setEnabled(false);
        cTask.add(txtTaskName);

        label3 = new JLabel("Task Description");
        label3.setFont(new Font("Arial", Font.PLAIN, 15));
        label3.setSize(150, 20);
        label3.setLocation(100, 200);
        cTask.add(label3);

        txtTaskDesc = new JTextField();
        txtTaskName.setDocument(new JTextFieldLimit(20));
        txtTaskDesc.setFont(new Font("Arial", Font.PLAIN, 15));
        txtTaskDesc.setSize(190, 20);
        txtTaskDesc.setLocation(250, 200);
        txtTaskDesc.setEnabled(false);
        cTask.add(txtTaskDesc);

        // Create Status section which outputs changes being made to form and operations to be handled
        txtStatus = new JTextArea();
        txtStatus.setFont(new Font("Arial", Font.PLAIN, 15));
        txtStatus.setSize(300, 400);
        txtStatus.setLocation(500, 100);
        txtStatus.setLineWrap(true);
        txtStatus.setEditable(false);
        cTask.add(txtStatus);

        // Set up buttons to be accessed by user depending on their requirements
        // New will allow user to enter in Task Name and Task Description to create a new task
        newBtn = new JButton("New");
        newBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        newBtn.setSize(100, 20);
        newBtn.setLocation(75, 450);
        cTask.add(newBtn);

        // Search will allow user to enter Task ID to locate an existing contact in database and return results
        // to status window
        srchBtn = new JButton("Search");
        srchBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        srchBtn.setSize(100, 20);
        srchBtn.setLocation(200, 450);
        cTask.add(srchBtn);

        // Update will allow user to locate an existing task using Task ID and make changes to data elements
        updtBtn = new JButton("Update");
        updtBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        updtBtn.setSize(100, 20);
        updtBtn.setLocation(325, 450);
        cTask.add(updtBtn);

        // Submit will be used when user to submit their query for New, Search, Update and Delete
        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        submitBtn.setSize(100, 20);
        submitBtn.setLocation(75, 500);
        submitBtn.setEnabled(false);
        cTask.add(submitBtn);

        // Delete will allow user to enter Task ID to delete an existing contact in database if found
        delBtn = new JButton("Delete");
        delBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        delBtn.setSize(100, 20);
        delBtn.setLocation(200, 500);
        cTask.add(delBtn);

        // Exit will close the current form and user will be returned to the Main Menu
        exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        exitBtn.setSize(100, 20);
        exitBtn.setLocation(325, 500);
        cTask.add(exitBtn);

        setVisible(true);

        // Adding Action Listener to New Button which enables the Task Name and Task Description as well as Submit Button
        // Disables all other buttons except for Submit and Exit until action is completed
        // Adds status that New was selected
        newBtn.addActionListener(e -> {
            txtTaskName.setEnabled(true);
            txtTaskDesc.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            //JOptionPane.showMessageDialog(TaskMenu.this, "Update functionality to be added in next release");
            action = "Create";
            txtStatus.append("New Button selected \n");
        });

        // Adding Action Listener to Search Button which enables the Task ID field and Submit Button
        // Disables all other buttons except for Submit and Exit until action is completed
        // Adds status that Search was selected
        srchBtn.addActionListener(e -> {
            txtTask.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            action = "Read";
            txtStatus.append("Search Button selected \n");
        });

        // Adding Action Listener to Update Button which enables the Task ID field and Submit Button
        // Disables all other buttons except for Submit and Exit until action is completed
        // Adds status that Update was selected
        updtBtn.addActionListener(e -> {
            txtTask.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            action = "UpdateSearch";
            txtStatus.append("Update Button selected \n");
        });

        // Adding Action Listener to Delete Button which enables the Task ID field and Submit Button
        // Disables all other buttons except for Submit and Exit until action is completed
        // Adds status that delete was selected
        delBtn.addActionListener(e -> {
            txtTask.setEnabled(true);
            submitBtn.setEnabled(true);
            newBtn.setEnabled(false);
            srchBtn.setEnabled(false);
            updtBtn.setEnabled(false);
            delBtn.setEnabled(false);
            action = "Delete";
            txtStatus.append("Delete Button selected \n");
        });

        // Adding Action Listener to Submit button to handle submission depending on action taken by user
        // If New is selected, will run through the Create process by creating a new Task item and append to the database
        // If Search is selected, will allow user to input Task ID and submit to return results if found, else show no result
        // If Update is selected, will allow user to input Task ID and submit to return results to textfields and allow user to input changes to database
        // If Delete is selected, will allow user to input Task ID and submit to delete record from database if found, else will output error message
        // Once completed, will clear text fields and sets button back to original state
        submitBtn.addActionListener(e -> {
            // Create instance of TaskService class
            TaskService taskSrvc = new TaskService();
            switch(action)
            {
                // Run through Create action if New is selected
                case "Create":
                    // sets resultsID to Task ID created in MongoDB for newly created record
                    resultsID = Integer.valueOf(taskSrvc.addTask(txtTaskName.getText(), txtTaskDesc.getText()));
                    // outputs Task ID to Status output screen
                    txtStatus.append("Submitted new task under Task ID " + resultsID + "\n");
                    // run clear data to set form back to start
                    clearData();
                    break;
                // Run through Read action if Search is selected
                case "Read" :
                    // sets resultsArray to hold data returned by function getTask of TaskService using user inputted Task ID
                    resultsArray = taskSrvc.getTask(Integer.valueOf(txtTask.getText()));
                    // if no record found, output messages to user that no task could be found and exits further execution
                    // clears all data elements and ensures action is set to null
                    if (resultsArray == null) {
                        JOptionPane.showMessageDialog(TaskMenu.this, "No Task Found Under Task ID " + txtTask.getText());
                        clearData();
                        action = "";
                        break;
                    // else output to Status window the fields returned
                    } else {
                        txtStatus.append("Task Name: " + resultsArray.get(0) + "\n");
                        txtStatus.append("Task Description: " + resultsArray.get(1) + "\n");
                    }
                    // run clear data to set form back to start
                    clearData();
                    break;
                //Run through search portion of Update action if Update selected
                case "UpdateSearch" :
                    // set resultsArray to hold data returned by function getTask of TaskServices using user inputted Task ID
                    resultsArray = taskSrvc.getTask(Integer.valueOf(txtTask.getText()));
                    // if no record found, output messages to user that no task could be found and exits further execution
                    // Clears all data elements and ensures action is set to null
                    if (resultsArray == null) {
                        JOptionPane.showMessageDialog(TaskMenu.this, "No Task Found Under Task ID " + txtTask.getText());
                        clearData();
                        action = "";
                        break;
                        // else output to appropriate textfield the fields returned, enabled the fields for updates
                    } else {
                        txtTaskName.setEnabled(true);
                        txtTaskName.setText(resultsArray.get(0));
                        txtTaskDesc.setEnabled(true);
                        txtTaskDesc.setText(resultsArray.get(1));
                        txtTask.setEnabled(false);
                    }
                    // Updates action to Submit to ensure updated functionality for submission will execute
                    action = "UpdateSubmit";
                    break;
                // Run through Update Submission action if Update is selected and a record is located via UpdateSearch
                case "UpdateSubmit" :
                    //Runs the updateTask function of TaskService, passing the textfields with updated information if applicable
                    taskSrvc.updateTask(Integer.valueOf(txtTask.getText()), txtTaskName.getText(), txtTaskDesc.getText());
                    //Outputs message that update is completed for Task ID given
                    txtStatus.append(txtTask.getText() + " updated \n");
                    // run clear data to set form back to start
                    clearData();
                    break;
                // Run through Delete action if Delete is selected
                case "Delete":
                    //Runs the deleteTask function of TaskService for the Task ID given
                    taskSrvc.deleteTask(Integer.valueOf(txtTask.getText()));
                    //Outputs message that delete is completed for Task ID given
                    txtStatus.append(txtTask.getText() + " deleted \n");
                    // run clear data to set form back to start
                    clearData();
            }
        });

        // Adding Action Listener for Exit Button
        // Closes the Task Form only
        exitBtn.addActionListener(e -> {
            this.dispose();
        });
    }

    // Function to clear all data elements from form to allow further input from user
    // Clears all textfields to null and disables them
    // Ensures only New, Search, Update, Delete and Exit are enabled for user to input
    private void clearData() {
        txtTask.setText("");
        txtTask.setEnabled(false);
        txtTaskName.setText("");
        txtTaskName.setEnabled(false);
        txtTaskDesc.setText("");
        txtTaskDesc.setEnabled(false);
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
