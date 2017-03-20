/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.locationFilter.controller;

import attendanceautomation.gui.model.PieChartModel;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.sharedComponents.filters.schoolClassFilter.controller.SchoolClassFilterViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Mathias
 */
public class LocationFilterViewController implements Initializable {

    @FXML
    private ComboBox<String> comboLocationFilter;

    private static LocationFilterViewController instance;

    private final SchoolClassModel schoolClassModel = SchoolClassModel.getInstance();

    public static LocationFilterViewController getInstance() {
        return instance;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        comboLocationFilter.setItems(schoolClassModel.getLocationNames());
        selectFirst();
    }

    @FXML
    private void handleLocationFilter() {
        String location = comboLocationFilter.getSelectionModel().getSelectedItem();
        int locationID = schoolClassModel.getLocationID(location);
        Runnable task = () -> {
            Platform.runLater(() -> {
                schoolClassModel.loadSchoolClassByLocation(locationID);
                PieChartModel.getInstance().resetPieChart();
                SchoolClassFilterViewController.getInstance().openBox();
                schoolClassModel.clearSemesters();
            });
        };
        new Thread(task).start();
    }

    /**
     * Select the first element
     */
    public void selectFirst() {
        comboLocationFilter.getSelectionModel().selectFirst();
    }

}
