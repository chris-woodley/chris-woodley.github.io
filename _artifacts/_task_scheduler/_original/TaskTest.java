//Author Name: Christopher Woodley

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the unit tests for the task class (TaskTest).

package project1; 

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

	@Test
	@DisplayName("Task ID cannot have more than 10 characters")
	//test to validate Task ID cannot exceed the 10 character limit
	void testTaskIDWithMoreThanTenCharacters() {
		Task task = new Task("Name","Description");
		if(task.getTaskID().length() > 10) {
			fail("Task ID has more than 10 characters.");
		}
	}
	
	@Test
	@DisplayName("Task Name cannot have more than 20 characters")
	//test that passes Name with more then 20 characters to validate it will not pass
	void testTaskNameWithMOreThanTwentyCharacters() {
		Task task = new Task("WilliamHenryGoldmansmythe","Description");
		if(task.getName().length() > 20) {
			fail("Name has more than 20 characters.");
		}
	}
	
	@Test
	@DisplayName("Task Description cannot have more than 50 character")
	//test that passes Description with more than 50 characters to validate it will not pass
	void testTaskDescriptionWithMoreThanFiftyCharacters() {
		Task task = new Task("Name","ThisDescriptionHasMoreThanFiftyCharactersSoItShouldFail");
		if(task.getDescription().length() > 50) {
			fail("Description has more than 50 characters.");
		}
	}
	
	@Test
	@DisplayName("Task Name shall not be null")
	//test to pass a null data in Name to validate it will not pass
	void testTaskNameNotNull() {
		Task task = new Task(null, "Description");
		assertNotNull(task.getName(), "Task Name was null.");
	}
	
	@Test
	@DisplayName("Task Description shall not be null")
	//test to pass a null data in Description to validate it will not pass
	void testTaskDescriptionNotNull() {
		Task task = new Task("Name", null);
		assertNotNull(task.getDescription(), "Task Description was null.");
	}	
}
