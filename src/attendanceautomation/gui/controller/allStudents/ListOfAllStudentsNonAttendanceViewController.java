/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.allStudents;

import attendanceautomation.be.Student;
import attendanceautomation.gui.controls.AllStudentsNonAttendanceCell;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class ListOfAllStudentsNonAttendanceViewController implements Initializable {

    @FXML
    private ListView<Student> listView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellFactory();
    }

    /**
     * Populate the list vith students.
     *
     * @param model
     */
    public void setItemsInList(SchoolClassModel model) {
        //TODO RKL: Add feature to select class.
        listView.setItems(model.getSchoolClasses().get(0).getStudents());
    }

    /**
     * Set the cellFactory of the listView. Creates a
     * StudentAttendanceInformationView for each cell.
     */
    private void setCellFactory() {
        listView.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                AllStudentsNonAttendanceCell cell = new AllStudentsNonAttendanceCell();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/allStudents/StudentAttendanceInformationView.fxml"));
                    Node node = loader.load();
                    StudentAttendanceInformationViewController controller = loader.getController();
                    cell.setController(controller);
                    cell.setView(node);
                    cell.setGraphic(node);
                    return cell;
                } catch (IOException ex) {
                    System.out.println("Error loading allStudents listView!" + ex.getMessage());
                }
                return cell;
            }
        });
    }

}
