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
    private CurrentClassListViewController controllerPresent, controllerAbsence;
    private CurrentClassPieChartController pieController;

    public MockModalController() {
        model = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleBefore(ActionEvent event) {
        update(1, "white", "white");
    }

    @FXML
    private void handleAt(ActionEvent event) {
        update(2, "green", "white");
    }

    @FXML
    private void handleAfter(ActionEvent event) {
        update(3, "green", "red");
    }

    @FXML
    private void handleNormal(ActionEvent event) {
        update(0, "green", "red");
    }

    /**
     * Updates the information in CurrentClassView with the information
     * according to the parameters.
     *
     * @param mockStudents
     * @param presentColor
     * @param absentColor
     */
    private void update(int mockStudents, String presentColor, String absentColor) {
        model.updateCurrentClassStudents(mockStudents);
        controllerPresent.setCellFactory(presentColor);
        controllerAbsence.setCellFactory(absentColor);
        pieController.updateChart();
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

    /**
     * Sets the controllers for the two listViews.
     *
     * @param controllerPresent
     * @param controllerAbsence
     * @param pieController
     */
    public void setControllers(CurrentClassListViewController controllerPresent, CurrentClassListViewController controllerAbsence, CurrentClassPieChartController pieController) {
        this.controllerPresent = controllerPresent;
        this.controllerAbsence = controllerAbsence;
        this.pieController = pieController;
    }

}
