//Author Name: Christopher Woodley

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the task class. It creates and stores task information
//See the Constructor for more info

package project1;

import java.util.concurrent.atomic.AtomicLong;

public class Task {
	
	private final String taskID;
	private String name;
	private String description;
	private static AtomicLong idGenerator = new AtomicLong();
	
	//CONSTRUCTOR
	/*
	 * The constructor takes name and description as parameters.
	 * The first thing it does is generates a new ID for the taskID field.
	 * 
	 * Name is checked for null condition or blank fields. If either of 
	 * those conditions exists, fill in the field with the phrase "NULL" so that something exists
	 * to protect data integrity while making it clear it is a placeholder.
	 * If name is greater than 20 characters, truncate it 
	 * so that only the first 20 characters are used.
	 * 
	 *  Description is like Name field.  However it will take first 30 characters
	 *  if the field exceeds 30 characters.
	 */
	public Task(String name, String description) {
		//taskID
		//Task ID is generated when the constructor is called.  It is set as a final variable and has
		//no other getter or setter to ensure field is not able to be updated.
		//The idGenerator is static to prevent duplicates across all tasks.		
		this.taskID = String.valueOf(idGenerator.getAndIncrement());
	
		//name
		//if name is null or blank, set to "NULL" as placeholder
		if (name == null || name.isBlank()) {
			this.name = "NULL";
		}
		//if name exceeds 20 characters, truncate field to first 20 characters
		else if(name.length() > 20) {
			this.name = name.substring(0, 20);
		}
		//if no other issue, set name
		else {
			this.name = name;
		}
		
		//description
		//if description is null or blank, set to "NULL" as placeholder
		if (description == null || description.isBlank()) {
			this.description = "NULL";
		}
		//if description exceeds 50 characters, truncate field to first 50 characters
		else if(description.length() > 50) {
			this.description = description.substring(0, 50);
		}
		//if no other issue, set description
		else {
			this.description = description;
		}
	}
	
	//GETTERS
	public String getTaskID() {
		return taskID;		
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	//SETTERS
	
	public void setName(String name) {
		//if name is null or blank, sets to "NULL"
		if (name == null || name.isBlank()) {
			this.name = "NULL";
		}
		//if name is longer than 20 characters,  just grab the first 20 characters
		else if(name.length() > 20) {
			this.name = name.substring(0, 20);
		}
		else {
			this.name = name;
		}
	}
	
	public void setDescription(String description) {
		//if description is null or blank, sets to "NULL"
		if (description == null || description.isBlank()) {
			this.description = "NULL";
		}
		//if description is longer than 20 characters,  just grab the first 20 characters
		else if(description.length() > 20) {
			this.description = description.substring(0, 20);
		}
		else {
			this.description = description;
		}
	}
}
