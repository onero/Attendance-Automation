/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.main;

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

    @FXML
    private AnchorPane pieChartPane;
    @FXML
    private PieChart PieChart;

    private final ObservableList<PieChart.Data> pieChartData
            = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PieChart.setData(pieChartData);
        //Display the data on the chart
//        PieChart.getData().forEach(data
//                -> data.nameProperty().bind(
//                        Bindings.concat(
//                                data.getName(), " ", data.pieValueProperty(), " %"
//                        )
//                )
//        );
        PieChart.setTitle("Fravær");
    }

    public void setData(ObservableList<PieChart.Data> data) {
        pieChartData.clear();
        pieChartData.addAll(data);
    }

}
