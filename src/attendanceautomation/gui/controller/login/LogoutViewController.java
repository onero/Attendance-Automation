/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.login;

import attendanceautomation.gui.controller.RootViewController;
import attendanceautomation.gui.controller.components.WhiteComponentHolderController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;

/**
 * FXML Controller class
 *
 * @author Skovgaard
 */
public class LogoutViewController implements Initializable {

    @FXML
    private Hyperlink logout;

    /**
     * Initializes the controller class.
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
