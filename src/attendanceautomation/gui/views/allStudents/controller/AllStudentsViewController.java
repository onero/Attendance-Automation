/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.allStudents.controller;

import attendanceautomation.be.enums.EFXMLName;
import attendanceautomation.gui.views.NodeFactory;
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

    private final Node LIST_VIEW;

    private FXMLLoader searchViewLoader;

    private final Node weeksAndDaysBar;

    public AllStudentsViewController() {
        LIST_VIEW = NodeFactory.getInstance().createNewView(EFXMLName.LIST_OF_ALL_STUDENTS_NON_ATTENDANCE_VIEW);
        weeksAndDaysBar = NodeFactory.getInstance().createNewView(EFXMLName.WEEK_AND_DAYS_BAR);
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
}
