import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MonitorData {
    //set private fields
    private Scanner scnr;
    private FileInputStream fileByteStream;
    private Scanner inFS;    
    private String monitorData;
    private String menuPrompt;  
    private int retVal;
    
    //default constructor to assign fields default values
    public MonitorData() {
        Scanner scnr = null;
        FileInputStream fileByteStream = null;
        Scanner inFS = null;                     
        String monitorData = "";
        String menuPrompt = "";        
        int retVal = 0;
    }
    
    public void DisplayOption(String fileName) throws FileNotFoundException {
        //sets FileInputStream fileByteSteam to fileName called as argument to method
        fileByteStream = new FileInputStream(fileName);
        //sets inFS as Scanner of fileByteStream
        inFS = new Scanner(fileByteStream);
        //repetition loop to move through file until an empty line is encountered
        while(!(monitorData = inFS.nextLine()).isEmpty()) {                      
            //print output contents of file
            System.out.println(monitorData);                                
        }
        System.out.println();       
    }
    
    public int DisplayMonitor(String menuSelect, String fileName) throws FileNotFoundException {
        Scanner scnr = new Scanner(System.in);
        //repetition loop to move through file until finds a line containing the information found in argument to method
        retVal = 0;
        while(inFS.hasNextLine()) {
            monitorData = inFS.nextLine();
            if (monitorData.contains(menuSelect)) {
                System.out.println(monitorData);
                retVal = 1;
                while(!(monitorData = inFS.nextLine()).isEmpty()) {
                    if (!monitorData.contains("*****")) {
                        System.out.println(monitorData);
                    }
                    else {
                        monitorData = monitorData.substring(5);
                        JOptionPane.showMessageDialog(null, monitorData, "WARNING: OUT OF NORMAL RANGE", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            
        }   
        System.out.println();            
        return retVal;
    }
        
}        