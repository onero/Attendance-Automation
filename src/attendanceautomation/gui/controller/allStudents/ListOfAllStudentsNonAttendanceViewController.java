/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.allStudents;

import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.controller.RootViewController;
import attendanceautomation.gui.controller.components.studentAttendanceInformation.StudentAttendanceInformationViewController;
import attendanceautomation.gui.controller.detailedStudent.DetailedStudentViewController;
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

    private Node WEEKS_IN_MONTH_VIEW;

    public ListOfAllStudentsNonAttendanceViewController() {
        try {
            WEEKS_IN_MONTH_VIEW = createWeeksInMonthView();
        } catch (IOException e) {
            System.out.println("Couldn't create weeksInMonth");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCellFactory();
        BorderPane.setTop(WEEKS_IN_MONTH_VIEW);
    }

    /**
     * Populate the list vith students.
     *
     * @param model
     */
    public void setItemsInList(SchoolClassModel model) {
        //TODO RKL: Add feature to select class.
        listView.setItems(model.getStudents());
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
                    System.out.println("Error loading allStudents listView!" + ex.getMessage());
                }
                return cell;
            }
        });
    }

    /**
     * Creates the node of the daysInMonthView
     *
     * @return
     * @throws IOException
     */
    private Node createWeeksInMonthView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.DAYS_IN_MONTH_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

}
