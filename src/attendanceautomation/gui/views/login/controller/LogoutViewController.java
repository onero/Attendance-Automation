/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.login.controller;

import attendanceautomation.gui.views.rootView.controller.RootViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;

/**
 * FXML Controller class
 *
 * @author Skovgaard
 */
public class LogoutViewController implements Initializable {

    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Logs off and returns to the login page.
     *
     * @param event
     */
    @FXML
    private void processLogout(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING");
        alert.setHeaderText(" Press 'Ok' to logout. \n Press 'Cancel' to stay logged in.");
        ButtonType yesButton = new ButtonType("Ok", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, cancelButton);
        alert.showAndWait().ifPresent(type -> {

            if (type == yesButton) {
                RootViewController rootViewController = RootViewController.getInstance();
                rootViewController.logout();

            }
        });

               

        
        
        

    }

}
