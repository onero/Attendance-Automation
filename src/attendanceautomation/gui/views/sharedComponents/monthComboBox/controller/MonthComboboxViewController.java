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

    private ObservableList<String> viewSelection;

    private enum EView {
        WEEK,
        MONTH
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        viewSelection = FXCollections.observableArrayList("Uge", "Måned");

        viewSelect.setItems(viewSelection);
    }

    @FXML
    private void handleViewSelection() {
        String selection = viewSelect.getSelectionModel().getSelectedItem();

        switch (selection) {
            case "Uge":
                SchemaModel.getInstance().setCurrentWeekNumber(5);
                RootViewController.getInstance().reloadView();
                break;
            case "Måned":
                SchemaModel.getInstance().setCurrentWeekNumber(0);
                RootViewController.getInstance().reloadView();
                break;
            default:

        }
    }

}
