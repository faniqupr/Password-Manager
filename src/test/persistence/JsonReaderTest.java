package persistence;

import model.PasswordInfoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PasswordInfoList passwordInfoList = reader.read();
            fail("IO Exception");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPasswordInfoList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPasswordInfoList.json");
        try {
            PasswordInfoList passwordInfoList = reader.read();
            assertEquals(0, passwordInfoList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPasswordInfoList.json");
        try {
            PasswordInfoList passwordInfoList = reader.read();
            assertEquals(2, passwordInfoList.size());
            checkPasswordList("google.com","firstname_lastname@gmail.com", "johndoe", "1234567890", passwordInfoList.get(0));
            checkPasswordList("duckduckgo.com", "myname@outlook.com", "---", "weak password",passwordInfoList.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
