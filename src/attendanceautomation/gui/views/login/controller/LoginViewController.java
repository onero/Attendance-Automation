/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.login.controller;

import attendanceautomation.gui.model.LoginModel;
import attendanceautomation.gui.views.rootView.controller.RootViewController;
import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mathias
 */
public class LoginViewController implements Initializable {

    @FXML
    private TextField userId;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Label errorMessage;
    @FXML
    private JFXSpinner spinner;
    @FXML
    private Button btnLogin;

    private static LoginViewController instance;

    private final LoginModel loginModel;

    public static LoginViewController getInstance() {
        return instance;
    }

    public LoginViewController() {
        loginModel = LoginModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        errorMessage.setText("");
        userId.setPromptText("mrAwesome1234@easv365.dk");
        userPassword.setPromptText("your1337Password");
        spinner.setVisible(false);
    }

    /**
     * Sends a request to the LoginManager for a verification of the user and
     * logs it in, which will send it to the AllstudentsView or displays an
     * error message.
     *
     * @param event
     */
    @FXML
    private void processLogin() {
        spinner.setVisible(true);
        btnLogin.setDisable(true);
        String username = userId.getText();
        String password = userPassword.getText();
        checkUserExists(username, password);
    }

    /**
     * Check if user exists
     *
     * @param username
     * @param password
     */
    private void checkUserExists(String username, String password) {
        if (loginModel.verifyUserExists(username)) {
            RootViewController rootViewController = RootViewController.getInstance();
            checkForTeacherOrStudent(username, password, rootViewController);
        } else {
            denyAcccess();
        }
    }

    private void denyAcccess() {
        spinner.setVisible(false);
        btnLogin.setDisable(false);
        errorMessage.setText("Hello " + userId.getText() + " the password is wrong. \nPlease try agian.");
        //Clears the PasswordField for better usability
        userPassword.clear();
    }

    /**
     * Check if user is a teacher or a student
     *
     * @param username
     * @param password
     * @param rootViewController
     */
    private void checkForTeacherOrStudent(String username, String password, RootViewController rootViewController) {
        if (loginModel.isUserTeacher(username)) {
            verifyTeacher(password, rootViewController, username);
        } else {
            verifyStudent(password, rootViewController, username);
        }
    }

    public void resetLogin() {
        userId.setText("");
        userPassword.setText("");
        spinner.setVisible(false);
        btnLogin.setDisable(false);
    }

    private void verifyStudent(String password, RootViewController rootViewController, String username) {
        if (loginModel.verifyStudentLogin(password)) {
            rootViewController.handleStudentLogin(username);
        } else {
            denyAcccess();
        }
    }

    private void verifyTeacher(String password, RootViewController rootViewController, String username) {
        if (loginModel.verifyTeacherLogin(password)) {
            rootViewController.handleTeacherLogin(username);
        } else {
            denyAcccess();
        }
    }

}
