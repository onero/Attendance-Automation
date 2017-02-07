/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.main;

import attendanceautomation.be.Student;
import attendanceautomation.gui.controller.RootViewController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

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
        // TODO
    }

    /**
     * Sets the info about the person in the view.
     *
     * @param student
     */
    public void setStudentInfo(Student student) {
        lblName.setText(student.getFullName());
        lblAttendance.setText("" + student.getAttendancePercentage() + " %");
    }

    /**
     * Switches the center node to be the detailed student view
     *
     * @param event
     */
    @FXML
    private void handleOpenStudentDetails(MouseEvent event) {
        //If user double clicks. Switch view
        if (event.getClickCount() == 2) {
            RootViewController.getInstance().selectDetailedStudentView();
        }
    }

}
