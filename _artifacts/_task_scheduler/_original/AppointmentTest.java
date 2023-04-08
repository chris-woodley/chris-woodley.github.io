//Author Name: Christopher Woodley 

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the unit tests for the appointment class (AppointmentTest).

package project1;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AppointmentTest {
	/*
	 * The following tests exercise the Appointment class.
	 * The first 2 tests to make sure the field does not become longer than the constraint
	 * (10 characters for appointment ID and 50 for appointment description).
	 * The last 2 tests ensure that each field is not null.
	 * ApptID is not tested for being not null because there ins't a way to create
	 * an appointment with a null apptID.  Likewise it is not tested for being non-updateable
	 * because there is no way to update it.
	 * ApptDate is not tested for length constraint as all Gregorian Calendar fields have set limits
	 */
	
	private Date apptDate;
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/DD/YY hh:mm:ss");
	String currDate = "10/10/2021 10:00:00";
	String pastDate = "10/10/2020 10:00:00";
	
	@Test
	@DisplayName("Appointment ID cannot have more than 10 characters")
	void testApptIDWithMoreThanTenCharacters() throws ParseException {
		apptDate = dateFormat.parse(currDate);		
		Appointment appointment = new Appointment(apptDate, "AppointmentDescription");
		assertTrue(appointment.getApptID().length() <= 10);
	}
	
	
	@Test
	@DisplayName("Appointment Date cannot be in the past")
	void testApptDateInPast() throws ParseException {
		apptDate = dateFormat.parse(pastDate);
		Appointment appointment = new Appointment(apptDate, "AppointmentDescription");
		assertFalse(appointment.getApptDate().before(new Date()));		
	}
	
	@Test
	@DisplayName("Appointment Description cannot have more than 50 characters")
	void testApptDescWithMoreThanFiftyCharacters() throws ParseException {
		apptDate = dateFormat.parse(currDate);
		Appointment appointment = new Appointment(apptDate, "Appointmentisnotyetsetsopleasetryagainlatertoensureacceptance");
		assertTrue(appointment.getApptDesc().length() <= 50);		
	}
	
	@Test
	@DisplayName("Appointment Date cannot be null")
	void testApptDateNotNull() {
		apptDate = null;
		Appointment appointment = new Appointment(apptDate, "AppointmentDescription");
		assertNotNull(appointment.getApptDate(), "Appoinment Date was null.");
	}
	
	@Test
	@DisplayName("Appointment Description cannot be null")
	void testApptDescNotNull() throws ParseException {
		apptDate = dateFormat.parse(currDate);
		Appointment appointment = new Appointment(apptDate, null);
		assertNotNull(appointment.getApptDesc(), "Appointment Description was null.");
	}
}
