/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.main;

import attendanceautomation.be.Student;
import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.controller.RootViewController;
import attendanceautomation.gui.controller.detailedStudent.DetailedStudentViewController;
import attendanceautomation.gui.controls.StudentListViewCell;
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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class ListOfNameStatisticsViewController implements Initializable {

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
     * Populate the list with students.
     *
     * @param model
     */
    public void setItemsInList(SchoolClassModel model) {
        //TODO ALH: Add feature to select class
        listView.setItems(model.getStudents());
    }

    /**
     * Set the cellFactory of the listView meaning how the individual cell is
     * displayet. Creates a NameStatisticsView and populate it with data for
     * every cell.
     */
    private void setCellFactory() {
        listView.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                StudentListViewCell cell = new StudentListViewCell();
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.NAME_STATISTICS_VIEW.toString()));
                    Node node = loader.load();
                    NameStatisticsViewController controller = loader.getController();
                    cell.setController(controller);
                    cell.setView(node);
                    cell.setGraphic(node);
                    return cell;
                } catch (IOException ex) {
                    System.out.println("Error loading individual cells in the listView!" + ex.getMessage());
                }
                return cell;
            }
        });
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

}
