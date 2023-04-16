//Author Name: Christopher Woodley

//Date: 10/04/2021
//Updated: 03/14/2023

//Description: This is the contact service. It maintains a list of contacts and has capabilities
//for adding and deleting contacts, as well as updating first name, last name, phone number, and address.
//Functions reach out to MongoDB collection contact under taskScheduler database

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

public class ContactService extends Component {

    //Create an ArrayList of Contact to hold the list of contacts
    ArrayList<Contact> contactList = new ArrayList<Contact>();

    //Create an ArrayList of Strings to hold results of queries from MongoDB
    ArrayList<String> contactResults = new ArrayList<String>();


    //Adds a new contact using the Contact constructor, then assign the new contact to the list.
    public Integer addContact(String firstName, String lastName, String phoneNumber, String address) {
        //Create the new contact
        Contact contact = new Contact(firstName, lastName, phoneNumber, address);
        //Add newly created contact to variable contactList
        contactList.add(contact);
        //Connect to MongoDB taskscheduler under collection contact
        MongoDatabase contactDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = contactDB.getCollection("contact");

            try {
                //insert newly created contact into MongoDB
                InsertOneResult addContact = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("firstName", contactList.get(0).getFirstName())
                        .append("lastName", contactList.get(0).getLastName())
                        .append("address", contactList.get(0).getAddress())
                        .append("phoneNumber", contactList.get(0).getPhoneNumber())
                );

            } catch (MongoException me) {
               // JOptionPane.showMessageDialog(ContactService.this, "Unable to add due to error: " + me);
            }
        try {
            //pauses execution for 1 second to allow MongoDB to process incoming data properly
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(ContactService.this, ex);
        }

        // create Document to return field that equals address passed to newly created contact
        Document document = (Document) collection.find(eq("address", address)).first();

        // return the ContactID from MongoDB to the original process
        return Integer.valueOf(document.get("contactID").toString());
    }

    //Using Contact ID, return a contact object
    //If a matching Contact ID is not found, return a contact object with default values
    public ArrayList<String> getContact(Integer contactID) {
        // Create new instance of array string contactResults
        ArrayList<String> contactResults = new ArrayList<String>();
        //Connect to MongoDB taskscheduler under collection contact
        MongoDatabase contactDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = contactDB.getCollection("contact");

        // create Document to return field that equals address passed to newly created contact
        Document document = (Document) collection.find(eq("contactID", contactID)).first();

        //If nothing returned from above query, set contactResults to null
        //Else, add each value from query results to array
        if (document == null) {
            contactResults = null;
        } else {
            contactResults.add(document.get("firstName").toString());
            contactResults.add(document.get("lastName").toString());
            contactResults.add(document.get("phoneNumber").toString());
            contactResults.add(document.get("address").toString());
        }

        //Return contactResults to source
        return contactResults;
    }

    //Delete contact.
    //Use the contactID to find the right contact to delete from the list
    //If we get to the end of the list without finding a match for contactID report that to the console.
    //This method of searching for contactID is the same for all update methods below.

    public void deleteContact(Integer contactID) {
        //Connect to MongoDB taskscheduler under collection contact
        MongoDatabase contactDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = contactDB.getCollection("contact");

        //Create Bson query to equal to contact ID
        Bson deleteQuery = eq("contactID", contactID);

        //If nothing returned from above query, prompt user that no contact was found
        //Else, run delete query deleteQuery
        if (deleteQuery == null) {
            JOptionPane.showMessageDialog(ContactService.this, "No Contact Found Under Contact ID " + contactID);
        } else {
            try {
                DeleteResult delete = collection.deleteOne(deleteQuery);
            } catch (MongoException me) {
                System.err.println("Unable to delete due to error: " + me);
            }
        }
    }

    //Update contact
    //Use the contactID to find the right contact to update data from the database
    //
    public void updateContact(Integer contactID, String firstName, String lastName, String address, String phoneNumber) {
        //Connect to MongoDB taskscheduler under collection contact
        MongoDatabase contactDB = Connection.getClient().getDatabase("taskScheduler");
        MongoCollection collection = contactDB.getCollection("contact");

        //Create new document updateContact where contactID is equal to passed value
        Document updateContact = new Document().append("contactID", contactID);

        //Create BSon query to update the passed values to the found record in the collection
        Bson updates = Updates.combine(
                Updates.set("firstName", firstName),
                Updates.set("lastName", lastName),
                Updates.set("address", address),
                Updates.set("phoneNumber", phoneNumber)
        );

        try {
            //Run query to update fields
            UpdateResult update = collection.updateOne(updateContact, updates);
        } catch (MongoException me) {
            System.err.println("Unable to update due to error: " + me);
        }
    }
}
