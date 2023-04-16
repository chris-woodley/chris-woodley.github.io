//Author Name: Christopher Woodley

//Date: 03/20/2023

//Description: This is the Monitor System class. This class runs the applications main functionality and
//provides the main menu for user input

package com.MonitoringSystem;

import java.util.Scanner;
import java.io.IOException;

public class MonitoringSystem {

    // Instance variables
    public static int menuSelect;
    public static String inputFile;


    // creates the Main Menu for users to access each monitor system
    public static void monitorMenu () throws IOException{
        //creates new input scanner scnr
        Scanner scnr = new Scanner(System.in);
        //sets repetition loop condition value under variable inputDone to ensure at least one repetition
        menuSelect = 0;
        //repetition loop to continue until inputDone is set to true value by inputting exit from user menu
        while (menuSelect != 3) {
            //create user menu
            System.out.println("Zoo Monitoring System\n");
            System.out.println("1. Monitor an animal");
            System.out.println("2. Monitor a habitat");
            System.out.println("3. Exit Monitoring System");
            System.out.print("Enter selection: ");
            //prompt for user input
            menuSelect = Integer.parseInt(scnr.nextLine());

            //switch case to handle user input functions
            switch (menuSelect) {
                // pass animal CSV file to AnimalMonitor to display the Animal Monitor class
                case 1 -> {
                    //set inputFile to animal.csv file
                    inputFile = "animals.csv";
                    //create object of class AnimalMonitor
                    AnimalMonitor animalMonitor = new AnimalMonitor();
                    //call out to animal monitor to run Animal Monitor program
                    animalMonitor.Monitor(inputFile);
                }
                // pass habitat CSV file to HabitatMonitor to display the Habitat Monitor class
                case 2 -> {
                    //set inputFile to habitat.csv file
                    inputFile = "habitats.csv";
                    //create object of class HabitatMonitor
                    HabitatMonitor habitatMonitor = new HabitatMonitor();
                    //call out to animal monitor to run Habitat Monitor program
                    habitatMonitor.Monitor(inputFile);
                }
                // Exits out of the application and displaying a goobye message to user
                case 3 ->
                    //display goodbye message to user
                        System.out.println("Goodbye");
                // If any other value is entered, display error message to user and inform of valid options
                default ->
                    //show error message if invalid entry entered
                        System.out.println("Invalid input. Please select one of the above options (1-3)");
            }
        }
    }

    //main function
    public static void main(String[] args) throws IOException {

        // Call out to function monitorMenu to display main menu to user
        monitorMenu();

    }
}


