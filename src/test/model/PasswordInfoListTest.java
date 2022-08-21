package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PasswordInfoListTest {
    PasswordInfoList passwordInfoList;

    @BeforeEach
    public void runBefore() {
        passwordInfoList = new PasswordInfoList();
    }

    @Test
    public void testPasswordInfo() {
        assertEquals("", passwordInfoList.getWebsiteName());
        assertEquals("", passwordInfoList.getLoginID());
        assertEquals("", passwordInfoList.getUsername());
        assertEquals("", passwordInfoList.getPassword());
        assertEquals(0, passwordInfoList.size());
        assertEquals(new ArrayList<PasswordInfo>(), passwordInfoList.getPasswordInfoList());
    }

    @Test
    public void testAddPasswordInfo() {
        assertEquals(0, passwordInfoList.size());
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setWebsiteName("example website");
        passwordInfo.setLoginID("example login id");
        passwordInfo.setUsername("example username");
        passwordInfo.setPassword("12345");
        passwordInfoList.addPasswordInfo(passwordInfo);
        assertEquals(1, passwordInfoList.size());
        assertEquals(passwordInfo, passwordInfoList.get(0));

        PasswordInfo passwordInfo2 = new PasswordInfo();
        passwordInfo2.setWebsiteName("Gradescope");
        passwordInfo2.setLoginID("example@gmail.com");
        passwordInfo2.setUsername("John Doe");
        passwordInfo2.setPassword("12fasdjKdfK");
        passwordInfoList.addPasswordInfo(passwordInfo2);
        assertEquals(2, passwordInfoList.size());
        assertEquals(passwordInfo2, passwordInfoList.get(1));
    }

    @Test
    public void testEdit() {
        assertEquals(0, passwordInfoList.size());
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setWebsiteName("example website");
        passwordInfo.setLoginID("example login id");
        passwordInfo.setUsername("example username");
        passwordInfo.setPassword("12345");
        passwordInfoList.addPasswordInfo(passwordInfo);

        PasswordInfo passwordInfo2 = new PasswordInfo();
        passwordInfo2.setWebsiteName("Gradescope");
        passwordInfo2.setLoginID("example@gmail.com");
        passwordInfo2.setUsername("John Doe");
        passwordInfo2.setPassword("12fasdjKdfK");
        passwordInfoList.addPasswordInfo(passwordInfo2);

        PasswordInfo passwordInfo3 = new PasswordInfo();
        passwordInfo3.setWebsiteName("Piazza");
        passwordInfo3.setLoginID("example@student.ubc.ca");
        passwordInfo3.setUsername("Student");
        passwordInfo3.setPassword("foijs93dKLKFDJ");
        passwordInfoList.addPasswordInfo(passwordInfo3);

        assertEquals(passwordInfo2, passwordInfoList.get(1));
        passwordInfoList.edit(1, passwordInfo);
        assertEquals(passwordInfo, passwordInfoList.get(1));

        assertEquals(passwordInfo, passwordInfoList.get(0));
        passwordInfoList.edit(0, passwordInfo3);
        assertEquals(passwordInfo3, passwordInfoList.get(0));
    }

    @Test
    public void testRemove() {
        assertEquals(0, passwordInfoList.size());
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setWebsiteName("example website");
        passwordInfo.setLoginID("example login id");
        passwordInfo.setUsername("example username");
        passwordInfo.setPassword("12345");
        passwordInfoList.addPasswordInfo(passwordInfo);

        PasswordInfo passwordInfo2 = new PasswordInfo();
        passwordInfo2.setWebsiteName("Gradescope");
        passwordInfo2.setLoginID("example@gmail.com");
        passwordInfo2.setUsername("John Doe");
        passwordInfo2.setPassword("12fasdjKdfK");
        passwordInfoList.addPasswordInfo(passwordInfo2);

        PasswordInfo passwordInfo3 = new PasswordInfo();
        passwordInfo3.setWebsiteName("Piazza");
        passwordInfo3.setLoginID("example@student.ubc.ca");
        passwordInfo3.setUsername("Student");
        passwordInfo3.setPassword("foijs93dKLKFDJ");
        passwordInfoList.addPasswordInfo(passwordInfo3);

        assertEquals(3, passwordInfoList.size());
        passwordInfoList.remove(2);
        assertEquals(2, passwordInfoList.size());
        assertEquals(passwordInfo, passwordInfoList.get(0));
        assertEquals(passwordInfo2, passwordInfoList.get(1));

        passwordInfoList.remove(0);
        assertEquals(1, passwordInfoList.size());
        assertEquals(passwordInfo2, passwordInfoList.get(0));
    }

    @Test
    public void testGetIndexOfPasswordInfo() {
        assertEquals(0, passwordInfoList.size());
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setWebsiteName("example website");
        passwordInfo.setLoginID("example login id");
        passwordInfo.setUsername("example username");
        passwordInfo.setPassword("12345");
        passwordInfoList.addPasswordInfo(passwordInfo);

        PasswordInfo passwordInfo2 = new PasswordInfo();
        passwordInfo2.setWebsiteName("Gradescope");
        passwordInfo2.setLoginID("example@gmail.com");
        passwordInfo2.setUsername("John Doe");
        passwordInfo2.setPassword("12fasdjKdfK");
        passwordInfoList.addPasswordInfo(passwordInfo2);

        PasswordInfo passwordInfo3 = new PasswordInfo();
        passwordInfo3.setWebsiteName("Piazza");
        passwordInfo3.setLoginID("example@student.ubc.ca");
        passwordInfo3.setUsername("Student");
        passwordInfo3.setPassword("foijs93dKLKFDJ");
        passwordInfoList.addPasswordInfo(passwordInfo3);

        assertEquals(2, passwordInfoList.getIndexOfPasswordInfo("Piazza"));
        assertEquals(1, passwordInfoList.getIndexOfPasswordInfo("Gradescope"));
    }

    @Test
    public void testGetIndexOfPasswordInfoIgnoreCase() {
        assertEquals(0, passwordInfoList.size());
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setWebsiteName("example website");
        passwordInfo.setLoginID("example login id");
        passwordInfo.setUsername("example username");
        passwordInfo.setPassword("12345");
        passwordInfoList.addPasswordInfo(passwordInfo);

        PasswordInfo passwordInfo2 = new PasswordInfo();
        passwordInfo2.setWebsiteName("Gradescope");
        passwordInfo2.setLoginID("example@gmail.com");
        passwordInfo2.setUsername("John Doe");
        passwordInfo2.setPassword("12fasdjKdfK");
        passwordInfoList.addPasswordInfo(passwordInfo2);

        PasswordInfo passwordInfo3 = new PasswordInfo();
        passwordInfo3.setWebsiteName("Piazza");
        passwordInfo3.setLoginID("example@student.ubc.ca");
        passwordInfo3.setUsername("Student");
        passwordInfo3.setPassword("foijs93dKLKFDJ");
        passwordInfoList.addPasswordInfo(passwordInfo3);

        assertEquals(0, passwordInfoList.getIndexOfPasswordInfo("Example Website"));
        assertEquals(2, passwordInfoList.getIndexOfPasswordInfo("piazza"));
    }

    @Test
    public void testGetIndexOfPasswordInfoReturnNegativeOne() {
        assertEquals(0, passwordInfoList.size());
        PasswordInfo passwordInfo = new PasswordInfo();
        passwordInfo.setWebsiteName("example website");
        passwordInfo.setLoginID("example login id");
        passwordInfo.setUsername("example username");
        passwordInfo.setPassword("12345");
        passwordInfoList.addPasswordInfo(passwordInfo);

        PasswordInfo passwordInfo2 = new PasswordInfo();
        passwordInfo2.setWebsiteName("Gradescope");
        passwordInfo2.setLoginID("example@gmail.com");
        passwordInfo2.setUsername("John Doe");
        passwordInfo2.setPassword("12fasdjKdfK");
        passwordInfoList.addPasswordInfo(passwordInfo2);

        PasswordInfo passwordInfo3 = new PasswordInfo();
        passwordInfo3.setWebsiteName("Piazza");
        passwordInfo3.setLoginID("example@student.ubc.ca");
        passwordInfo3.setUsername("Student");
        passwordInfo3.setPassword("foijs93dKLKFDJ");
        passwordInfoList.addPasswordInfo(passwordInfo3);

        assertEquals(-1, passwordInfoList.getIndexOfPasswordInfo("example web"));
        assertEquals(-1, passwordInfoList.getIndexOfPasswordInfo("grade scope"));
    }
}