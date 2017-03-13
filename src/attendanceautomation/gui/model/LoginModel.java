/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.bll.LoginManager;

/**
 *
 * @author Mathias
 */
public class LoginModel {

    private static LoginModel instance;
    private final LoginManager loginManager;

    public static LoginModel getInstance() {
        if (instance == null) {
            instance = new LoginModel();
        }
        return instance;
    }

    private LoginModel() {
        loginManager = LoginManager.getInstance();
    }

    /**
     * Contacts the LoginManager by sending the and verifies the user.
     *
     * @param username
     * @return
     */
    public boolean verifyUserExists(String username) {
        boolean verification = false;
        if (loginManager.checkIfUserExist(username)) {
            verification = true;
        }
        return verification;
    }

    /**
     * Check if user is a teacher
     *
     * @param username
     * @return
     */
    public boolean isUserTeacher(String username) {
        return loginManager.checkIfUserIsTeacher(username);
    }

    public boolean verifyTeacherLogin(String password) {
        return loginManager.validateTeacherPassword(password);
    }

    public boolean verifyStudentLogin(String password) {
        return loginManager.validateStudentPassword(password);
    }
}
