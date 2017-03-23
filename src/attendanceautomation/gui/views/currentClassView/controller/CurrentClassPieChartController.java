/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.currentClassView.controller;

import attendanceautomation.gui.model.PieChartModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class CurrentClassPieChartController implements Initializable {

    @FXML
    private PieChart pieChart;

    private PieChartModel pieModel;

    public CurrentClassPieChartController() {
        pieModel = PieChartModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pieChart.setLabelsVisible(false);
    }

    /**
     * Updates the data in the pieChart.
     */
    public void updateChart() {
        pieChart.setData(pieModel.getCurrentClassPieChartData());
        displayDataInformationOnChart();
    }

    /**
     * For each data entry in the PieChart, display the name and value
     */
    private void displayDataInformationOnChart() {
        pieModel.getCurrentClassPieChartData().forEach(data
                -> data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), " %"
                        )
                )
        );
    }

}
