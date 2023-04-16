//Author Name: Christopher Woodley

//Date: 03/14/2023

//Description: This is the TaskScheduler class. This class is used to run the application and call all
//applicable classes for the current user's run

package com.mongodb.taskscheduler;

public class TaskScheduler {


    public static void main(String args[])
    {
        // Create new MainMenu object
        MainMenu menu = new MainMenu();

        // Launches MainMenu object
        menu.show();
    }
}
