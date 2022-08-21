package persistence;

import model.EventLog;
import model.PasswordInfo;
import model.PasswordInfoList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads PasswordInfoList from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads PasswordInfoList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PasswordInfoList read() throws IOException {
        String jsonData = readFile(this.source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePasswordInfoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses PasswordInfoList from JSON object and returns it
    private PasswordInfoList parsePasswordInfoList(JSONObject jsonObject) {
        PasswordInfoList passwordInfoList = new PasswordInfoList();
        addPasswordInfoList(passwordInfoList, jsonObject);
        return passwordInfoList;
    }

    // MODIFIES: passwordInfoList
    // EFFECTS: parses PasswordInfoList from JSON object and adds it to PasswordInfoList
    private void addPasswordInfoList(PasswordInfoList passwordInfoList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Password Info List");
        for (Object json : jsonArray) {
            JSONObject nextPasswordInfo = (JSONObject) json;
            addPasswordInfo(passwordInfoList, nextPasswordInfo);
        }
    }

    // MODIFIES: passwordInfoList
    // EFFECTS: parses passwordInfo from JSON Object and adds it to PasswordInfoList
    private void addPasswordInfo(PasswordInfoList passwordInfoList, JSONObject jsonObject) {
        PasswordInfo passwordInfo = new PasswordInfo();
        String websiteName = jsonObject.getString("website name");
        String loginID = jsonObject.getString("login ID");
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        passwordInfo.setWebsiteName(websiteName);
        passwordInfo.setLoginID(loginID);
        passwordInfo.setUsername(username);
        passwordInfo.setPassword(password);
        passwordInfoList.addPasswordInfo(passwordInfo);
    }
}
