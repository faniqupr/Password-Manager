package persistence;

import model.PasswordInfo;
import model.PasswordInfoList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// // Citation: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            PasswordInfoList passwordInfoList = new PasswordInfoList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            PasswordInfoList passwordInfoList = new PasswordInfoList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPasswordInfoList.json");
            writer.open();
            writer.write(passwordInfoList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPasswordInfoList.json");
            passwordInfoList = reader.read();
            assertEquals(0, passwordInfoList.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            PasswordInfoList passwordInfoList = new PasswordInfoList();
            PasswordInfo passwordInfo1 = new PasswordInfo();
            passwordInfo1.setWebsiteName("google.com");
            passwordInfo1.setLoginID("firstname_lastname@gmail.com");
            passwordInfo1.setUsername("johndoe");
            passwordInfo1.setPassword("1234567890");
            PasswordInfo passwordInfo2 = new PasswordInfo();
            passwordInfo2.setWebsiteName("duckduckgo.com");
            passwordInfo2.setLoginID("myname@outlook.com");
            passwordInfo2.setUsername("---");
            passwordInfo2.setPassword("weak password");
            passwordInfoList.addPasswordInfo(passwordInfo1);
            passwordInfoList.addPasswordInfo(passwordInfo2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPasswordInfoList.json");
            writer.open();
            writer.write(passwordInfoList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPasswordInfoList.json");
            passwordInfoList = reader.read();
            assertEquals(2, passwordInfoList.size());
            checkPasswordList("google.com","firstname_lastname@gmail.com", "johndoe", "1234567890", passwordInfoList.get(0));
            checkPasswordList("duckduckgo.com", "myname@outlook.com", "---", "weak password",passwordInfoList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
