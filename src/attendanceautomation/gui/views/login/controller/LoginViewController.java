/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.login.controller;

import attendanceautomation.gui.model.LoginModel;
import attendanceautomation.gui.views.rootView.controller.RootViewController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Mathias
 */
public class LoginViewController implements Initializable {

    @FXML
    private ProgressIndicator spinner;

    @FXML
    private TextField userId;
    @FXML
    private PasswordField userPassword;
    @FXML
    private Label errorMessage;
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
        userId.setPromptText("Username");
        userPassword.setPromptText("Password");
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
        String username = userId.getText();
        String password = userPassword.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            startLoginProcess(username, password);
        } else if (username.isEmpty()) {
            setErrorMessage("Please provide a username");
        } else if (password.isEmpty()) {
            setErrorMessage("Please provide a password");
        }
    }

    private void startLoginProcess(String username, String password) {
        setLoginMode(true);
        Runnable task = () -> {
            try {
                checkUserExists(username, password);
            } catch (SQLException ex) {
                LoginViewController.getInstance().setErrorMessage(ex.getMessage());
            }
        };
        new Thread(task).start();
    }

    private void setLoginMode(boolean visible) {
        spinner.setVisible(visible);
        btnLogin.setDisable(visible);
        errorMessage.setVisible(false);
    }

    /**
     * Check if user exists
     *
     * @param username
     * @param password
     */
    private void checkUserExists(String username, String password) throws SQLException {
        if (loginModel.verifyUserExists(username)) {
            RootViewController rootViewController = RootViewController.getInstance();
            checkForTeacherOrStudent(username, password, rootViewController);
        } else {
            denyAcccess(0);
        }
    }

    private void denyAcccess(int error) {
        Platform.runLater(() -> {
            setLoginMode(false);
            this.errorMessage.setVisible(true);
            //Clears the PasswordField for better usability
            userPassword.clear();
            switch (error) {
                case 0:
                    errorMessage.setText(userId.getText() + " does not exist");
                    break;
                default:
                    errorMessage.setText("Hello " + userId.getText() + " the password is wrong. \nPlease try again.");
            }
        });
    }

    public void setErrorMessage(String errorMessage) {
        Platform.runLater(() -> {
            setLoginMode(false);
            this.errorMessage.setVisible(true);
            this.errorMessage.setWrapText(true);
            this.errorMessage.setText(errorMessage);

        });
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
        userPassword.setText("");
        spinner.setVisible(false);
        btnLogin.setDisable(false);
        errorMessage.setVisible(false);
    }

    private void verifyStudent(String password, RootViewController rootViewController, String username) {
        if (loginModel.verifyStudentLogin(password)) {
            loginModel.setIsStudent(true);
            rootViewController.handleStudentLogin(username);
        } else {
            denyAcccess(1);
        }
    }

    private void verifyTeacher(String password, RootViewController rootViewController, String username) {
        if (loginModel.verifyTeacherLogin(password)) {
            loginModel.setIsStudent(false);
            rootViewController.handleTeacherLogin(username);
        } else {
            denyAcccess(1);
        }
    }

}
