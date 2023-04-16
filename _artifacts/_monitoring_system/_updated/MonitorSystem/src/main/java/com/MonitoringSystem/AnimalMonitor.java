//Author Name: Christopher Woodley

//Date: 03/20/2023

//Description: This is the Animal Monitor class. This class runs the monitor for the individual animals, utilizing a binary search tree
// The binary search tree is loaded upon execution of the class and used for printing, searching, updating, adding and removing data
// Upon exit of the Animal Monitor, the data file is replaced with the updated information as per execution

package com.MonitoringSystem;

import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class AnimalMonitor {

    // instance variable
    Integer animalSelect;
    String searchField;
    public Node root;


    // data structure to hold data for each Animal
    public class Animal {
        String animalType;
        String animalName;
        String animalAge;
        String animalHealth;
        String animalFeeding;
    }

    // Node class to hold the information for each node of the binary search tree
    public class Node {
        // instance variable of Node class
        public Animal animal;
        public Node left;
        public Node right;

        //constructor
        public Node() {
            this.left = null;
            this.right = null;
        }

        public Node(Animal anAnimal) {
            this.animal = anAnimal;
        }
    }

    // displays the submitted animal data to the terminal
    public void displayAnimal(Animal animal) {
        System.out.println("Animal Type: " + animal.animalType);
        System.out.println("Name: " + animal.animalName);
        System.out.println("Age: " + animal.animalAge);
        System.out.println("Health Concerns: " + animal.animalHealth);
        System.out.println("Feeding Schedule: " + animal.animalFeeding);
        System.out.println();
    }

    // constructor for initialize the root to null
    public AnimalMonitor() {
        this.root = null;
    }

    // insert method to insert new data into the tree
    public void insert(Animal animal) {
        // if root is null, create root as a new node of parameter animal passed
        if (this.root == null) {
            root = new Node(animal);
        // else call to insert function passing the current node and animal parameter
        } else {
            insert(this.root, animal);
        }
    }

    // insert functionality to traverse down binary search tree to add new node to proper placement based on animal type
    private void insert(Node node, Animal animal) {
        // Checking if node data is greater than the requested animal type
        if (node.animal.animalType.compareTo(animal.animalType) > 0) {
            // if greater, and the node to the left is null, add the new node to the left of existing node
            if(node.left == null) {
                node.left = new Node(animal);
                // else traverse down binary search tree to the left
            } else {
                this.insert(node.left, animal);
            }
            // if not greater, and the node to the left is null, add a new node to the right of existing node
        } else {
            if (node.right == null) {
                node.right = new Node(animal);
                // else traverse down binary search tree to the right
            } else {
                this.insert(node.right, animal);
            }
        }
    }


    // search functionality to traverse down binary search tree to locate requested animal type
    public Animal search(String animalType) {
        Node node = this.root;
        // repetition loop to all traversal through binary search tree until node is null
        while (node != null) {
            // if the animal type matches node animal type, return the node information
            if (animalType.compareTo(node.animal.animalType) == 0) {
                return node.animal;
                // else if the animal type is less than the node animal type, traverse tree to the left
            } else if (animalType.compareTo(node.animal.animalType) < 0) {
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

    // Traverse down the tree starting from the left to the right, displaying each animal in order
    private void inOrder(Node root) {
        if (root != null) {
            // traverse down the left subtree
            inOrder(root.left);
            // call display to print out the animal data in the node
            displayAnimal(root.animal);
            // traverse down the right subtree
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
        File oldFile = new File("animals.csv");
        // delete the old file
        oldFile.delete();
        // create new file with name for output
        File newFile = new File("animals.csv");
        // create filewriter to add data to the newly created file
        FileWriter write = new FileWriter(newFile, true);
        if (root != null) {
            // traverse down the left subtree
            inOrderOutput(root.left);
            // call output function to write node data to CSV file
            outputToCSV(root.animal, write);
            // traverse down the right subtree
            inOrderOutput(root.right);
        }
        // close the filewriter
        write.close();
    }

    // Delete given node
    public void delete(String animalType)
    {
        delete(this.root, animalType);
    }

    // Delete given node based on animal type requested
    public Node delete(Node node, String animalType)
    {
        // if node is null, return null
        if (node == null)
        {
            return null;
        }
        // recurse down left subtree
        else if (animalType.compareTo(node.animal.animalType) < 0)
        {
            node.left = delete(node.left, animalType);
        }
        // recurse down right subtree
        else if (animalType.compareTo(node.animal.animalType) > 0)
        {
            node.right = delete(node.right, animalType);
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
                node.right = delete(node.right, animalType);
            // left child so need to replace removed node with child
            } else {
                node = predecessor(node);
                node.left = delete(node.left, animalType);
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
    public void loadAnimals(String animalData, AnimalMonitor monitor)  throws FileNotFoundException {

        // instance variable to hold incoming data
        String line;

        // create try..catch instance to ensure errors are properly handled
        try {
            // create reader for incoming data
            BufferedReader bufferedReader = new BufferedReader(new FileReader(animalData));

            // repetition loop to continue until the reader finds a blank line on the file
            while((line = bufferedReader.readLine()) != null)
            {
                // sets incoming data into string list split by the comma delimiter
                String[] data = line.split(",");
                // creates new Animal type
                Animal animal = new Animal();
                // sets the data from the file into the animal fields
                animal.animalType = data[0];
                animal.animalName = data[1];
                animal.animalAge = data[2];
                animal.animalHealth = data[3];
                animal.animalFeeding = data[4];

                // inserts the newly pulled animal into the binary search tree
                monitor.insert(animal);
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
    public void outputToCSV(Animal animalOutput, FileWriter csvWrite) {
        // create try..catch instance to ensure errors are properly handled
        try {
            // append all data from submitted Animal class into CSV file, adding a comma delimiter between each field
            csvWrite.append(animalOutput.animalType);
            csvWrite.append(",");
            csvWrite.append(animalOutput.animalName);
            csvWrite.append(",");
            csvWrite.append(animalOutput.animalAge);
            csvWrite.append(",");
            csvWrite.append(animalOutput.animalHealth);
            csvWrite.append(",");
            csvWrite.append(animalOutput.animalFeeding);
            // add a new line to ensure new line for next set of data
            csvWrite.append("\n");
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


    //main Monitor class to run the Animal Monitor, displaying options and request incoming data from user
    public void Monitor(String animalData) throws IOException {

        // create new Animal Monitor class object monitor
        AnimalMonitor monitor = new AnimalMonitor();
        // create new Animal class object animal
        Animal animal;

        // run the loadAnimals function to pull in data from CSV file into binary search tree
        loadAnimals(animalData, monitor);

        // set animalSelect to zero to ensure repetition loop will execute at least once
        animalSelect = 0;
        // create scanner type scnr to accept incoming data
        Scanner scnr = new Scanner(System.in);
        //repetition loop to continue until animalSelect equals 5
        while (animalSelect != 5) {
            //display menu
            System.out.println("Animal Monitoring System\n");
            System.out.println("1. Find an animal");
            System.out.println("2. Display all animals");
            System.out.println("3. Add an animal");
            System.out.println("4. Edit an animal");
            System.out.println("5. Exit menu");
            System.out.print("Enter selection: ");
            //prompt for user input
            animalSelect = Integer.parseInt(scnr.nextLine());
            // switch statement to handle user input
            switch (animalSelect) {
                // search for animal
                case 1 -> {
                    // request user to input animal type for search
                    System.out.println("Please enter animal type to search: ");
                    searchField = scnr.nextLine();
                    // set animal object to results from search, ensuring search field is set to lowercase characters only
                    animal = monitor.search(searchField.toLowerCase());
                    // if animal object is null, output to user that animal type could not be found
                    if (animal == null) {
                        System.out.println("Animal Type " + searchField + " cannot be found.");

                    }
                    // else run displayAnimal to output animal object details
                    else {
                        displayAnimal(animal);
                    }
                }
                // break out of statement to return
                // display all animals
                case 2 ->
                    // runs InOrder function of monitor to display all animals in alphabetical order
                        monitor.InOrder();

                // break out of statement to return
                // add new animal
                case 3 -> {
                    // create newAnimal Animal object for incoming data
                    Animal newAnimal = new Animal();
                    // request user to input all fields to be added to new animal, storing in new variables
                    System.out.println("Enter animal type: ");
                    String newType = scnr.nextLine();
                    System.out.println("Enter animal name: ");
                    String newName = scnr.nextLine();
                    System.out.println("Enter animal age: ");
                    String newAge = scnr.nextLine();
                    System.out.println("Enter animal health status: ");
                    String newHealth = scnr.nextLine();
                    System.out.println("Enter animal feeding schedule: ");
                    String newFeeding = scnr.nextLine();

                    // add all user input fields to newAnimal object, ensuring all characters are lowercase
                    newAnimal.animalType = newType.toLowerCase();
                    newAnimal.animalName = newName.toLowerCase();
                    newAnimal.animalAge = newAge.toLowerCase();
                    newAnimal.animalHealth = newHealth.toLowerCase();
                    newAnimal.animalFeeding = newFeeding.toLowerCase();

                    // insert newAnimal into binary search tree
                    monitor.insert(newAnimal);
                }

                // break out of statement to return
                // update existing animal
                case 4 -> {
                    // create updateAnimal Animal type to hold data from animal to be edited
                    Animal updateAnimal;
                    // prompt user to enter animal type and store in editType variable
                    System.out.println("Enter animal type to edit: ");
                    String editType = scnr.nextLine();
                    // set updateAnimal to search results
                    updateAnimal = monitor.search(editType.toLowerCase());
                    // repetition loop to continue as long as updateAnimal is null
                    while (updateAnimal == null) {
                        // inform user animal type selected is not found in search and prompt for new input
                        System.out.println("Animal type " + editType + " not found.");
                        System.out.println("Enter animal type to edit: ");
                        editType = scnr.nextLine();
                        // set updateAnimal to search results
                        updateAnimal = monitor.search(editType.toLowerCase());
                    }
                    // display menu prompting user to select what information they wish to edit
                    System.out.println("1. Edit Name");
                    System.out.println("2. Edit Age");
                    System.out.println("3. Edit Health Status");
                    System.out.println("4. Edit Feeding Schedule");
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
                            // set animal name to updateInfo
                                updateAnimal.animalName = updateInfo;
                        case 2 ->
                            // set animal age to updateInfo
                                updateAnimal.animalAge = updateInfo;
                        case 3 ->
                            // set animal health status to updateInfo
                                updateAnimal.animalHealth = updateInfo;
                        case 4 ->
                            // set animal feeding scheduel to updateInfo
                                updateAnimal.animalFeeding = updateInfo;
                        default ->
                            // if any other selection is made, show invalid response
                                System.out.println("Invalid selection. Please select a valid selection (1-4)");
                    }
                    // insert updated animal information into binary search tree
                    monitor.insert(updateAnimal);
                }
                // pull all data from binary search tree and add to CSV file
                case 5 ->
                    // call output function to traverse through binary tree to output to CSV file
                        monitor.InOrderOutput();
                // if no valid selection is made (1-5), notify user and advise of valid options
                default ->
                    // prompt user that invalid selection was made
                        System.out.println("Invalid selection. Please select from the above options only (1-5)");
            }
        }
    }
}
