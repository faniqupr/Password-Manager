package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a list of all user's PasswordInfo
public class PasswordInfoList extends PasswordInfo implements Writable {

    protected ArrayList<PasswordInfo> passwordInfoList;

    // EFFECTS: Instantiates passwordInfoList as an empty array list and calls the constructor of the super class
    public PasswordInfoList() {
        super();
        passwordInfoList = new ArrayList<>();
    }

    // REQUIRES: passwordInfo is not a null object
    // MODIFIES: this
    // Adds the given passwordInfo into the array list passwordInfoList
    public void addPasswordInfo(PasswordInfo passwordInfo) {
        passwordInfoList.add(passwordInfo);
        EventLog.getInstance().logEvent(new Event("New password added for " + passwordInfo.getWebsiteName()));
    }

    // REQUIRES: indexOfPasswordInfo >=0 and < passwordInfoList.size() and
    // passwordInfo is not a null object
    // MODIFIES: this
    // EFFECTS: Replaces the element at indexOfPasswordInfo index of passwordInfoList with passwordInfo
    public void edit(int indexOfPasswordInfo, PasswordInfo passwordInfo) {
        passwordInfoList.set(indexOfPasswordInfo, passwordInfo);
        EventLog.getInstance().logEvent(new Event("Password edited for " + passwordInfo.getWebsiteName()));
    }


    // REQUIRES: indexOfPasswordInfo >= 0 and < passwordInfoList.size()
    // MODIFIES: this
    // EFFECTS: removes the element at indexOfPasswordInfo index of passwordInfoLIst
    public void remove(int indexOfPasswordInfo) {
        EventLog.getInstance().logEvent(new Event("Password deleted for "
                + passwordInfoList.get(indexOfPasswordInfo).getWebsiteName()));
        passwordInfoList.remove(indexOfPasswordInfo);
    }

    // REQUIRES:
    // EFFECTS: Returns the index of the element that stores the password information for the given website
    // returns -1 if no such element exists
    public int getIndexOfPasswordInfo(String website) {
        for (int i = 0; i < passwordInfoList.size(); i++) {
            if (passwordInfoList.get(i).getWebsiteName().equalsIgnoreCase(website)) {
                return i;
            }
        }
        return -1;
    }

    // EFFECTS: Returns the size of the passwordInfoList
    public int size() {
        return passwordInfoList.size();
    }

    // EFFECTS: Returns the element at given index of passwordInfoLIst
    public PasswordInfo get(int index) {
        return passwordInfoList.get(index);
    }

    // EFFECTS: Returns the list of password info
    public ArrayList<PasswordInfo> getPasswordInfoList() {
        return passwordInfoList;
    }


    @Override
    public JSONObject toJson() {
        JSONObject  json = new JSONObject();
        json.put("Password Info List", passwordInfoListToJson());
        return json;
    }

    // EFFECTS: returns passwordInfo in the passwordInfoList as a JSON Array
    private JSONArray passwordInfoListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (PasswordInfo passwordInfo : this.passwordInfoList) {
            jsonArray.put(passwordInfo.toJson());
        }
        return jsonArray;
    }

}
