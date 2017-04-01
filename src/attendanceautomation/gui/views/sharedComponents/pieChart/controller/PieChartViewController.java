/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.pieChart.controller;

import attendanceautomation.gui.model.PieChartModel;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 */
public class PieChartViewController implements Initializable {

    private static PieChartViewController instance;

    @FXML
    private PieChart PieChart;

    private final PieChartModel pieChartModel;

    public static PieChartViewController getInstance() {
        return instance;
    }

    public PieChartViewController() {
        pieChartModel = PieChartModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        PieChart.setLegendVisible(false);

    }

    /**
     * Bind the data to the chart
     *
     * @param view
     */
    public void updateChart() {
        pieChartModel.computeTotalPieChartPercentage();
        PieChart.setData(pieChartModel.getPieChartData());
        displayDataInformationOnChart();
    }

    /**
     * For each data entry in the PieChart, display the name and value
     */
    private void displayDataInformationOnChart() {
        DecimalFormat df = new DecimalFormat("#.##");
        pieChartModel.getPieChartData().forEach(data
                -> data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", df.format(data.pieValueProperty().get()), " %"
                        )
                )
        );
    }
}
