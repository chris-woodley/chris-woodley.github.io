//Author Name: Christopher Woodley

//Date: 03/20/2023

//Description: This is the Habitat Monitor class. This class runs the monitor for the individual habitats, utilizing a binary search tree
// The binary search tree is loaded upon execution of the class and used for printing, searching, updating, adding and removing data
// Upon exit of the Habitat Monitor, the data file is replaced with the updated information as per execution

package com.MonitoringSystem;

import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class HabitatMonitor {

    // instance variable
    Integer habitatSelect;
    String searchField;
    public Node root;

    public class Habitat {
        String habitatType;
        String habitatTemperature;
        String habitatFood;
        String habitatClean;
    }

    // Node class to hold the information for each node of the binary search tree
    public class Node {
        // instance variable of Node class
        public Habitat habitat;
        public Node left;
        public Node right;

        //constructor
        public Node() {
            this.left = null;
            this.right = null;
        }

        public Node(Habitat aHabitat) {
            this.habitat = aHabitat;
        }
    }

    // displays the submitted habitat data to the terminal
    public void displayHabitat(Habitat habitat) {
        System.out.println("Habitat Type: " + habitat.habitatType);
        System.out.println("Habitat Temperature: " + habitat.habitatTemperature);
        System.out.println("Habitat Food Source: " + habitat.habitatFood);
        System.out.println("Habitat Cleanliness: " + habitat.habitatClean);
        System.out.println();
    }

    // constructor for initialize the root to null
    public HabitatMonitor() {
        this.root = null;
    }

    // insert method to insert new data
    public void insert(Habitat habitat) {
        if (this.root == null) {
            root = new Node(habitat);
        } else {
            insert(this.root, habitat);
        }
    }

    // insert functionality to traverse down binary search tree to add new node to proper placement based on habitat type
    private void insert(Node node, Habitat habitat) {
        // Checking if node data is greater than the requested habitat type
        if (node.habitat.habitatType.compareTo(habitat.habitatType) > 0) {
            // if greater, and the node to the left is null, add the new node to the left of existing node
            if(node.left == null) {
                node.left = new Node(habitat);
                // else traverse down binary search tree to the left
            } else {
                this.insert(node.left, habitat);
            }
            // if not greater, and the node to the left is null, add a new node to the right of existing node
        } else {
            if (node.right == null) {
                node.right = new Node(habitat);
                // else traverse down binary search tree to the right
            } else {
                this.insert(node.right, habitat);
            }
        }
    }

    // search functionality to traverse down binary search tree to locate requested habitat type
    public Habitat search(String habitatType) {
        Node node = this.root;
        // repetition loop to all traversal through binary search tree until node is null
        while (node != null) {
            // if the habitat type matches node habitat type, return the node information
            if (habitatType.compareTo(node.habitat.habitatType) == 0) {
                return node.habitat;
                // else if the habitat type is less than the node habitat type, traverse tree to the left
            } else if (habitatType.compareTo(node.habitat.habitatType) < 0) {
                node = node.left;
                // else traverse tree to the right
            } else {
                node = node.right;
            }
        }
        // if no result is found, return null
        return null;
    }

    // Traversing the tree for output to terminal
    public void InOrder() {
        this.inOrder(root);
    }

    // Traverse down the tree starting from the left to the right, displaying each habitat in order
    private void inOrder(Node root) {
        if (root != null) {
            inOrder(root.left);
            displayHabitat(root.habitat);
            inOrder(root.right);
        }
    }

    // Traversing the tree for output to CSV file
    public void InOrderOutput() throws IOException {
        this.inOrderOutput(root);
    }

    // Traverse down the tree starting from the left to the right, outputting each item to the CSV file
    private void inOrderOutput(Node root) throws IOException {
        // set file name to old file
        File oldFile = new File("habitats.csv");
        // delete the old file
        oldFile.delete();
        // create new file with name for output
        File newFile = new File("habitats.csv");
        // create filewriter to add data to the newly created file
        FileWriter write = new FileWriter(newFile, true);
        if (root != null) {
            inOrderOutput(root.left);
            outputToCSV(root.habitat, write);
            inOrderOutput(root.right);
        }
        // close the filewriter
        write.close();
    }

    // Delete given node
    public void delete(String habitatType)
    {
        delete(this.root, habitatType);
    }

    // Delete given node based on habitat type requested
    public Node delete(Node node, String habitatType)
    {
        // if node is null, return null
        if (node == null)
        {
            return null;
        }
        // recurse down left subtree
        else if (habitatType.compareTo(node.habitat.habitatType) < 0)
        {
            node.left = delete(node.left, habitatType);
        }
        // recurse down right subtree
        else if (habitatType.compareTo(node.habitat.habitatType) > 0)
        {
            node.right = delete(node.right, habitatType);
        }
        // if both are equal
        else {
            // no children so this is a leaf node so set node to null
            if (node.left == null && node.right == null) {
                node = null;
            }
            // right child so need to replace removed node with child
            else if (node.right != null) {
                node = successor(node);
                node.right = delete(node.right, habitatType);
                // left child so need to replace removed node with child
            } else {
                node = predecessor(node);
                node.left = delete(node.left, habitatType);
            }
        }
        // return current node
        return node;
    }

    // traverse down tree to the right to locate child leafs of given node
    private Node successor(Node node) {
        node = node.right;
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // traverse down tree to the left to locate child leafs of given node
    private Node predecessor(Node node) {
        node = node.left;
        while(node.right != null) {
            node = node.right;
        }
        return node;
    }

    // load data from csv file to the application
    public void loadHabitats(String habitatData, HabitatMonitor monitor)  throws FileNotFoundException {

        // instance variable to hold incoming data
        String line;

        // create try..catch instance to ensure errors are properly handled
        try {
            // create reader for incoming data
            BufferedReader bufferedReader = new BufferedReader(new FileReader(habitatData));

            // repetition loop to continue until the reader finds a blank line on the file
            while((line = bufferedReader.readLine()) != null)
            {
                // sets incoming data into string list split by the comma delimiter
                String[] data = line.split(",");
                // creates new Habitat type
                Habitat habitat = new Habitat();
                // sets the data from the file into the habitat fields
                habitat.habitatType = data[0];
                habitat.habitatTemperature = data[1];
                habitat.habitatFood = data[2];
                habitat.habitatClean = data[3];

                // inserts the newly pulled habitat into the binary search tree
                monitor.insert(habitat);
            }
            // close buffered reader
            bufferedReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // output data to CSV file
    public void outputToCSV(Habitat habitatOutput, FileWriter csvWrite) {
        // create try..catch instance to ensure errors are properly handled
        try {
            // append all data from submitted Habitat class into CSV file, adding a comma delimiter between each field
            csvWrite.append(habitatOutput.habitatType);
            csvWrite.append(",");
            csvWrite.append(habitatOutput.habitatTemperature);
            csvWrite.append(",");
            csvWrite.append(habitatOutput.habitatFood);
            csvWrite.append(",");
            csvWrite.append(habitatOutput.habitatClean);
            // add a new line to ensure new line for next set of data
            csvWrite.append("\n");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    //main Monitor class to run the Habitat Monitor, displaying options and request incoming data from user
    public void Monitor(String habitatData) throws IOException {

        // create new Habitat Monitor class object monitor
        HabitatMonitor monitor = new HabitatMonitor();
        // create new Habitat class object habitat
        HabitatMonitor.Habitat habitat;

        // run the loadHabitats function to pull in data from CSV file into binary search tree
        loadHabitats(habitatData, monitor);

        // set habitatSelect to zero to ensure repetition loop will execute at least once
        habitatSelect = 0;
        // create scanner type scnr to accept incoming data
        Scanner scnr = new Scanner(System.in);
        //repetition loop to continue until habitatSelect equals 5
        while (habitatSelect != 5) {
            //display menu
            System.out.println("Habitat Monitoring System\n");
            System.out.println("1. Find a habitat");
            System.out.println("2. Display all habitats");
            System.out.println("3. Add a habitat");
            System.out.println("4. Edit a habitat");
            System.out.println("5. Exit menu");
            System.out.print("Enter selection: ");
            //prompt for user input
            habitatSelect = Integer.parseInt(scnr.nextLine());
            switch (habitatSelect) {
                // request user to input habitat type for search
                case 1 -> {
                    // request user to input habitat type for search
                    System.out.println("Please enter habitat type to search: ");
                    searchField = scnr.nextLine();
                    // set habitat object to results from search, ensuring search field is set to lowercase characters only
                    habitat = monitor.search(searchField.toLowerCase());
                    // if habitat object is null, output to user that habitat type could not be found
                    if (habitat == null) {
                        System.out.println("Habitat Type " + searchField + " cannot be found.");

                    }
                    // else run displayHabitat to output habitat object details
                    else {
                        displayHabitat(habitat);
                    }
                }
                // break out of statement to return
                // display all habitats
                case 2 ->
                    // runs InOrder function of monitor to display all habitats in alphabetical order
                        monitor.InOrder();

                // break out of statement to return
                case 3 -> {
                    // create newHabitat Habitat object for incoming data
                    Habitat newHabitat = new Habitat();
                    // request user to input all fields to be added to new habitat, storing in new variables
                    System.out.println("Enter habitat type: ");
                    String newType = scnr.nextLine();
                    System.out.println("Enter habitat temperature: ");
                    String newTemperature = scnr.nextLine();
                    System.out.println("Enter habitat food source: ");
                    String newFood = scnr.nextLine();
                    System.out.println("Enter habitat cleanliness: ");
                    String newClean = scnr.nextLine();

                    // add all user input fields to newHabitat object, ensuring all characters are lowercase
                    newHabitat.habitatType = newType.toLowerCase();
                    newHabitat.habitatTemperature = newTemperature.toLowerCase();
                    newHabitat.habitatFood = newFood.toLowerCase();
                    newHabitat.habitatClean = newClean.toLowerCase();

                    // insert newHabitat into binary search tree
                    monitor.insert(newHabitat);
                }

                // break out of statement to return
                // update existing habitat
                case 4 -> {
                    // create updateHabitat Habitat type to hold data from habitat to be edited
                    Habitat updateHabitat;
                    // prompt user to enter habitat type and store in editType variable
                    System.out.println("Enter habitat type to edit: ");
                    String editType = scnr.nextLine();
                    // set updateHabitat to search results
                    updateHabitat = monitor.search(editType.toLowerCase());
                    // repetition loop to continue as long as updateHabitat is null
                    while (updateHabitat == null) {
                        // inform user habitat type selected is not found in search and prompt for new input
                        System.out.println("Habitat type " + editType + " not found.");
                        System.out.println("Enter habitat type to edit: ");
                        editType = scnr.nextLine();
                        // set updateHabitat to search results
                        updateHabitat = monitor.search(editType.toLowerCase());
                    }
                    // display menu prompting user to select what information they wish to edit
                    System.out.println("1. Edit Temperature");
                    System.out.println("2. Edit Food Source");
                    System.out.println("3. Edit Cleanliness");
                    System.out.println("Enter selection: ");
                    int editSelect = Integer.parseInt(scnr.nextLine());
                    // prompt user to enter new value for the selected update
                    System.out.println("Enter new value for selection: ");
                    String updateInfo = scnr.nextLine();
                    // delete existing item from the binary search tree
                    monitor.delete(editType);
                    // switch statement to update depending on selection from user
                    switch (editSelect) {
                        case 1 ->
                            // set habitat temperature to updateInfo
                                updateHabitat.habitatTemperature = updateInfo;
                        case 2 ->
                            // set habitat food source to updateInfo
                                updateHabitat.habitatFood = updateInfo;
                        case 3 ->
                            // set habitat cleanliness to updateInfo
                                updateHabitat.habitatClean = updateInfo;
                        default ->
                            // if any other selection is made, show invalid response
                                System.out.println("Invalid selection. Please select a valid selection (1-3)");
                    }
                    // insert updated habitat information into binary search tree
                    monitor.insert(updateHabitat);
                }
                // pull all data from binary search tree and add to CSV file
                case 5 ->
                    // call output function to traverse through binary tree to output to CSV file
                        monitor.InOrderOutput();
                // if no valid selection is made (1-5), notify user and advise of valid options
                default -> System.out.println("Invalid selection. Please select from the above options only (1-5)");
            }
        }
    }
}