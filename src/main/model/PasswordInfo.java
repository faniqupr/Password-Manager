package model;

import org.json.JSONObject;
import persistence.Writable;

// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents password information having a password, website name, login id and username
public class PasswordInfo implements Writable {
    protected String websiteName;
    protected String loginID;
    protected String username;
    protected String password;

    // EFFECTS: Instantiates PasswordInfo with default values
    public PasswordInfo() {
        this.websiteName = "";
        this.loginID = "";
        this.username = "";
        this.password = "";
    }

    // REQUIRES: n > 0
    // MODIFIES: this
    // EFFECTS: Generates a random password of length n and stores it to the password field
    public void generatePassword(int n) {
        this.password = "";
        for (int i = 0; i < n; i++) {
            int random = (int) (Math.random() * 3);
            if (random == 0) {
                this.password += String.valueOf((int) (Math.random() * 10));
            } else if (random == 1) {
                // Citation: https://stackoverflow.com/questions/20634031/generate-random-lower-case-letter-error
                this.password += (char) ('a' + Math.random() * ('z' - 'a' + 1));
            } else {
                this.password += (char) ('A' + Math.random() * ('Z' - 'A' + 1));
            }
        }
        EventLog.getInstance().logEvent(new Event("Password of length " + n + " generated"));
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getWebsiteName() {
        return this.websiteName;
    }

    public String getLoginID() {
        return this.loginID;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("website name", this.websiteName);
        json.put("login ID", this.loginID);
        json.put("username", this.username);
        json.put("password", this.password);
        return json;
    }
}
