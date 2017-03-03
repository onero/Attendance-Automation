/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.allStudents.controller;

import attendanceautomation.be.enums.EFXMLNames;
import attendanceautomation.gui.model.SchoolClassModel;
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

    private SchoolClassModel schoolClassModel;

    public AllStudentsViewController() {
        schoolClassModel = SchoolClassModel.getInstance();
        try {
            LIST_VIEW = createListView();
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
    }

    /**
     * Creates the node of the listView and parsed the schoolClassModel to its
     * controller.
     *
     * @return
     * @throws IOException
     */
    private Node createListView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.LIST_OF_ALL_STUDENTS_NON_ATTENDANCE_VIEW.toString()));
        Node node = loader.load();
        ListOfAllStudentsNonAttendanceViewController controller = loader.getController();
        controller.setItemsInList(schoolClassModel);
        return node;
    }
//
//    private Node createSearchView() throws IOException {
//        searchViewLoader = new FXMLLoader(getClass().getResource(EFXMLNames.SEARCH_VIEW.toString()));
//        Node node = searchViewLoader.load();
//        return node;
//    }
//
//    private Node createTopViewHolder() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.COMPONENTS_HOLDER_VIEW.toString()));
//        Node node = loader.load();
//        ComponentsHolderViewController controller = loader.getController();
//        controller.setBorderPaneLeft(SEARCH_VIEW);
//        return node;
//    }
}
