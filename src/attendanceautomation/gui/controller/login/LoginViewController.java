/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.login;

import attendanceautomation.gui.controller.RootViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Label errorMessage;
    
//    private RootViewController rootViewController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorMessage.setText("");
        userId.setPromptText("Brugernavn");
        password.setPromptText("Password");
    }    

    /**
     * Sends a request to the LoginManager for a verification of the user and logs it in, which will 
     * send it to the AllstudentsView or displays an error message.
     * @param event 
     */
    @FXML
    private void processLogin(ActionEvent event) {
        if (//TODO: Verification from Manager.
                true) {
            RootViewController rootViewController = RootViewController.getInstance();
            rootViewController.handleStartView(event);
            //Temporarily solved.
            rootViewController.setDisabledButtonsOff();
        } else {
            errorMessage.setText("Hello " + userId.getText() + " the password is wrong. \nPlease try agian.");   
        }
    }

    /**
     * Sets the RootViewController.
     * @param rootViewController 
     */
//    public void setRootViewController(RootViewController rootViewController) {
//        this.rootViewController = rootViewController;
//    }
}