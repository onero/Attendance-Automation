/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controller.components.studentAttendanceInformation;

import attendanceautomation.be.enums.EFXMLNames;
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
        lblMonth.setText("Februar");
        HBox.getChildren().add(new Label("Week 5"));
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(new Label("Week 6"));
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(new Label("Week 7"));
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(createFillerLabel());
        HBox.getChildren().add(new Label("Week 8"));
//        HBox.getChildren().add(new Label("2     "));
//        HBox.getChildren().add(new Label("3     "));
//        HBox.getChildren().add(new Label("4     "));
//        HBox.getChildren().add(new Label("5   "));
//        HBox.getChildren().add(new Label("8    "));
//        HBox.getChildren().add(new Label("9    "));
//        HBox.getChildren().add(new Label("10  "));
//        HBox.getChildren().add(new Label("11  "));
//        HBox.getChildren().add(new Label("12  "));
//        HBox.getChildren().add(createFillerLabel());
//        HBox.getChildren().add(new Label("15  "));
//        HBox.getChildren().add(new Label("16  "));
//        HBox.getChildren().add(new Label("17  "));
//        HBox.getChildren().add(new Label("18  "));
//        HBox.getChildren().add(new Label("19    "));
//        HBox.getChildren().add(createFillerLabel());
//        HBox.getChildren().add(new Label("22  "));
//        HBox.getChildren().add(new Label("23  "));
//        HBox.getChildren().add(new Label("24  "));
//        HBox.getChildren().add(new Label("25  "));
//        HBox.getChildren().add(new Label("26  "));
//        HBox.getChildren().add(createParentCheckBoxView());
//        HBox.getChildren().add(createParentCheckBoxView());
//        HBox.getChildren().add(createFillerLabel());
//        HBox.getChildren().add(createParentCheckBoxView());
//        HBox.getChildren().add(createFillerLabel());
    }

}
