//Author Name: Christopher Woodley

//Date: 10/04/2021
//Updated: 03/28/2023

//Course ID: CS-320-T1021

//Description: This is the task service.  It maintains a list of tasks and has capabilities
//for adding and deleting tasks, as well as updating the name and description of existing tasks
//Functions reach out to MongoDB collection task under taskScheduler database

package com.mongodb.taskscheduler;

import java.awt.*;
import java.util.ArrayList;

import com.mongodb.*;
import com.mongodb.client.*;

import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Updates;
import org.bson.types.ObjectId;

import javax.swing.*;

import static com.mongodb.client.model.Filters.eq;

public class TaskService extends Component {

    //Start with an ArrayList of tasks to hold the list of tasks
    ArrayList<Task> taskList = new ArrayList<>();

    //Create an ArrayList of Strings to hold results of queries from MongoDB
    ArrayList<String> taskResults = new ArrayList<>();

    //Add a new task using the Task constructor, then assign the new task to the list
    public String addTask(String taskName, String taskDesc) {
        //Create the new task
        Task task = new Task(taskName, taskDesc);
        //Add newly created task to variable taskList
        taskList.add(task);
        //Connect to MongoDB taskscheduler under collection task
        MongoDatabase taskDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection<Document> collection = taskDB.getCollection("task");

        try {
            //insert newly created task into MongoDB
            InsertOneResult addTask = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("taskName", taskList.get(0).getTaskName())
                    .append("taskDesc", taskList.get(0).getTaskDescription())
            );

        } catch (MongoException me) {
            // JOptionPane.showMessageDialog(ContactService.this, "Unable to add due to error: " + me);
        }

        try {
            //pauses execution for 1 second to allow MongoDB to process incoming data properly
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(TaskService.this, ex);
        }

        // create Document to return field that equals address passed to newly created task
        Document document = collection.find(eq("taskName", taskName)).first();

        // return the TaskID from MongoDB to the original process
        return document.get("taskID").toString();
    }

    //Using Task ID, return a task object
    //If a matching Task ID is not found, return a task object with default values
    public ArrayList<String> getTask(Integer taskID) {
        // Create new instance of array string taskResults
        ArrayList<String> taskResults = new ArrayList<String>();
        //Connect to MongoDB taskscheduler under collection task
        MongoDatabase taskDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = taskDB.getCollection("task");

        // create Document to return field that equals task ID passed by source function
        Document document = (Document) collection.find(eq("taskID", taskID)).first();

        //If nothing returned from above query, set taskResults to null
        //Else, add each value from query results to array
        if (document == null) {
            taskResults = null;
        } else {
            taskResults.add(document.get("taskName").toString());
            taskResults.add(document.get("taskDesc").toString());
        }

        //Return taskResults to source
        return taskResults;
    }

    //Delete contact
    //Using the Task ID to find the right task to delete from list
    //If no matching Task ID can be found in the list, will output it cannot be found.
    //This method of searching for taskID is the same for all update methods below.
    public void deleteTask(Integer taskID) {
        //Connect to MongoDB taskscheduler under collection task
        MongoDatabase taskDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = taskDB.getCollection("task");

        //Create Bson query to equal to task ID
        Bson deleteQuery = eq("taskID", taskID);

        //If nothing returned from above query, prompt user that no task was found
        //Else, run delete query deleteQuery
        if (deleteQuery == null) {
            JOptionPane.showMessageDialog(TaskService.this, "No Task Found Under Task ID " + taskID);
        } else {
            try {
                DeleteResult delete = collection.deleteOne(deleteQuery);
            } catch (MongoException me) {
                System.err.println("Unable to delete due to error: " + me);
            }
        }
    }

    //Update task
    //Use the taskID to find the right task to update data from the database
    //
    public void updateTask(Integer taskID, String taskName, String taskDesc) {
        //Connect to MongoDB taskscheduler under collection task
        MongoDatabase taskDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = taskDB.getCollection("task");

        //Create new document updateTask where taskID is equal to passed value
        Document updateTask = new Document().append("taskID", taskID);

        //Create BSon query to update the passed values to the found record in the collection
        Bson updates = Updates.combine(
                Updates.set("taskName", taskName),
                Updates.set("taskDesc", taskDesc)
        );

        try {
            //Run query to update fields
            UpdateResult update = collection.updateOne(updateTask, updates);
        } catch (MongoException me) {
            System.err.println("Unable to update due to error: " + me);
        }
    }
}
