//Author Name: Christopher Woodley 

//Date: 10/04/2021

//Course ID: CS-320-T1021

//Description: This is the appointment class. It creates and stores appointment information.
//See the Constructor for more info.

package project1;

import java.util.concurrent.atomic.AtomicLong;
import java.util.Date;


public class Appointment {
	
	private final String apptID;
	private Date apptDate;
	private String apptDesc;
	private static AtomicLong idGenerator = new AtomicLong();
	
	//CONSTRUCTOR
	/*
	 * The constructor takes appointment date and appoint description as parameters.
	 * The first thing it does is generates a new ID for the apptID field.
	 * 
	 * Appointment Date is checked for null condition and if prior to today's date. If either of
	 *  those conditions exists, fill in the field with current date as a placeholder.
	 *   
	 * Appointment Description is checked for null condition or blank fields. If
	 * either of those conditions exists, set it to "NULL". If it has more than 50 characters, truncate it
	 * so that only the first 50 characters are used. 
	 *    
	 */
	
	public Appointment(Date apptDate, String apptDesc) {
		
		//APPTID
		//Appointment ID is generated when the constructor is called.  It is set as a final variable and has
		//no other getter or setter so there should be no way to change it.
		//The idGenerator is static to prevent duplicates across all appointments.
		this.apptID = String.valueOf(idGenerator.getAndIncrement());
		
		//APPTDATE
				if (apptDate == null || apptDate.before(new Date())) {
			this.apptDate = new Date();
		}
		else {
			this.apptDate = apptDate;
		}
		
		//APPTDESC
		if (apptDesc == null || apptDesc.isBlank()) {
			this.apptDesc = "NULL";
		}
		else if (apptDesc.length() > 50) {
			this.apptDesc = apptDesc.substring(0,50);
		}
		else {
			this.apptDesc = apptDesc;
		}
	}
		
		//GETTERS
		public String getApptID() {
			return apptID;		
		}
		
		public Date getApptDate() {
			return apptDate;
		}
		
		public String getApptDesc() {
			return apptDesc;
		}
			
		//SETTERS
		/*
		 * The setters follow the same rules as the constructor
		 */
		public void setApptDate(Date apptDate) {
			//if apptDate is null or prior to today's date, will set to today's date
			if (apptDate == null || apptDate.before(new Date())) {
				this.apptDate = new Date();
			}
			else {
				this.apptDate = apptDate;
			}
		}
		
		public void setApptDesc(String apptDesc) {
			//if apptDesc is null or blank, set apptDesc to "NULL"
			if (apptDesc == null || apptDesc.isBlank()) {			
				this.apptDesc = "NULL";
			}
			//if apptDesc is longer than 50 characters, grab the first 50 characters
			else if(apptDesc.length() > 50) {
				this.apptDesc = apptDesc.substring(0,50);
			}
			else {
				this.apptDesc = apptDesc;
			}
		}
}

	

