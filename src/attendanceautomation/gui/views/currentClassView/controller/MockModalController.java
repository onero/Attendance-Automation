/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.currentClassView.controller;

import attendanceautomation.gui.model.SchoolClassModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class MockModalController implements Initializable {

    @FXML
    private AnchorPane pane;

    private Stage stage;

    private final SchoolClassModel model;

    public MockModalController() {
        model = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleBefore(ActionEvent event) {
        model.updateCurrentClassStudents(1);
        stage.close();
    }

    @FXML
    private void handleAt(ActionEvent event) {
        model.updateCurrentClassStudents(2);
        stage.close();
    }

    @FXML
    private void handleAfter(ActionEvent event) {
        model.updateCurrentClassStudents(3);
        stage.close();
    }

    @FXML
    private void handleNormal(ActionEvent event) {
        model.updateCurrentClassStudents(0);
        stage.close();
    }

    /**
     * Sets the stage of the modal.
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
