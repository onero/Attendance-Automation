/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import attendanceautomation.bll.SchemaManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SchemaModel {

    private static SchemaModel instance;

    private final SchemaManager schemaManager;

    public static SchemaModel getInstance() {
        if (instance == null) {
            instance = new SchemaModel();
        }
        return instance;
    }

    public static final int FIRST_WEEK_NUMBER = 1;
    public static final int SECOND_WEEK_NUMBER = 2;
    public static final int THIRD_WEEK_NUMBER = 3;
    public static final int LAST_WEEK_NUMBER = 4;

    private final List<Date> firstWeekOfMonth;
    private final List<Date> secondWeekOfMonth;
    private final List<Date> thirdWeekOfMonth;
    private final List<Date> lastWeekOfMonth;
    private final List<List<Date>> weeksOfMonth;

    private final List<Integer> weekNumbers;

    private String nameOfMonth;

    private int currentWeekOfMonth;

    private String startDate;
    private String endDate;

    private StringProperty propertyStartDate = new SimpleStringProperty();
    private StringProperty propertyEndDate = new SimpleStringProperty();

    private SchemaModel() {
        schemaManager = new SchemaManager();

        firstWeekOfMonth = new ArrayList<>();
        secondWeekOfMonth = new ArrayList<>();
        thirdWeekOfMonth = new ArrayList<>();
        lastWeekOfMonth = new ArrayList<>();
        weekNumbers = new ArrayList<>();
        weeksOfMonth = new ArrayList<>();
        weeksOfMonth.add(firstWeekOfMonth);
        weeksOfMonth.add(secondWeekOfMonth);
        weeksOfMonth.add(thirdWeekOfMonth);
        weeksOfMonth.add(lastWeekOfMonth);

        setMockDateRange();
        setCurrentMonth(startDate, endDate);

//        propertyStartDate = new SimpleStringProperty(startDate);
//        propertyEndDate = new SimpleStringProperty(endDate);
        //Zero for all weeks in month
        currentWeekOfMonth = 0;
    }

    public void setMockDateRange() {
        startDate = "2016-10-31";
        endDate = "2017-02-28";
    }

    public int getCurrentWeekOfMonthNumber() {
        return currentWeekOfMonth;
    }

    public void currentWeekOfMonthNumber(int currentWeekNumber) {
        this.currentWeekOfMonth = currentWeekNumber;
    }

    /**
     * Set the current
     *
     * @param startDate
     * @param endDate
     */
    public void setCurrentMonth(String startDate, String endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
        firstWeekOfMonth.clear();
        secondWeekOfMonth.clear();
        thirdWeekOfMonth.clear();
        lastWeekOfMonth.clear();
        weekNumbers.clear();

        SimpleDateFormat monthDayYear = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);

        try {
            Date start = monthDayYear.parse(startDate);
            Date end = monthDayYear.parse(endDate);

            int numberOfDays = 0;

            while (!start.after(end)) {
                numberOfDays++;
                start.setDate(start.getDate() + 1);
            }
            start = monthDayYear.parse(startDate);

            if (numberOfDays <= 5) {
                setFirstWeekOfMonth(start);
                currentWeekOfMonth = 1;
            } else if (numberOfDays <= 10) {
                setFirstWeekOfMonth(start);
                setSecondWeekOfMonth(start);
                currentWeekOfMonth = 2;
            } else if (numberOfDays <= 15) {
                setFirstWeekOfMonth(start);
                setSecondWeekOfMonth(start);
                setThirdWeekOfMonth(start);
                currentWeekOfMonth = 3;
            } else {
                Calendar firstDate = Calendar.getInstance();
                firstDate.setTime(end);
                firstDate.set(firstDate.get(Calendar.YEAR), firstDate.get(Calendar.MONTH), 1);
                start = firstDate.getTime();

                setFirstWeekOfMonth(start);
                setSecondWeekOfMonth(start);
                setThirdWeekOfMonth(start);
                setLastWeekOfMonth(start);
                currentWeekOfMonth = 0;
            }

        } catch (ParseException ex) {
            Logger.getLogger(SchemaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setLastWeekOfMonth(Date date) {
        Calendar first = getFirstDayInCurrentWeek(date, 3);

        Calendar last = getLastDateInCurrentWeek(first);

        while (!first.after(last)) {
            lastWeekOfMonth.add(first.getTime());
            first.add(Calendar.DATE, 1);
        }
    }

    private Calendar getLastDateInCurrentWeek(Calendar first) {
        Calendar last = Calendar.getInstance();
        last.setTime(schemaManager.getLastDateOfWeek(first));
        return last;
    }

    private void setThirdWeekOfMonth(Date date) {
        Calendar first = getFirstDayInCurrentWeek(date, 2);

        Calendar last = getLastDateInCurrentWeek(first);

        while (!first.after(last)) {
            thirdWeekOfMonth.add(first.getTime());
            first.add(Calendar.DATE, 1);
        }
    }

    private void setSecondWeekOfMonth(Date date) {
        Calendar first = getFirstDayInCurrentWeek(date, 1);

        Calendar last = getLastDateInCurrentWeek(first);

        while (!first.after(last)) {
            secondWeekOfMonth.add(first.getTime());
            first.add(Calendar.DATE, 1);
        }
    }

    private Calendar getFirstDayInCurrentWeek(Date date, int weeksToAdd) {
        Calendar first = Calendar.getInstance();
        first.setTime(schemaManager.getFirstDateOfWeek(date, weeksToAdd));
        weekNumbers.add(first.get(Calendar.WEEK_OF_YEAR));
        return first;
    }

    private void setFirstWeekOfMonth(Date date) {
        Calendar first = getFirstDayInCurrentWeek(date, 0);
        Calendar last = getLastDateInCurrentWeek(first);
        nameOfMonth = last.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        while (!first.after(last)) {
            firstWeekOfMonth.add(first.getTime());
            first.add(Calendar.DATE, 1);
        }
    }

    public List<Date> getFirstWeekOfMonth() {
        return firstWeekOfMonth;
    }

    public List<Date> getSecondWeekOfMonth() {
        return secondWeekOfMonth;
    }

    public List<Date> getThirdWeekOfMonth() {
        return thirdWeekOfMonth;
    }

    public List<Date> getLastWeekOfMonth() {
        return lastWeekOfMonth;
    }

    public List<List<Date>> getWeeksOfMonth() {
        return weeksOfMonth;
    }

    public List<Integer> getWeekNumbers() {
        return weekNumbers;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
        propertyStartDate.setValue(startDate);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
        propertyEndDate.setValue(endDate);
    }

    public String getNameOfMonth() {
        return nameOfMonth;
    }

    public StringProperty getPropertyStartDate() {
        return propertyStartDate;
    }

    public StringProperty getPropertyEndDate() {
        return propertyEndDate;
    }
}
