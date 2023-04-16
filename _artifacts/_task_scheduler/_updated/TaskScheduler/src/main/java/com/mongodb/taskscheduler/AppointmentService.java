//Author Name: Christopher Woodley

//Date: 10/04/2021
//Updated: 03/28/2023

//Course ID: CS-320-T1021

//Description: This is the appointment service. It maintains a list of appointments and has capabilities
//for adding and deleting appointments.
//Functions reach out to MongoDB collection appointment under taskScheduler database

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

public class AppointmentService extends Component {
    //Start with an ArrayList of appointments to hold the list of appointments
    ArrayList<Appointment> appointmentList = new ArrayList<>();

    //Create an ArrayList of Strings to hold results of queries from MongoDB
    ArrayList<String> appointmentResults = new ArrayList<>();

    //Adds a new appointment using the Appointment constructor, then assign the new appointment to the list.
    public String addAppt(String apptDate, String apptDesc) {
        // Create the new appointment
        Appointment appointment = new Appointment(apptDate, apptDesc);
        //Add newly created appointment to variable appointmentList
        appointmentList.add(appointment);
        //Connect to MongoDB taskscheduler under collection appointment
        MongoDatabase appointmentDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = appointmentDB.getCollection("appointment");

        try {
            //insert newly created appointment into MongoDB
            InsertOneResult addAppt = collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("apptDate", appointmentList.get(0).getApptDate())
                    .append("apptDesc", appointmentList.get(0).getApptDesc())
            );

        } catch (MongoException me) {
            JOptionPane.showMessageDialog(AppointmentService.this, "Unable to add due to error: " + me);
        }

        try
        {
            //pauses execution for 1 second to allow MongoDB to process incoming data properly
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(AppointmentService.this, ex);
        }

        // create Document to return field that equals description passed to newly created appointment
        Document document = new Document();

        document = (Document) collection.find(eq("apptDesc", apptDesc)).first();

        // return the Appointment ID from MongoDB to the original process
        return document.get("apptID").toString();
    }

    //Using Appointment ID, return an appointment object
    //If a matching Appoint ID is not found, return an appointment object with default values
    public ArrayList<String> getAppt(Integer apptID) {
        // Create new instance of array string appointmentResults
        ArrayList<String> appointmentResults = new ArrayList<String>();
        //Connect to MongoDB taskscheduler under collection appointment
        MongoDatabase apptDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = apptDB.getCollection("appointment");

        // create Document to return field that equals appointmnet ID passed by the source application
        Document document = (Document) collection.find(eq("apptID", apptID)).first();

        //If nothing returned from above query, set appointmentResults to null
        //Else, add each value from query results to array
        if (document == null) {
            appointmentResults = null;
        } else {
            appointmentResults.add(document.get("apptDate").toString());
            appointmentResults.add(document.get("apptDesc").toString());
        }

        //Return appointmentResults to source
        return appointmentResults;

    }

    //Delete appointment
    //Use the apptID to find the right appointment to delete from the list
    //If we get to the end of the list without finding a match for apptID report that to the console.
    //This method of searching for appointment ID is the same for all update methods below.
    public void deleteAppt(Integer apptID) {
        //Connect to MongoDB taskscheduler under collection appointment
        MongoDatabase apptDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = apptDB.getCollection("appointment");

        //Create Bson query to equal to appointment ID
        Bson deleteQuery = eq("apptID", apptID);

        //If nothing returned from above query, prompt user that no appointment was found
        //Else, run delete query deleteQuery
        if (deleteQuery == null) {
            JOptionPane.showMessageDialog(AppointmentService.this, "No Appointment Found Under Appointment ID " + apptID);
        } else {
            try {
                DeleteResult delete = collection.deleteOne(deleteQuery);
            } catch (MongoException me) {
                JOptionPane.showMessageDialog(AppointmentService.this, "Unable to delete due to error: " + me);
            }
        }
    }

    //Update the appointment
    //Displays not found message if after cycling through list unable to locate apptID requested
    public void updateAppt(Integer apptID, String apptDate, String apptDesc) {
        //Connect to MongoDB taskscheduler under collection appointment
        MongoDatabase apptDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = apptDB.getCollection("appointment");

        //Create new document updateAppointment where appointment ID is equal to passed value
        Document updateTask = new Document().append("apptID", apptID);

        //Create BSon query to update the passed values to the found record in the collection
        Bson updates = Updates.combine(
                Updates.set("apptDate", apptDate),
                Updates.set("apptDesc", apptDesc)
        );

        try {
            UpdateResult update = collection.updateOne(updateTask, updates);
        } catch (MongoException me) {
            JOptionPane.showMessageDialog(AppointmentService.this, "Unable to update due to error: " + me);
        }
    }
}
