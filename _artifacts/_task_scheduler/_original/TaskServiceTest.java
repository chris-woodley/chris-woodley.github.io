//Author Name: Christopher Woodley

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the unit tests for the task service (TaskServiceTest)

package project1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

@TestMethodOrder(OrderAnnotation.class)
public class TaskServiceTest {

	@Test
	@DisplayName("Test to Update Task Name.")
	@Order(1)
	//test to create a record and then update the task name field and validate the results
	void testUpdateTaskName() {
		TaskService service = new TaskService();
		service.addTask("UpdateName", "Task to Update Name");
		service.updateName("NewTaskName", "0");
		service.displayTaskList();
		assertEquals("NewTaskName",service.getTask("0").getName(), "Task Name was not updated.");		
	}
	
	@Test
	@DisplayName("Test to Update Task Description")
	@Order(2)
	//test to create a record and then update the task description field and validate the results
	void testUpdateTaskDescription() {
		TaskService service = new TaskService();
		service.addTask("UpdateDescription", "Task to Update Description");
		service.updateDescription("NewDescriptionData", "2");
		service.displayTaskList();
		assertEquals("NewDescriptionData",service.getTask("2").getDescription(), "Task Description was not updated.");		
	}
	
	@Test
	@DisplayName("Test to ensure that service correctly deletes tasks.")
	@Order(3)
	//test to create a record and then delete the newly created record and validate that it is no longer in array
	void testDeleteTask() {
		TaskService service = new TaskService();
		service.addTask("TaskName", "Task Description");
		service.deleteTask("4");
		ArrayList<Task> taskListEmpty = new ArrayList<Task>();
		service.displayTaskList();
		assertEquals(service.taskList, taskListEmpty, "The task was not deleted.");
	}
	
	@Test
	@DisplayName("Test to ensure that service can add a contact.")
	@Order(4)
	//test to create a new record and validate that the record exists
	void testAddTask() {
		TaskService service = new TaskService();
		service.addTask("TaskName", "Task Description");
		service.displayTaskList();
		assertNotNull(service.getTask("6"), "Task was not added correctly.");
	}
}
