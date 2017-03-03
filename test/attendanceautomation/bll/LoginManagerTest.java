/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Rasmus
 */
public class LoginManagerTest {

    public LoginManagerTest() {
    }

    /**
     * Test if it can find a valid user.
     */
    @Test
    public void testCheckIfUserExistWithAValidUser() {
        String userName = "rasm035h@easv365.dk";
        LoginManager instance = new LoginManager();
        boolean expResult = true;
        boolean result = instance.checkIfUserExist(userName);
        assertEquals(expResult, result);
    }

    /**
     * Test if it can find a valid user.
     */
    @Test
    public void testCheckIfUserExistWithAnotherValidUser() {
        String userName = "adam3964@easv365.dk";
        LoginManager instance = new LoginManager();
        boolean expResult = true;
        boolean result = instance.checkIfUserExist(userName);
        assertEquals(expResult, result);
    }

    /**
     * Test if a random user can login.
     */
    @Test
    public void testCheckIfUserExistWithANonValidUser() {
        String userName = "rand6231@easv365.dk";
        LoginManager instance = new LoginManager();
        boolean expresult = false;
        boolean result = instance.checkIfUserExist(userName);
        assertEquals(expresult, result);
    }

    /**
     * Test if able to login with right username and password.
     */
    @Test
    public void testValidateLoginAttemptTeacherRightPaasword() {
        String userName = "rasm035h@easv365.dk";
        String password = "teacher";
        LoginManager instance = new LoginManager();
        boolean expResult = true;
        boolean result = instance.validateLoginAttempt(userName, password);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with right username and password.
     */
    @Test
    public void testValidateLoginAttemptStudentWithValidPassword() {
        String userName = "adam3964@easv365.dk";
        String password = "student";
        LoginManager instance = new LoginManager();
        boolean expResult = true;
        boolean result = instance.validateLoginAttempt(userName, password);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with wrong password, but password is in the
     * database.
     */
    @Test
    public void testValidateLoginAttemptStudentWithTeacherPassword() {
        String userName = "adam3964@easv365.dk";
        String password = "teacher";
        LoginManager instance = new LoginManager();
        boolean expResult = true;
        boolean result = instance.validateLoginAttempt(userName, password);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with a random passsword that doesn't exist in the
     * database.
     */
    @Test
    public void testValidateLoginAttemptStudentWithUnValidPassword() {
        String userName = "adam3964@easv365.dk";
        String password = "random";
        LoginManager instance = new LoginManager();
        boolean expResult = true;
        boolean result = instance.validateLoginAttempt(userName, password);
        assertEquals(expResult, result);
    }
}
