package ui;

import model.PasswordInfo;
import model.PasswordInfoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Password Manager application
public class PasswordApp {
    private static final String JSON_STORE = "./data/passwordInfoList.json";
    private PasswordInfo passwordInfo;
    private String websiteName;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private PasswordInfoList passwordInfoList;

    // EFFECTS: Instantiates the super class constructor
    // assigns "" to websiteName,
    // and class runPasswordApp method
    public PasswordApp() throws FileNotFoundException {
        super();
        websiteName = "";
        passwordInfoList = new PasswordInfoList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPasswordApp();
    }

    // EFFECTS: Takes input and calls appropriate methods
    public void runPasswordApp() {
        Scanner input = new Scanner(System.in);
        while (true) {
            displayMenu();
            String command = input.next().toLowerCase();
            passwordInfo = new PasswordInfo();
            System.out.println();
            if (command.equals("q")) {
                System.out.println("Goodbye!");
                break;
            } else {
                processInput(command);
            }
        }
    }

    // EFFECTS: Calls appropriate function based on user input
    private void processInput(String command) {
        if (command.equals("a")) {
            addPasswordInfo();
        } else if (command.equals("r")) {
            retrievePasswordInfo();
        } else if (command.equals("v")) {
            viewListOfPasswordInfo();
        } else if (command.equals("e")) {
            editPasswordInfo();
        } else if (command.equals("d")) {
            removePasswordInfo();
        } else if (command.equals("s")) {
            savePasswordInfoList();
        } else if (command.equals("l")) {
            loadPasswordInfoList();
        } else {
            System.out.println("Invalid input, try again");
        }
    }

    // EFFECTS: Displays options for the user to select
    public void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add new password information");
        System.out.println("\tr -> Retrieve password information");
        System.out.println("\tv -> View all of your passwords");
        System.out.println("\te -> Edit password information");
        System.out.println("\td -> Delete password information");
        System.out.println("\ts -> Save password information to file");
        System.out.println("\tl -> Load password information from file");
        System.out.println("\tq -> Quit");
    }

    // MODIFIES: this
    // EFFECTS: Takes user input for website name, login id, username and password
    // and adds this password information to the array list of the super class
    public void addPasswordInfo() {
        System.out.println("Enter the website's name whose password information you want to add");
        websiteNameInput();
        this.passwordInfo.setWebsiteName(this.websiteName);
        takeInputForLoginUsernameAndPassword();
        passwordInfoList.addPasswordInfo(this.passwordInfo);
        System.out.println("\n Password was successfully added");
    }

    // MODIFIES: this
    // EFFECTS: Takes user input for the website name and displays the password information for that website
    // from the array list of the super class
    public void retrievePasswordInfo() {
        System.out.println("Enter the website's name whose password you want to retrieve");
        websiteNameInput();
        if (passwordInfoList.getIndexOfPasswordInfo(websiteName) == -1) {
            System.out.println("Sorry, no password information exists for the website");
        } else {
            retrieve(this.websiteName);
        }
    }

    // EFFECTS: Displays all the password information in the array list of the super class
    // Displays "You have no passwords, enter a to add 1" if there are is no password information in the array list
    public void viewListOfPasswordInfo() {
        if (passwordInfoList.size() == 0) {
            System.out.println("You have no passwords, enter a to add");
        }
        for (PasswordInfo passwordInfo : passwordInfoList.getPasswordInfoList()) {
            viewPasswordInfo(passwordInfo);
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: Takes user input for the website name and removes the password information for that website
    // from the array list of super class
    public void removePasswordInfo() {
        System.out.println("Enter the website's name whose password information you want to remove");
        websiteNameInput();
        if (passwordInfoList.getIndexOfPasswordInfo(this.websiteName) == -1) {
            System.out.println("Sorry, no password information exists for the website");
        } else {
            passwordInfoList.remove(passwordInfoList.getIndexOfPasswordInfo(this.websiteName));
        }
        System.out.println("\n Password information was successfully removed");
    }


    // MODIFIES: this
    // EFFECTS: Takes user input for the website name and then edits the password information for that website
    // in the array list
    public void editPasswordInfo() {
        System.out.println("Enter the website's name whose details you want to edit");
        websiteNameInput();
        if (passwordInfoList.getIndexOfPasswordInfo(websiteName) == -1) {
            System.out.println("Sorry, no password information exists for the website");
        } else {
            System.out.println("The following are the previous password details: \n");
            retrieve(this.websiteName);
            this.passwordInfo.setWebsiteName(this.websiteName);
            System.out.println();
            System.out.println("Enter the new details \n");
            takeInputForLoginUsernameAndPassword();
            passwordInfoList.edit(passwordInfoList.getIndexOfPasswordInfo(this.websiteName), this.passwordInfo);
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays the password information for a given website name
    public void retrieve(String websiteName) {
        viewPasswordInfo(passwordInfoList.get(passwordInfoList.getIndexOfPasswordInfo(websiteName)));
    }


    // EFFECTS: Displays the website name, login id, username and password for a given passwordInfo
    public void viewPasswordInfo(PasswordInfo passwordInfo) {
        System.out.println("Website name: " + passwordInfo.getWebsiteName());
        System.out.println("Login ID: " + passwordInfo.getLoginID());
        System.out.println("Username: " + passwordInfo.getUsername());
        System.out.println("Password: " + passwordInfo.getPassword());
    }

    // MODIFIES: this
    // EFFECTS: Takes user input for website name
    public void websiteNameInput() {
        Scanner websiteName = new Scanner(System.in);
        this.websiteName = websiteName.next();
        System.out.println();
    }

    // MODIFIES: this
    // EFFECTS: Takes user input for login id, username and password(computer generated or user's choice_
    public void takeInputForLoginUsernameAndPassword() {
        System.out.println("Enter login id");
        Scanner loginID = new Scanner(System.in);
        String loginIDString = loginID.next();
        System.out.println("Enter username (if there is no username enter ---)");
        Scanner username = new Scanner(System.in);
        String usernameString = username.next();
        System.out.println("Enter password");
        System.out.println("Or enter g to generate a password");
        Scanner password = new Scanner(System.in);
        String passwordString = password.next().toLowerCase();
        if (passwordString.equals("g")) {
            System.out.println("Enter the length of generated password");
            Scanner length = new Scanner(System.in);
            String lengthString = length.next();
            passwordInfoList.generatePassword(Integer.parseInt(lengthString));
            System.out.println("Generated password: " + passwordInfoList.getPassword());
            this.passwordInfo.setPassword(passwordInfoList.getPassword());
        } else {
            this.passwordInfo.setPassword(passwordString);
        }
        this.passwordInfo.setLoginID(loginIDString);
        this.passwordInfo.setUsername(usernameString);
    }

    // EFFECTS: saves the PasswordInfoList to file
    private void savePasswordInfoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(passwordInfoList);
            jsonWriter.close();
            System.out.println("Saved password information");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads PasswordInfolist from file
    private void loadPasswordInfoList() {
        try {
            this.passwordInfoList = jsonReader.read();
            System.out.println("Loaded password info list from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
