/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.monthComboBox.controller;

import attendanceautomation.gui.model.SchemaModel;
import attendanceautomation.gui.views.rootView.controller.RootViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Mathias
 */
public class MonthComboboxViewController implements Initializable {

    @FXML
    private ComboBox<String> viewSelect;
    @FXML
    private ComboBox<String> weekPicker;

    private final SchemaModel schemaModel;

    private ObservableList<String> viewSelection;

    public MonthComboboxViewController() {
        schemaModel = SchemaModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewSelection = FXCollections.observableArrayList("Uge", "Måned");
        viewSelect.setItems(viewSelection);
        disableWeekPicker();

        weekPicker.setItems(SchemaModel.getInstance().getWeekNamesInFebruary());
    }

    private void disableWeekPicker() {
        weekPicker.setDisable(true);
        weekPicker.setVisible(false);
    }

    @FXML
    private void handleViewSelection() {
        String selection = viewSelect.getSelectionModel().getSelectedItem();

        switch (selection) {
            case "Uge":
                RootViewController.getInstance().reloadView();
                weekPicker.setDisable(false);
                weekPicker.setVisible(true);
                break;
            case "Måned":
                schemaModel.setCurrentWeekNumber(0);
                RootViewController.getInstance().reloadView();
                disableWeekPicker();
                break;
            default:

        }
    }

    @FXML
    private void handleWeekSelection() {
        String week = weekPicker.getSelectionModel().getSelectedItem();

        switch (week) {
            case SchemaModel.WEEK5:
                schemaModel.setCurrentWeekNumber(5);
                RootViewController.getInstance().reloadView();
                break;
            case SchemaModel.WEEK6:
                schemaModel.setCurrentWeekNumber(6);
                RootViewController.getInstance().reloadView();
                break;
            case SchemaModel.WEEK7:
                schemaModel.setCurrentWeekNumber(7);
                RootViewController.getInstance().reloadView();
                break;
            case SchemaModel.WEEK8:
                schemaModel.setCurrentWeekNumber(8);
                RootViewController.getInstance().reloadView();
                break;
            default:
                break;
        }
    }

}
