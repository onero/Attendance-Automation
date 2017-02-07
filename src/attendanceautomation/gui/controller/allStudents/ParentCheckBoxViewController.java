/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.allStudents;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class ParentCheckBoxViewController implements Initializable {

    @FXML
    private BorderPane checkBoxPane;
    @FXML
    private HBox horizontalCheckBoxPane;
    
    private ArrayList<CheckBox> listOfCheckBoxes;
    
    public ParentCheckBoxViewController(){
        listOfCheckBoxes = new ArrayList<>();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pouplateWithCheckBoxes();
    }
    
    /**
     * Creates 5 checkBoxes and puts them in the horizontalCheckBoxPane and listOfCheckBoxes.
     */
    private void pouplateWithCheckBoxes() {
        for(int i = 0; i < 5; i++){
            CheckBox newCheckBox = new CheckBox();
            horizontalCheckBoxPane.getChildren().add(newCheckBox);
            listOfCheckBoxes.add(newCheckBox);
        }
    }
    
    /**
     * Returns listOfCheckboxes.
     * @return 
     */
    public ArrayList<CheckBox> getCheckBoxes(){
        return listOfCheckBoxes;
    }

}
