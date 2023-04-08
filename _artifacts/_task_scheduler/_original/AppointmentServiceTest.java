//Author Name: Christopher Woodley 

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the unit tests for the appointment service (AppointmentServiceTest).

package project1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class AppointmentServiceTest {

	/*
	 * The following tests exercise the AppointmentService class.
	 * In each test, a new service is created with default values for each filed.
	 * Then the service is requested to make some change to the list of appointments and the result
	 * is tested to ensure the actual field matches what is expected.
	 * 
	 * Because each appointment that gets created has a new automatically assigned apptID,
	 * and the tests are run based on that apptID, the order in which the tests are run depend
	 * on the value of each apptID.  Therefore, the @Order annotation is used to keep the tests
	 * in a specific order.
	 * 
	 * For evidence that the apptID is correct for each test, uncomment the service.displayAppotList();
	 * prior to each assertion so that the records are displayed on the console for error checking.
	 */
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/DD/YYYY hh:mm:ss");
	Date apptDate;
	String aDate = "10/10/2021 10:00:00";
	
	@Test	
	@DisplayName("Test to ensure that servcie can add an appointment.")
	@Order(1)
	void testAddAppointment() throws ParseException {
		apptDate = dateFormat.parse(aDate);
		Appointment appointment = new Appointment(apptDate, "AppointmentDescription");
		AppointmentService service = new AppointmentService();
		service.addAppt(appointment);
		service.displayApptList();
		assertNotNull(service.getAppt("1"), "Appointment was not added correctly.");
	}
				
	@Test
	@DisplayName("Test to ensure that service correctly deleted appointments.")
	@Order(2)
	void testDeleteAppointment() throws ParseException {
		apptDate = dateFormat.parse(aDate);
		Appointment appointment = new Appointment(apptDate, "AppointmentDescription");
		AppointmentService service = new AppointmentService();
		service.addAppt(appointment);		
		service.deleteAppt("2");
		ArrayList<Appointment> apptListEmpty = new ArrayList<Appointment>();
		service.displayApptList();
		assertEquals(service.apptList, apptListEmpty, "The contact was not deleted.");
	}

}
