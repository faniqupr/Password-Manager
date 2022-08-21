package persistence;

import model.PasswordInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
// Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkPasswordList(String websiteName, String loginID, String username, String password, PasswordInfo passwordInfo) {
        assertEquals(websiteName, passwordInfo.getWebsiteName());
        assertEquals(loginID, passwordInfo.getLoginID());
        assertEquals(username, passwordInfo.getUsername());
        assertEquals(password, passwordInfo.getPassword());
    }
}
