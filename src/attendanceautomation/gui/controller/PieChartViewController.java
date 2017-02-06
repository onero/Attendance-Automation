/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller;

import attendanceautomation.gui.model.AttendanceModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 */
public class PieChartViewController implements Initializable {

    private AttendanceModel attendanceModel;
    @FXML
    private AnchorPane pieChartPane;
    @FXML
    private PieChart PieChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO ALH: Load data from model

        //Mock data for pieChart.
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Donald", 2),
                new PieChart.Data("Daisy", 10),
                new PieChart.Data("Scrouge", 100));
        PieChart.setData(pieChartData);
        PieChart.setTitle("Fravær");
    }

}
