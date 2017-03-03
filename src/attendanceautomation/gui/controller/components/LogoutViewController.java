/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components;

import attendanceautomation.gui.controller.RootViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Logs off and returns to the login page.
     * @param event
     */
    @FXML
    private void processLogout(ActionEvent event) {
        RootViewController rootViewController = RootViewController.getInstance();
        rootViewController.logout();
    }

}
