/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import java.sql.SQLException;
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
    public void testCheckIfUserExistWithAValidUser() throws SQLException {
        String userName = "rasm035h@easv365.dk";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = true;
        boolean result = instance.checkIfUserExist(userName);
        assertEquals(expResult, result);
    }

    /**
     * Test if it can find a valid user.
     */
    @Test
    public void testCheckIfUserExistWithAnotherValidUser() throws SQLException {
        String userName = "adam3964@easv365.dk";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = true;
        boolean result = instance.checkIfUserExist(userName);
        assertEquals(expResult, result);
    }

    /**
     * Test if a random user can login.
     */
    @Test
    public void testCheckIfUserExistWithANonValidUser() throws SQLException {
        String userName = "rand6231@easv365.dk";
        LoginManager instance = LoginManager.getInstance();
        boolean expresult = false;
        boolean result = instance.checkIfUserExist(userName);
        assertEquals(expresult, result);
    }

    /**
     * Test if able to login with right username and password.
     */
    @Test
    public void testValidateUserIsTeacher() {
        String userName = "pgn@easv.dk";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = true;
        boolean result = instance.checkIfUserIsTeacher(userName);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with right username and password.
     */
    @Test
    public void testValidateUserIsNotTeacher() {
        String userName = "rasm035h@easv365.dk";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = false;
        boolean result = instance.checkIfUserIsTeacher(userName);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with wrong password, but password is in the
     * database.
     */
    @Test
    public void testValidateTeacherLoginAttemptWithValidPassword() {
        String password = "teacher";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = true;
        boolean result = instance.validateTeacherPassword(password);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with a random passsword that doesn't exist in the
     * database.
     */
    @Test
    public void testValidateTeacherLoginAttemptWithInValidPassword() {
        String password = "random";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = false;
        boolean result = instance.validateTeacherPassword(password);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with wrong password, but password is in the
     * database.
     */
    @Test
    public void testValidateStudentLoginAttemptWithValidPassword() {
        String password = "student";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = true;
        boolean result = instance.validateStudentPassword(password);
        assertEquals(expResult, result);
    }

    /**
     * Test if able to login with a random passsword that doesn't exist in the
     * database.
     */
    @Test
    public void testValidateStudentLoginAttemptWithInValidPassword() {
        String password = "random";
        LoginManager instance = LoginManager.getInstance();
        boolean expResult = false;
        boolean result = instance.validateStudentPassword(password);
        assertEquals(expResult, result);
    }
}
