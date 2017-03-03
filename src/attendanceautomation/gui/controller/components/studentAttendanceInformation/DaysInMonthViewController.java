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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author gta1
 */
public class DaysInMonthViewController implements Initializable {

    @FXML
    private Label lblMonth;
    @FXML
    private GridPane GridPane;
    @FXML
    private HBox HBox;

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
        //Create week 5
        GridPane.addColumn(0, new Label("Week 5"));
//        HBox daysInWeek5Box = createDaysInWeekBox();
//        GridPane.add(daysInWeek5Box, 0, 1);

        //Create gap before next week
        GridPane.add(createFillerLabel(), 1, 0);
        GridPane.add(createFillerLabel(), 2, 0);

        /**
         * Create week 6
         */
        GridPane.add(new Label("Week 6"), 3, 0);
//        HBox daysInWeek6Box = createDaysInWeekBox();
//        GridPane.add(daysInWeek6Box, 3, 1);

        //Create gap before next week
        GridPane.add(createFillerLabel(), 4, 0);

        /**
         * Create week 7
         */
        GridPane.add(new Label("Week 7"), 5, 0);
//        HBox daysInWeek7Box = createDaysInWeekBox();
//        GridPane.add(daysInWeek7Box, 5, 1);

        //Create gap before next week
        GridPane.add(createFillerLabel(), 6, 0);

        /**
         * Create week 8
         */
        GridPane.add(new Label("Week 8"), 7, 0);
//        HBox daysInWeek8Box = createDaysInWeekBox();
//        GridPane.add(daysInWeek8Box, 7, 1);
    }

    /**
     * Create an HBox containing the days in the week
     *
     * @return
     */
    private HBox createDaysInWeekBox() {
        HBox daysInWeekBox = new HBox();
        daysInWeekBox.getChildren().add(new Label("M"));
        daysInWeekBox.getChildren().add(new Label("     "));
        daysInWeekBox.getChildren().add(new Label("T"));
        daysInWeekBox.getChildren().add(new Label("     "));
        daysInWeekBox.getChildren().add(new Label("O"));
        daysInWeekBox.getChildren().add(new Label("     "));
        daysInWeekBox.getChildren().add(new Label("T"));
        daysInWeekBox.getChildren().add(new Label("     "));
        daysInWeekBox.getChildren().add(new Label("F"));
        daysInWeekBox.setMinWidth(150);
        return daysInWeekBox;
    }

}
