/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Student;
import attendanceautomation.dal.LoginDAO;
import java.util.ArrayList;

/**
 *
 * @author Rasmus
 */
public class LoginManager {

    private final LoginDAO loginDAO;

    public LoginManager() {
        loginDAO = new LoginDAO();
    }

    /**
     * Gets a list of students from LoginDAO and checks if the parsed user
     * exist.
     *
     * @param userName
     * @return
     */
    public boolean checkIfUserExist(String userName) {
        ArrayList<Student> mockListOfStudents = loginDAO.getMockStudents();
        for (Student student : mockListOfStudents) {
            if (student.getEmail().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the username matches the password.
     *
     * @param userName
     * @param password
     * @return
     */
    public boolean validateLoginAttempt(String userName, String password) {
        boolean succesfullLogin = false;
        ArrayList<Student> mockListOfStudents = loginDAO.getMockStudents();
        Student user = null;
        for (Student student : mockListOfStudents) {
            if (student.getEmail().equals(userName)) {
                user = student;
            }
        }

        //Validation for the mockUsers. For a real database, this should be redone.
        if (user.getFirstName().equals("Adam")) {
            if (password.equals(loginDAO.getMockStudentPassword())) {
                succesfullLogin = true;
            }
        } else if (user.getFirstName().equals("Rasmus")) {
            if (password.equals(loginDAO.getMockTeacherPassword())) {
                succesfullLogin = true;
            }
        }
        return succesfullLogin;
    }
}
