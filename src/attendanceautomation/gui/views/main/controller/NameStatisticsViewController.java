/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.main.controller;

import attendanceautomation.be.Student;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class NameStatisticsViewController implements Initializable {

    @FXML
    private Label lblName;
    @FXML
    private Label lblAttendance;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Sets the info about the person in the view.
     *
     * @param student
     */
    public void setStudentInfo(Student student) {
        DecimalFormat df = new DecimalFormat("#.##");
        lblName.setText(student.getFullName());
        lblAttendance.setText(df.format(student.getNonAttendancePercentage().get()) + " %");
    }

}
