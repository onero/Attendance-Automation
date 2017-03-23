/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.currentClassView.controller;

import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Skovgaard
 */
public class CurrentClassViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private Node LIST_VIEW_PRESENT;
    private Node LIST_VIEW_ABSENCE;
    private Node PIE_CHART;

    private CurrentClassListViewController controllerPresent, controllerAbsence;

    private SchoolClassModel model;

    private CurrentClassPieChartController pieChartController;

    public CurrentClassViewController() {
        model = SchoolClassModel.getInstance();
        try {
            LIST_VIEW_PRESENT = createListViewPresent();
            LIST_VIEW_ABSENCE = createListViewAbsence();
            PIE_CHART = createPieChartNode();
        } catch (IOException ex) {
            Logger.getLogger(CurrentClassViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setLeft(LIST_VIEW_PRESENT);
        borderPane.setRight(LIST_VIEW_ABSENCE);
        borderPane.setCenter(PIE_CHART);
    }

    /**
     * Creathes the list of the present students.
     *
     * @return @throws IOException TODO LATER REFACTOER TO ONE METHOD.
     */
    private Node createListViewPresent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.CURRENT_CLASS_LIST_VIEW.toString()));
        Node node = loader.load();
        controllerPresent = setItemsInList(loader, model.getCurrentClassStudentsPresent());
        return node;
    }

    /**
     * Creathes the list of the present students.
     *
     * @return @throws IOException TODO LATER REFACTOER TO ONE METHOD.
     */
    private Node createListViewAbsence() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.CURRENT_CLASS_LIST_VIEW.toString()));
        Node node = loader.load();
        controllerAbsence = setItemsInList(loader, model.getCurrentClassStudentsAbsence());
        return node;
    }

    private CurrentClassListViewController setItemsInList(FXMLLoader loader, ObservableList<Student> listOfStudents) {
        CurrentClassListViewController controller = loader.getController();
        controller.setItemsInList(listOfStudents);
        return controller;
    }

    /**
     * Creates the modal controlling to set the mockCurrentView.
     *
     * @param event
     */
    @FXML
    private void handleSetMock(ActionEvent event) {
        Stage primStage = (Stage) borderPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/views/currentClassView/view/MockModal.fxml"));
        try {
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(scene);

            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(primStage);

            MockModalController controller = loader.getController();
            controller.setStage(newStage);
            controller.setControllers(controllerPresent, controllerAbsence, pieChartController);

            newStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CurrentClassViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the pieChartNode.
     *
     * @return
     * @throws IOException
     */
    private Node createPieChartNode() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.CURRENT_CLASS_PIE_CHART.toString()));
        Node node = loader.load();

        pieChartController = loader.getController();
        pieChartController.updateChart();
        return node;
    }

}
