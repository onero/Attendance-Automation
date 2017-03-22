/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.views.sharedComponents.weeksAndDaysBar.controller;

import attendanceautomation.gui.model.SchemaModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private HBox hBoxWeekNumber;
    @FXML
    private HBox hBoxDays;
    @FXML
    private Label lblMonth;
    @FXML
    private Label fillerLabel;

    private Label weekNumber;

    private final List<Label> weekNumbers;

    private final List<Label> dayLabels;

    private final List<Label> fillerLabels;

    private final List<HBox> weekDays;

    private final SchemaModel schemaModel;

    public WeeksAndDaysBarViewController() {
        schemaModel = SchemaModel.getInstance();
        weekNumbers = new ArrayList<>();
        weekDays = new ArrayList<>();
        dayLabels = new ArrayList<>();
        fillerLabels = new ArrayList<>();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblMonth.setText("Februar");
        createWeekLabels();
    }

    private void createWeekLabels() {
        int currentWeekOfMonthNumber = schemaModel.getCurrentWeekOfMonthNumber();
        Label filler = new Label();
        switch (currentWeekOfMonthNumber) {
            case 1:
                createWeekNumberLabel(schemaModel.getWeekNumbers().get(0));
                createDayLabels();
                break;
            case 2:
                createWeekNumberLabel(schemaModel.getWeekNumbers().get(0));
                createDayLabels();
                filler = new Label();
                hBoxDays.getChildren().add(filler);
                fillerLabels.add(filler);
                createWeekNumberLabel(schemaModel.getWeekNumbers().get(1));
                createDayLabels();
                break;
            case 3:
                createWeekNumberLabel(schemaModel.getWeekNumbers().get(1));
                filler = new Label();
                hBoxDays.getChildren().add(filler);
                fillerLabels.add(filler);
                createDayLabels();
                createWeekNumberLabel(schemaModel.getWeekNumbers().get(2));
                filler = new Label();
                hBoxDays.getChildren().add(filler);
                fillerLabels.add(filler);
                createDayLabels();
                break;
            default:
                //Create all weeks
                for (Integer currentWeek : schemaModel.getWeekNumbers()) {
                    createWeekNumberLabel(currentWeek);
                    createDayLabels();
                    if (schemaModel.getWeekNumbers().indexOf(currentWeek) != schemaModel.getWeekNumbers().size()) {
                        filler = new Label();
                        hBoxDays.getChildren().add(filler);
                        fillerLabels.add(filler);
                    }
                }
        }

    }

    /**
     * Create a label with the name and number of the week
     *
     * @param currentWeekNumber
     */
    private void createWeekNumberLabel(int currentWeekNumber) {
        weekNumber = new Label("Uge " + currentWeekNumber);
        hBoxWeekNumber.getChildren().add(weekNumber);
        weekNumbers.add(weekNumber);
    }

    /**
     * Create a label for each schoolday
     */
    private void createDayLabels() {
        Label monday = new Label("M");
        hBoxDays.getChildren().add(monday);
        dayLabels.add(monday);

        Label tuesday = new Label("T");
        hBoxDays.getChildren().add(tuesday);
        dayLabels.add(tuesday);

        Label wednesday = new Label("O");
        hBoxDays.getChildren().add(wednesday);
        dayLabels.add(wednesday);

        Label thursday = new Label("T");
        hBoxDays.getChildren().add(thursday);
        dayLabels.add(thursday);

        Label friday = new Label("F");
        hBoxDays.getChildren().add(friday);
        dayLabels.add(friday);
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
        lblMonth.setMaxWidth(widthMonth);

        fillerLabel.setPrefWidth(widthMonth);
        fillerLabel.setMinWidth(widthMonth);
        fillerLabel.setMaxWidth(widthMonth);

        for (Label currentFillerLabel : fillerLabels) {
            currentFillerLabel.setPrefWidth(40);
            currentFillerLabel.setMinWidth(40);
            currentFillerLabel.setMaxWidth(40);
        }

        /**
         * Set prefered width for all weekNumbers
         */
        for (Label currentWeekNumber : weekNumbers) {
            currentWeekNumber.setPrefWidth(widthWeek);
            currentWeekNumber.setMinWidth(widthWeek);
            currentWeekNumber.setMaxWidth(widthWeek);
        }

        /**
         * Set prefered width for daysBox
         */
        for (HBox weekDay : weekDays) {
            weekDay.setPrefWidth(widthWeek);
            weekDay.setMinWidth(widthWeek);
            weekDay.setMaxWidth(widthWeek);
        }
        int width = 30;
        for (Label dayLabel : dayLabels) {
            //If it's not the last label set a width
            if (dayLabels.indexOf(dayLabel) != dayLabels.size() - 1) {
                dayLabel.setPrefWidth(width);
                dayLabel.setMinWidth(width);
                dayLabel.setMaxWidth(width);
            }
        }

    }

}
