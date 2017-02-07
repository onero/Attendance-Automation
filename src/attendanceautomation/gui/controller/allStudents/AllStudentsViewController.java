/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.allStudents;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class AllStudentsViewController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private Node checkBoxView;
    private Node LIST_VIEW;
    
    public AllStudentsViewController(){
        try {
            LIST_VIEW = createListView();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.setCenter(LIST_VIEW);
    }

    private Node createListView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/attendanceautomation/gui/view/allStudents/ListOfAllStudentsNonAttendanceView.fxml"));
        Node node = loader.load();
        return node;
    }

}