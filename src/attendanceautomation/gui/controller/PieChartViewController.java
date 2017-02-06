/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller;

import attendanceautomation.gui.model.AttendanceModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 */
public class PieChartViewController implements Initializable {

    private AttendanceModel attendanceModel = new AttendanceModel();
    @FXML
    private AnchorPane pieChartPane;
    @FXML
    private PieChart PieChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PieChart.setData(attendanceModel.getPieChartData());
        PieChart.setTitle("Fravær");
    }

}
