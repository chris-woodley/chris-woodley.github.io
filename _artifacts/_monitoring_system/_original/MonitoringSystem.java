import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;


public class MonitoringSystem {

	public static void main(String[] args) throws FileNotFoundException, IOException {
                
		Scanner scnr = new Scanner(System.in);
                FileInputStream fileByteStream = null;
                Scanner inFS = null;                               
		String menuSelect = "";                
                String inputFile = "";
		boolean inputDone = false;	
                MonitorData monitor = new MonitorData();
                int returnVal = 0;
		
		// repetition loop to continue until inputDone is set to true value by inputting exit
		while (!inputDone) {
			// prompt for user input
			System.out.println("Monitor an animal, habitat, or exit: ");
			menuSelect = scnr.nextLine();                    
                    switch (menuSelect) {
                        case "animal":
                            //set returnVal to 0
                            returnVal = 0;
                            //repetition loop to continue until returnVal no longer equals 0
                            while (returnVal == 0) {
                                //set inputFile to animals.txt file
                                inputFile = "\\\\apporto.com\\dfs\\Users\\christopher.w1_snhu\\Documents\\NetBeansProjects\\MonitoringSystem\\src\\animals.txt";
                                //Call out to Method DisplayOption in Class MonitorData with inputFile as argument
                                monitor.DisplayOption(inputFile); 
                                //Request user to input animal to monitor
                                System.out.println("Select an animal to monitor(enter in Proper Case): ");
                                menuSelect = scnr.nextLine();                                    
                                //Set returnVal to the result of method DisplayMonitor in Class MonitorData with user input and inputFile as arguments                                
                                returnVal = monitor.DisplayMonitor(menuSelect, inputFile); 
                                //verify if returnVal is still equal to 0
                                if (returnVal == 0) {
                                    //if yes, then display invalid selection and repetition loop will continue
                                    System.out.println("Invalid selection, please select again.");
                                }                            
                            }
                            break;                    
                        case "habitat":
                            //set returnVal to 0
                            returnVal = 0;
                            //repetition loop to continue until returnVal no longer equals 0
                            while (returnVal ==0) {
                                //set inputFile to habitats.txt file                            
                                inputFile = "\\\\apporto.com\\dfs\\Users\\christopher.w1_snhu\\Documents\\NetBeansProjects\\MonitoringSystem\\src\\habitats.txt";
                                //Call out to Method DisplayOption in Class MonitorData with habitats.txt file as argument
                                monitor.DisplayOption(inputFile);
                                //Request user to input habitat to monitor
                                System.out.println("Select habitat to monitor(enter in Proper Case): ");
                                menuSelect = scnr.nextLine();
                                //Set returnVal to the result of method DisplayMonitor in Class MonitorData with user input and inputFile as arguments
                                returnVal = monitor.DisplayMonitor(menuSelect, inputFile);
                                 //verify if returnVal is still equal to 0
                                if (returnVal == 0) {
                                    //if yes, then display invalid selection and repetition loop will continue
                                    System.out.println("Invalid selection, please select again.");
                                }
                            }
                            break;
                        case "exit":
                            System.out.println("You selected exit. Good bye");                            
                            // set inputDone to true if one of the 3 valid entries selected
                            inputDone = true;
                            break;
                        default:
                            // show error message if invalid entry entered
                            System.out.println("Invalid input.  Please select animal, habitat, or exit.");
                            break;
                    }
		}		
        }
}