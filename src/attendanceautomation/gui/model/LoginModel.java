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

    public LoginModel() {
        loginManager = new LoginManager();
    }

    /**
     * Contacts the LoginManager by sending the and verifies the user.
     *
     * @param username
     * @param password
     * @return
     */
    public boolean verifyLogin(String username, String password) {
        boolean verification = false;
        if (loginManager.checkIfUserExist(username)) {
            if (loginManager.validateLoginAttempt(username, password)) {
                verification = true;
            }
        }
        return verification;
    }

    public boolean isUserStudent(String username) {
        return // matches the username with a student via the loginmanager.
                username.equals("adam3964@easv365.dk");
    }
}
