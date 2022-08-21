package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordInfoTest {
    PasswordInfo passwordInfo;

    @BeforeEach
    public void runBefore() {
        passwordInfo = new PasswordInfo();
    }

    @Test
    public void testPasswordInfo() {
        assertEquals("", passwordInfo.getWebsiteName());
        assertEquals("", passwordInfo.getLoginID());
        assertEquals("", passwordInfo.getUsername());
        assertEquals("", passwordInfo.getPassword());
    }

    @Test
    public void testGeneratePassword() {
        passwordInfo.generatePassword(10);
        assertEquals(10, passwordInfo.getPassword().length());

        passwordInfo.generatePassword(20);
        assertEquals(20, passwordInfo.getPassword().length());
    }
}
