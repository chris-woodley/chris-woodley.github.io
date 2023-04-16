//Author Name: Christopher Woodley

//Date: 10/04/2021
//Updated: 03/14/2023

//Description: This is the contact class. It creates and stores contact information.
//See the Constructor for more info.

package com.mongodb.taskscheduler;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Contact {

    private Integer contactID;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;


    //CONSTRUCTOR
    /*
     * The constructor takes first name, last name, phone number, and address as parameters.
     *
     * The first thing it does is generate a new ID for the contactID field.
     * This is done by going through MongoDB and sorting by existing contactID until last value is found
     * It will then create a new value by incrementing the last value by one
     *
     * First name and last name are checked for null conditions or blank fields. If either of
     * those conditions exist, fill in the field with the phrase "NULL" so that something exists
     * to protect data integrity while making it clear it is a placeholder.
     * In both cases, if the first or last name is greater than 10 character, truncate it
     * so that only the first 10 characters are used.
     *
     * For the number field, if the phone number is not exactly 10 characters then fill it with
     * the placeholder '5555555555'.
     *
     * Address is like first and last names.  If it is blank or null, set it to "NULL".
     * If it is more than 30 characters, truncate to the first 30 characters.
     */

    public Contact(String firstName, String lastName, String phoneNumber, String address) {
        //CONTACTID
        //Contact ID is generated when the constructor is called. It is set as a final variable and has
        //no other getter or setter so there should be no way to change it.
        //The idGenerator is static to prevent duplicates across all contacts.

        //FIRSTNAME

        if (firstName == null || firstName.isBlank()) {
            this.firstName = "NULL";
        }
        else if(firstName.length() > 10) {
            this.firstName = firstName.substring(0, 10);
        }
        else {
            this.firstName = firstName;
        }

        //LASTNAME
        if (lastName == null || lastName.isBlank()) {
            this.lastName = "NULL";
        }
        else if(lastName.length() > 10) {
            this.lastName = lastName.substring(0, 10);
        }
        else {
            this.lastName = lastName;
        }

        //NUMBER
        if (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() != 10) {
            this.phoneNumber = "5555555555";
        }
        else {
            this.phoneNumber = phoneNumber;
        }

        //ADDRESS
        if (address == null || address.isBlank()){
            this.address = "NULL";
        }
        else if(address.length() > 30) {
            this.address = address.substring(0, 30);
        }
        else {
            this.address = address;
        }
    }

    //GETTERS

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    //SETTERS
    /*
     * The setters follow the same rules as the constructor.
     */

    public void setFirstName(String firstName) {
        //If first name is blank or null, set name to "NULL"
        if (firstName == null || firstName.isBlank()) {
            this.firstName = "NULL";
        }
        //If first name is longer than 10 characters, just grab the first 10 characters
        else if(firstName.length() > 10) {
            this.firstName = firstName.substring(0, 10);
        }
        else {
            this.firstName = firstName;
        }
    }

    public void setLastName(String lastName) {
        //If last name is blank or null, set name to "NULL"
        if (lastName == null || lastName.isBlank()) {
            this.lastName = "NULL";
        }
        //If last name is longer than 10 characters, just grab the first 10 characters
        else if(lastName.length() > 10) {
            this.lastName = lastName.substring(0, 10);
        }
        else {
            this.lastName = lastName;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        //if number is blank or null or not 10 characters exactly in length, set to "5555555555"
        if (phoneNumber == null || phoneNumber.isBlank() || phoneNumber.length() != 10) {
            this.phoneNumber = "5555555555";
        }
        else {
            this.phoneNumber = phoneNumber;
        }
    }

    public void setAddress(String address) {
        //if address is blank or null, set to "NULL"
        if (address == null || address.isBlank()) {
            this.address = "NULL";
        }
        //if address is longer than 30 characters, just grab the first 30 characters
        else if(address.length() > 30) {
            this.address = address.substring(0, 30);
        }
        else {
            this.address = address;
        }
    }
}
