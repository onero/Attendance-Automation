/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller;

import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.controller.allStudents.AllStudentsViewController;
import attendanceautomation.gui.controller.components.SearchViewController;
import attendanceautomation.gui.controller.main.MainViewController;
import attendanceautomation.gui.model.SchoolClassModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author gta1
 */
public class RootViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private static RootViewController instance;

    private Node MAIN_VIEW;
    private Node ALL_STUDENTS_VIEW;
    private Node DETAILED_STUDENT_VIEW;
    
    private FXMLLoader allStudentLoader;
    
    private FXMLLoader mainViewLoader;

    public static RootViewController getInstance() {
        return instance;
    }

    public RootViewController() {
        try {
            MAIN_VIEW = createMainView();
            ALL_STUDENTS_VIEW = createAllStudents();
            DETAILED_STUDENT_VIEW = createDetailedStudentView();
        } catch (IOException ex) {
            System.out.println("MainView not loaded! " + ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        setCenter(MAIN_VIEW);
    }

    /**
     * Creates the node containing MainView.
     *
     * @return
     * @throws IOException
     */
    private Node createMainView() throws IOException {
        mainViewLoader = new FXMLLoader(getClass().getResource(EFXMLNames.MAIN_VIEW.toString()));
        Node node = mainViewLoader.load();
        return node;
    }

    /**
     * Creates the node containing AllStudentsView.
     *
     * @return
     * @throws IOException
     */
    private Node createAllStudents() throws IOException {
        allStudentLoader = new FXMLLoader(getClass().getResource(EFXMLNames.ALL_STUDENTS_VIEW.toString()));
        Node node = allStudentLoader.load();
        return node;
    }

    /**
     * Create the StudentInformationTopView
     *
     * @return
     * @throws IOException
     */
    private Node createDetailedStudentView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.DETAILED_STUDENT_VIEW.toString()));
        Node node = loader.load();
        return node;
    }

    /**
     * Sets the All_STUDENTS_VIEW as the center node
     *
     * @param event
     */
    @FXML
    private void handleAllStudentsButton(ActionEvent event) {
        setCenter(ALL_STUDENTS_VIEW);
        AllStudentsViewController controller = allStudentLoader.getController();
        SearchViewController sController = controller.getSearchViewLoader().getController();
        sController.setSearchText(SchoolClassModel.getInstance().getSearchString().get());
    }

    /**
     * Sets the MAIN_VIEW as the center view
     *
     * @param event
     */
    @FXML
    private void handleStartView(ActionEvent event) {
        setCenter(MAIN_VIEW);
        MainViewController controller = mainViewLoader.getController();
        SearchViewController sController = controller.getSearchViewLoader().getController();
        sController.setSearchText(SchoolClassModel.getInstance().getSearchString().get());
    }

    /**
     * Sets the center node in the borderpane to the parsed view
     *
     * @param newView
     */
    private void setCenter(Node newView) {
        borderPane.setCenter(newView);
    }

    /**
     * Sets the node to be the detailed student view
     *
     * @param selectedStudent
     */
    public void selectDetailedStudentView() {
        setCenter(DETAILED_STUDENT_VIEW);
    }

}
