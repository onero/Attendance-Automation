/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.datePicker.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Rasmus
 */
public class DatePickerViewController implements Initializable {

    @FXML
    private DatePicker dpEnd;
    @FXML
    private DatePicker dpStart;

//    private final LocalDate mockDate;
    public DatePickerViewController() {
//        mockDate = "2017-02-24";
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpStart.setValue(LocalDate.of(2017, 2, 1));
        dpEnd.setValue(LocalDate.now());
        setDayCellFactoryStart();
        setDayCellFactoryEnd();
    }

    /**
     * Sets the dayCellFacotry of dpStart to not overexcede dpEnd.
     */
    private void setDayCellFactoryStart() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isAfter(dpEnd.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE");
                        }
                    }
                };
            }
        };
        dpStart.setDayCellFactory(dayCellFactory);
    }

    /**
     * Sets the dayCellFactory of the dpEnd to not overexcede dpStart
     */
    private void setDayCellFactoryEnd() {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(dpStart.getValue())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #EEEEEE");
                        }
                    }
                };
            }
        };
        dpEnd.setDayCellFactory(dayCellFactory);
    }

}
