#include <iostream>
#include <limits>
using namespace std;

/*
* Data is hardcoded to application
* All data being hardcoded into applications makes it easier for data to be compromised due to incursion
* Upon update, this needs to be pulled for an outside database
*/
int num1 = 1, num2 = 2, num3 = 1, num4 = 1, num5 = 2;
string name1 = "Bob Jones", name2 = "Sarah Davis", name3 = "Amy Friendly", name4 = "Johnny Smith", name5 = "Carol Spears";

void ChangeCustomerChoice()
{
    int changeChoice = 0, newService = 0;

    /*
    * cout << "Enter the number of the client that you wish to change" << endl;
    * cin >> changeChoice;
    * Current operation only accepts input and doesn't check for what is being input
    * This can lead to buffer overflow and/or integer overflow/underflow as users can input any data they request
    * Adding fail check on input for buffer overflow plus also ensuring choices can only be of the 5 possible choices
    */
    do {
        cin.clear();
        cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        cout << "Enter the number of the client that you wish to change" << endl;
        cin >> changeChoice;
        if (cin.fail() || changeChoice >= 6) {
            cout << "Invalid entry" << endl;
        }
    } while (cin.fail() || changeChoice >= 6);

    /*
    * cout << "Please enter the clients' new service choice (1 = Brokerage, 2 = Retirement)" << endl;
    * cin >> newService;
    * As with above, Current operation only accepts input and doesn't check for what is being input
    * This can lead to buffer overflow and/or integer overflow/underflow as users can input any data they request
    * Adding fail check on input for buffer overflow plus also ensuring choices can only be of the 2 possible choices
    */

    do {
        cin.clear();
        cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        cout << "Please enter the clients' new service choice (1 = Brokerage, 2 = Retirement)" << endl;
        cin >> newService;
        if (cin.fail() || newService >= 3)
        {
            cout << "Invalid entry" << endl;
        }
    } while (cin.fail() || newService >= 3);

    if (changeChoice == 1)
    {
        num1 = newService;
    }
    else if (changeChoice == 2)
    {
        num2 = newService;
    }
    else if (changeChoice == 3)
    {
        num3 = newService;
    }
    else if (changeChoice == 4)
    {
        num4 = newService;
    }
    else if (changeChoice == 5)
    {
        num5 = newService;
    }
}

int CheckUserPermissionAccess()
{
    string userName, password;

    /*
    * Current application does not hold a value for username and had a hardcoded password
    * This is an unsafe practice as any reverse engineer could obtain the password for unrestricted access
    * Updates will include holding of usernames and passwords in separate database to be retrieved
    * Access will be set up based on permissions granted    *  
    */

    /*
    *  cout << "Enter your username:" << endl;
    *  cin >> userName;
    *  Due to possible buffer overflow if input is over the limit of variable userName, added validation for this check to ensure size cannot exceed capacity
    *  Will loop through until valid username is entered    * 
    */

    do {
        cin.clear();        
        cout << "Enter your username:" << endl;
        cin >> userName;        
        if (userName.size() >= userName.capacity()) {
            cout << "Invalid username" << endl;
        }
    } while (cin.fail() || userName.size() >= userName.capacity());

    /*
    *  cout << "Enter your password:" << endl;
    *  cin >> password;
    *  Due to possible buffer overflow if input is over the limit of variable password, added validation for this check to ensure size cannot exceed capacity
    *  Will loop through until valid password is entered    *
    */

    do {
        cout << "Enter your password:" << endl;
        cin >> password;
        if (password.size() >= password.capacity()) {
            cout << "Invalid password" << endl;
        }
    } while (cin.fail() || password.size() >= password.capacity());

    if (password == "123")
    {
        return 1;
    }
    else {
        return 2;
    }
}

void DisplayInfo()
{
    cout << "  Client's Name   Service Selected (1 = Brokerage, 2 = Retirement)" << endl;
    cout << "1. " << name1 << " selected option " << num1 << endl;
    cout << "2. " << name2 << " selected option " << num2 << endl;
    cout << "3. " << name3 << " selected option " << num3 << endl;
    cout << "4. " << name4 << " selected option " << num4 << endl;
    cout << "5. " << name5 << " selected option " << num5 << endl;
}

int main() {

    int choice = 0, answer = 2;

    cout << "Created by Christopher Woodley" << endl;
    cout << "Hello! Welcome to our Investment Company" << endl;
    while (answer != 1)
    {
        answer = CheckUserPermissionAccess();
        if (answer == 1)
        {
            break;
        }
        cout << "Invalid Password" << endl;
    }

    while (choice != 3)
    {
        /*
        *  cout << "What would you like to do?" << endl;
        *  cout << "DISPLAY the client list (enter 1)" << endl;
        *  cout << "CHANGE a client's choice (enter 2)" << endl;
        *  cout << "Exit the program.. (enter 3)" << endl;
        *  cin >> choice;
        *  With current application, there is a possibility of incorrect input leading to possible buffer overflow and/or input overflow/underflow
        *  Adding in check to ensure that input will does not cause buffer overflow and/or input overflow/underflow
        *  Additionally, ensuring that limits on accepted input are put to only applicable choices so as to ensure application will run as intended
        */

        do {
            cin.clear();
            cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            cout << "What would you like to do?" << endl;
            cout << "DISPLAY the client list (enter 1)" << endl;
            cout << "CHANGE a client's choice (enter 2)" << endl;
            cout << "Exit the program.. (enter 3)" << endl;
            cin >> choice;
            if (cin.fail() || choice >= 4) {
                cout << "Invalid selection" << endl;
            }
        } while (cin.fail() || choice >= 4);        

        cout << "You chose " << choice << endl;
        if (choice == 1)
        {
            DisplayInfo();
            choice = 0;
        }
        else if (choice == 2)
        {
            ChangeCustomerChoice();
            choice = 0;
        }        
    }
}