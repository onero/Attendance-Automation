/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SchemaModel {

    private static SchemaModel instance;

    public static final String WEEK5 = "Uge 5";
    public static final String WEEK6 = "Uge 6";
    public static final String WEEK7 = "Uge 7";
    public static final String WEEK8 = "Uge 8";

    public static SchemaModel getInstance() {
        if (instance == null) {
            instance = new SchemaModel();
        }
        return instance;
    }

    private final List<List<Integer>> weeksInFebruary;

    private final ObservableList<String> weekNamesInFebruary;

    private final List<Integer> firstWeekFebruary;
    private final List<Integer> secondWeekFebruary;
    private final List<Integer> thirdWeekFebruary;
    private final List<Integer> lastWeekFebruary;

    private int currentWeekNumber;

    private SchemaModel() {
        firstWeekFebruary = new ArrayList<>();
        secondWeekFebruary = new ArrayList<>();
        thirdWeekFebruary = new ArrayList<>();
        lastWeekFebruary = new ArrayList<>();
        weeksInFebruary = new ArrayList<>();
        createFebruaryWeeks();
        addAllWeeksToLargeArray();
        weekNamesInFebruary = FXCollections.observableArrayList();
        weekNamesInFebruary.add(WEEK5);
        weekNamesInFebruary.add(WEEK6);
        weekNamesInFebruary.add(WEEK7);
        weekNamesInFebruary.add(WEEK8);

        //Zero for all weeks in month
        currentWeekNumber = 0;
    }

    private void addAllWeeksToLargeArray() {
        weeksInFebruary.add(firstWeekFebruary);
        weeksInFebruary.add(secondWeekFebruary);
        weeksInFebruary.add(thirdWeekFebruary);
        weeksInFebruary.add(lastWeekFebruary);
    }

    private void createFebruaryWeeks() {
        firstWeekFebruary.add(30);
        firstWeekFebruary.add(31);
        firstWeekFebruary.add(1);
        firstWeekFebruary.add(2);
        firstWeekFebruary.add(3);

        secondWeekFebruary.add(6);
        secondWeekFebruary.add(7);
        secondWeekFebruary.add(8);
        secondWeekFebruary.add(9);
        secondWeekFebruary.add(10);

        thirdWeekFebruary.add(13);
        thirdWeekFebruary.add(14);
        thirdWeekFebruary.add(15);
        thirdWeekFebruary.add(16);
        thirdWeekFebruary.add(17);

        lastWeekFebruary.add(20);
        lastWeekFebruary.add(21);
        lastWeekFebruary.add(22);
        lastWeekFebruary.add(23);
        lastWeekFebruary.add(24);
    }

    public void setCurrentWeekNumber(int currentWeekNumber) {
        this.currentWeekNumber = currentWeekNumber;
    }

    public int getCurrentWeekNumber() {
        return currentWeekNumber;
    }

    public List<Integer> getFirstWeekFebruary() {
        return firstWeekFebruary;
    }

    public List<Integer> getSecondWeekFebruary() {
        return secondWeekFebruary;
    }

    public List<Integer> getThirdWeekFebruary() {
        return thirdWeekFebruary;
    }

    public List<Integer> getLastWeekFebruary() {
        return lastWeekFebruary;
    }

    public List<List<Integer>> getWeeksInFebruary() {
        return weeksInFebruary;
    }

    public ObservableList<String> getWeekNamesInFebruary() {
        return weekNamesInFebruary;
    }

}
