/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.allStudents.controller;

import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.controls.AllStudentsNonAttendanceCell;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.detailedStudent.controller.DetailedStudentViewController;
import attendanceautomation.gui.views.rootView.controller.RootViewController;
import attendanceautomation.gui.views.sharedComponents.studentAttendanceInformation.controller.StudentAttendanceInformationViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class ListOfAllStudentsNonAttendanceViewController implements Initializable {

    @FXML
    private ListView<Student> listView;
    @FXML
    private BorderPane BorderPane;

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
     */
    public void setItemsInList() {
        listView.setItems(SchoolClassModel.getInstance().getStudents());
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
            RootViewController.getInstance().handleDetailedStudentView();
            Student selectedStudent = listView.getSelectionModel().getSelectedItem();
            DetailedStudentViewController.getInstance().setCurrentStudent(selectedStudent);
        }
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.STUDENTS_ATTENDANCE_INFORMATION.toString()));
                    Node node = loader.load();
                    StudentAttendanceInformationViewController controller = loader.getController();
                    cell.setController(controller);
                    cell.setView(node);
                    cell.setGraphic(node);
                    return cell;
                } catch (IOException ex) {
                    System.out.println("Error loading allStudents listView!");
                    System.out.println(ex);
                }
                return cell;
            }
        });
    }

}
