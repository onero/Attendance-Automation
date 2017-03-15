/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.currentClassView.controller;

import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

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

    private SchoolClassModel model = SchoolClassModel.getInstance();

    public CurrentClassViewController() {
        try {
            LIST_VIEW_PRESENT = createListViewPresent();
            LIST_VIEW_ABSENCE = createListViewAbsence();
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
    }

    /**
     * Creathes the list of the present students.
     *
     * @return @throws IOException TODO LATER REFACTOER TO ONE METHOD.
     */
    private Node createListViewPresent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.CURRENT_CLASS_LIST_VIEW.toString()));
        Node node = loader.load();
        setItemsInList(loader);
        return node;
    }

    /**
     * Creathes the list of the present students.
     *
     * @return @throws IOException TODO LATER REFACTOER TO ONE METHOD.
     */
    private Node createListViewAbsence() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.CURRENT_CLASS_LIST_VIEW.toString()));
        Node node = loader.load();
        setItemsInList(loader);
        return node;
    }

    private void setItemsInList(FXMLLoader loader) {
        CurrentClassListViewController controller = loader.getController();
        controller.setItemsInList(model.getCurrentClassStudents());
    }

}
