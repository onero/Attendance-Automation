/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components;

import attendanceautomation.gui.model.AttendanceModel;
import java.net.URL;
import java.util.ResourceBundle;
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

    private ObservableList<Data> pieChartData
            = FXCollections.observableArrayList();

    AttendanceModel model = AttendanceModel.getInstance();

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
     * @param newData
     */
    public void setData() {
        pieChartData.clear();
        pieChartData.addAll(model.getPieChartData());
//        pieChartData.forEach(data
//                -> data.nameProperty().bind(
//                        Bindings.concat(
//                                data.getName(), " ", data.pieValueProperty(), " %"
//                        )
//                )
//        );

    }

}
