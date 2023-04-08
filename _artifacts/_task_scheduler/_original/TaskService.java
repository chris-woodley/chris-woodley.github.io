//Author Name: Christopher Woodley

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the task service.  It maintains a list of tasks and has capabilities
//for adding and deleting tasks, as well as updating the name and description of existing tasks

package project1;

import java.util.ArrayList;

public class TaskService {
		
	ArrayList<Task> taskList = new ArrayList<Task>();
	
	//display the full listing of all tasks to console for error checking
	public void displayTaskList() {
		for(int counter = 0; counter < taskList.size(); counter++) {
			System.out.println("\t Task ID: " + taskList.get(counter).getTaskID());
			System.out.println("\t Name: " + taskList.get(counter).getName());
			System.out.println("\t Description: " + taskList.get(counter).getDescription() + "\n");
		}
	}
	
	//Add a new task using the Task constructor, then assing the new task to the list
	public void addTask(String name, String description) {
		Task task = new Task(name, description);
		taskList.add(task);
	}
	
	//Using Task ID, return a task object
	//If a matching Task ID is not found, return a taks obejct with default values
	public Task getTask(String taskID) {
		Task task = new Task(null,null);
		for(int counter = 0; counter < taskList.size(); counter++) {
			if(taskList.get(counter).getTaskID().contentEquals(taskID)) {
				task = taskList.get(counter);
			}
		}
		return task;
	}
		
	//Delete contact
	//Using the Task ID to find the right contact to delete from list
	//If no matching Task ID can be found in the list, will output it cannot be found.
	public void deleteTask(String taskID) {
		for(int counter = 0; counter < taskList.size(); counter++) {
			if(taskList.get(counter).getTaskID().equals(taskID)) {
				taskList.remove(counter);
				break;
			}
			if(counter == taskList.size()-1) {
				System.out.println("Task ID: " + taskID + " not found.");
			}
		}
	}
	
	//Update name	
	public void updateName(String updatedString, String taskID) {
		for(int counter = 0; counter < taskList.size(); counter++) {
			if(taskList.get(counter).getTaskID().equals(taskID)) {
				taskList.get(counter).setName(updatedString);
			}
		}
	}
	
	//Update description	
	public void updateDescription(String updatedString, String taskID) {
		for(int counter = 0; counter < taskList.size(); counter++) {
			if(taskList.get(counter).getTaskID().equals(taskID)) {
				taskList.get(counter).setDescription(updatedString);
			}
		}
	}
	

}
