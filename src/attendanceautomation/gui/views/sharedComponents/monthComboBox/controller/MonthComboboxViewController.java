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
    private ObservableList<String> weeks;

    public MonthComboboxViewController() {
        schemaModel = SchemaModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewSelection = FXCollections.observableArrayList("Uge", "Måned");
        weeks = FXCollections.observableArrayList();
        viewSelect.setItems(viewSelection);
        setVisibilityOfWeekPicker(false);
        for (Integer weekNumber : schemaModel.getWeekNumbers()) {
            weeks.add("Uge " + weekNumber);
        }
        weekPicker.setItems(weeks);
        weekPicker.setPromptText("Vælg uge");
    }

    /**
     * Change visibility of weekPicker
     */
    private void setVisibilityOfWeekPicker(boolean value) {
        weekPicker.setDisable(!value);
        weekPicker.setVisible(value);
        if (value == true) {
            weekPicker.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void handleViewSelection() {
        String selection = viewSelect.getSelectionModel().getSelectedItem();

        switch (selection) {
            case "Uge":
                RootViewController.getInstance().reloadView();
                setVisibilityOfWeekPicker(true);
                break;
            case "Måned":
                schemaModel.currentWeekOfMonthNumber(0);
                RootViewController.getInstance().reloadView();
                setVisibilityOfWeekPicker(false);
                break;
            default:

        }
    }

    @FXML
    private void handleWeekSelection() {
        int week = weekPicker.getSelectionModel().getSelectedIndex();

        switch (week) {
            case 0:
                schemaModel.currentWeekOfMonthNumber(1);
                RootViewController.getInstance().reloadView();
                break;
            case 1:
                schemaModel.currentWeekOfMonthNumber(2);
                RootViewController.getInstance().reloadView();
                break;
            case 2:
                schemaModel.currentWeekOfMonthNumber(3);
                RootViewController.getInstance().reloadView();
                break;
            case 3:
                schemaModel.currentWeekOfMonthNumber(4);
                RootViewController.getInstance().reloadView();
                break;
            default:
                break;
        }
    }

}
