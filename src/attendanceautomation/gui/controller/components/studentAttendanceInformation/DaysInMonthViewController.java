/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.AttendanceAutomationMain;
import attendanceautomation.be.EFXMLNames;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class DaysInMonthViewController implements Initializable {

    @FXML
    private HBox HBox;
    @FXML
    private Label lblMonth;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillUpHBox();
        } catch (Exception e) {
        }
    }

//    /**
//     * Creates a ParentCheckBoxView
//     */
//    private Node createParentCheckBoxView() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(AttendanceAutomationMain.PARENT_CHECKBOX_STRING));
//        Node node = loader.load();
//        return node;
//    }
    /**
     * Creates a fillerLabel.
     *
     * @return
     * @throws IOException
     */
    private Node createFillerLabel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(EFXMLNames.FILLER_LABEL.toString()));
        Node node = loader.load();
        return node;
    }

    /**
     * Fills the hBox up to look nicely.
     *
     * @throws IOException
     */
    private void fillUpHBox() throws IOException {
        //TODO ALH: Fix this!
        HBox.getChildren().add(new Label("1     "));
        HBox.getChildren().add(new Label("2     "));
        HBox.getChildren().add(new Label("3     "));
        HBox.getChildren().add(new Label("4     "));
        HBox.getChildren().add(new Label("5     "));
//        HBox.getChildren().add(createParentCheckBoxView());
//        HBox.getChildren().add(createFillerLabel());
//        HBox.getChildren().add(createParentCheckBoxView());
//        HBox.getChildren().add(createFillerLabel());
//        HBox.getChildren().add(createParentCheckBoxView());
//        HBox.getChildren().add(createFillerLabel());
    }

}
