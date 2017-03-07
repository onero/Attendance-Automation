/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.pieChart.controller;

import attendanceautomation.gui.model.PieChartModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;

/**
 * FXML Controller class
 *
 */
public class PieChartViewController implements Initializable {

    @FXML
    private PieChart PieChart;

    private final ObservableList<Data> pieChartData;

    private final PieChartModel pieChartModel;

    public PieChartViewController() {
        pieChartModel = PieChartModel.getInstance();
        pieChartData = FXCollections.observableArrayList();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PieChart.setData(pieChartData);
        PieChart.setLegendVisible(false);
    }

    /**
     * Bind the data to the chart
     *
     */
    public void setData() {
        pieChartData.clear();
        pieChartData.addAll(pieChartModel.getPieChartData());

        displayDataInformationOnChart();

    }

    /**
     * For each data entry in the PieChart, display the name and value
     */
    private void displayDataInformationOnChart() {
        pieChartData.forEach(data
                -> data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), " %"
                        )
                )
        );
    }

}
