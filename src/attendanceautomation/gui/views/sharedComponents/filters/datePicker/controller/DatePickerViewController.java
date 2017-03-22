/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.filters.datePicker.controller;

import attendanceautomation.gui.model.PieChartModel;
import attendanceautomation.gui.model.SchemaModel;
import attendanceautomation.gui.model.SchoolClassModel;
import attendanceautomation.gui.views.rootView.controller.RootViewController;
import attendanceautomation.gui.views.sharedComponents.pieChart.controller.PieChartViewController;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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

    private final SchemaModel schemaModel;

    private final SchoolClassModel schoolClassModel;

    public DatePickerViewController() {
        schemaModel = SchemaModel.getInstance();
        schoolClassModel = SchoolClassModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpStart.setValue(createLocalDate(schemaModel.getStartDate()));
        dpEnd.setValue(createLocalDate(schemaModel.getEndDate()));
        setDayCellFactoryStart();
        setDayCellFactoryEnd();
//        schemaModel.setStartDate(dpStart.getValue().toString());
//        schemaModel.setEndDate(dpEnd.getValue().toString());
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

    @FXML
    private void handleStartDate(ActionEvent event) {
        schemaModel.setStartDate(dpStart.getValue().toString());
    }

    @FXML
    private void handleEndDate(ActionEvent event) {
        schemaModel.setEndDate(dpEnd.getValue().toString());

        schemaModel.setCurrentMonth(schemaModel.getStartDate(), schemaModel.getEndDate());
        schoolClassModel.setCurrentSchoolClass(schoolClassModel.getCurrentSchoolClass().getID());
        RootViewController.getInstance().reloadView();
        PieChartModel.getInstance().resetPieChart();
        PieChartViewController.getInstance().updateChart();
    }

    private LocalDate createLocalDate(String date) {
        String[] stringArr = date.split("-");
        int[] intArr = new int[stringArr.length];
        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = Integer.parseInt(stringArr[i]);
        }
        LocalDate localDate = LocalDate.of(intArr[0], intArr[1], intArr[2]);
        return localDate;
    }
}
