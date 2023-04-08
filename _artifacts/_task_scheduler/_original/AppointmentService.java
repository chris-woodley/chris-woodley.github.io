//Author Name: Christopher Woodley 

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the appointment service. It maintains a list of appointments and has capabilities
//for adding and deleting appointments.

package project1;

import java.util.ArrayList;

public class AppointmentService {
	//Start with an ArrayList of appointments to hold the list of appointments
	ArrayList<Appointment> apptList = new ArrayList<Appointment>();
	
	//Display the full list of appointments to the console for error checking.
	public void displayApptList() {
		for(int counter = 0; counter < apptList.size(); counter++) {
			System.out.println("\t Appointment ID: " + apptList.get(counter).getApptID());
			System.out.println("\t Appointment Date: " + apptList.get(counter).getApptDate());
			System.out.println("\t Appointment Description: " + apptList.get(counter).getApptDesc());
		}
	}
	
	//Adds a new appointment using the Appointment constructor, then assign the new appointment to the list.
	public void addAppt(Appointment appointment) {		
		apptList.add(appointment);
	}
	
	//Using Appointment ID, return an appointment object
	//If a matching Appoint ID is not found, return an appointment object with default values
	public Appointment getAppt(String apptID) {
		Appointment appointment = new Appointment(null,null);
		for(int counter=0; counter < apptList.size(); counter++) {
			if(apptList.get(counter).getApptID().contentEquals(apptID)) {
				appointment = apptList.get(counter);
			}
		}
		return appointment;
	}
	
	//Delete appointment
	//Use the apptID to find the right appointment to delete from the list
	//If we get to the end of the list without finding a match for apptID report that to the console.
	public void deleteAppt(String apptID) {
		for(int counter=0; counter < apptList.size(); counter++) {
			if(apptList.get(counter).getApptID().equals(apptID)) {
				apptList.remove(counter);
				break;			
			}
			if (counter == apptList.size()-1) {
				System.out.println("Appointment ID: " + apptID + " not found.");
			}
		}
	}

}
