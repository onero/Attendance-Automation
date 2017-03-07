/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.weeksAndDaysBar.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class WeeksAndDaysBarViewController implements Initializable {

    @FXML
    private Label lblMonth;
    @FXML
    private Label lblWeek1;
    @FXML
    private Label lblWeek2;
    @FXML
    private Label lblWeek3;
    @FXML
    private Label lblWeek4;
    @FXML
    private Label fillerLabel;
    @FXML
    private HBox daysWeek1;
    @FXML
    private HBox daysWeek2;
    @FXML
    private HBox daysWeek3;
    @FXML
    private HBox daysWeek4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Sets the prefered and minnimum width of the labels.
     *
     * @param widthMonth
     * @param widthWeek
     */
    public void setWidth(int widthMonth, int widthWeek) {
        lblMonth.setPrefWidth(widthMonth);
        lblMonth.setMinWidth(widthMonth);

        lblWeek1.setPrefWidth(widthWeek);
        lblWeek2.setPrefWidth(widthWeek);
        lblWeek3.setPrefWidth(widthWeek);

        lblWeek1.setMinWidth(widthWeek);
        lblWeek2.setMinWidth(widthWeek);
        lblWeek3.setMinWidth(widthWeek);

        fillerLabel.setPrefWidth(widthMonth);
        fillerLabel.setMinWidth(widthMonth);

        daysWeek1.setPrefWidth(widthWeek);
        daysWeek2.setPrefWidth(widthWeek);
        daysWeek3.setPrefWidth(widthWeek);
        daysWeek4.setPrefWidth(widthWeek);

        daysWeek1.setMinWidth(widthWeek);
        daysWeek2.setMinWidth(widthWeek);
        daysWeek3.setMinWidth(widthWeek);
        daysWeek4.setMinWidth(widthWeek);
    }

    /**
     * Sets the information of the labels to contain the ones of February.
     */
    public void setFebruary() {
        lblMonth.setText("Februar");
        lblWeek1.setText("Uge 5");
        lblWeek2.setText("Uge 6");
        lblWeek3.setText("Uge 7");
        lblWeek4.setText("Uge 8");
    }

}
