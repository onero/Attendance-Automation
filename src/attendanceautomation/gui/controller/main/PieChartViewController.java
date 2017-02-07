/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.main;

import attendanceautomation.gui.model.AttendanceModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 */
public class PieChartViewController implements Initializable {

    private final AttendanceModel attendanceModel;
    @FXML
    private AnchorPane pieChartPane;
    @FXML
    private PieChart PieChart;

    public PieChartViewController() {
        attendanceModel = AttendanceModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PieChart.setData(attendanceModel.getPieChartData());
        //Display the data on the chart
        PieChart.getData().forEach(data
                -> data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", data.pieValueProperty(), " %"
                        )
                )
        );
//        forEach(pieData -> {
//            System.out.println(pieData.getName() + ": "
//            + pieData.getPieValue());
//        });
        PieChart.setTitle("Fravær");
    }

}
