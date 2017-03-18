/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.allStudents.controller;

import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.gui.views.sharedComponents.weeksAndDaysBar.controller.WeeksAndDaysBarViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 */
public class AllStudentsViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private Node LIST_VIEW;

    private FXMLLoader searchViewLoader;

    private Node weeksAndDaysBar;

    public AllStudentsViewController() {
        try {
            LIST_VIEW = createListView();
            weeksAndDaysBar = createWeeksAndDaysBar();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public FXMLLoader getSearchViewLoader() {
        return searchViewLoader;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(LIST_VIEW);
        borderPane.setTop(weeksAndDaysBar);
    }

    /**
     * Creates the node of the listView and parsed the schoolClassModel to its
     * controller.
     *
     * @return
     * @throws IOException
     */
    private Node createListView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.LIST_OF_ALL_STUDENTS_NON_ATTENDANCE_VIEW.toString()));
        Node node = loader.load();
        ListOfAllStudentsNonAttendanceViewController controller = loader.getController();
        controller.setItemsInList();
        return node;
    }

    private Node createWeeksAndDaysBar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLName.WEEK_AND_DAYS_BAR.toString()));
        Node node = loader.load();

        WeeksAndDaysBarViewController controller = loader.getController();
        controller.setWidth(150, 190);
        return node;
    }
}
