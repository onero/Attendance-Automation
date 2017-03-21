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

public class SchemaModel {

    private static SchemaModel instance;

    private final SchemaManager schemaManager;

    public static SchemaModel getInstance() {
        if (instance == null) {
            instance = new SchemaModel();
        }
        return instance;
    }

    private Calendar currentMonth;

    private final List<Date> firstWeekOfMonth;
    private final List<Date> secondWeekOfMonth;
    private final List<Date> thirdWeekOfMonth;
    private final List<Date> lastWeekOfMonth;
    private final List<List<Date>> weeksOfMonth;

    private final List<Integer> weekNumbers;

    private int currentWeekOfMonth;

    private SchemaModel() {
        schemaManager = SchemaManager.getInstance();

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

        String month = "2016/11/01";
        setCurrentMonth(month);

        //Zero for all weeks in month
        currentWeekOfMonth = 0;
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
     * @param month
     */
    public void setCurrentMonth(String month) {
        SimpleDateFormat monthDayYear = new SimpleDateFormat("yyyy/MM/dd", Locale.GERMANY);
        try {
            Date date = monthDayYear.parse(month);

            setFirstWeekOfMonth(date);

            setSecondWeekOfMonth(date);

            setThirdWeekOfMonth(date);

            setLastWeekOfMonth(date);
        } catch (ParseException ex) {
            Logger.getLogger(SchemaModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setLastWeekOfMonth(Date date) {
        Calendar last = Calendar.getInstance();
        last.setTime(date);
        last.add(Calendar.WEEK_OF_MONTH, 3);
        last.setWeekDate(last.get(Calendar.YEAR), last.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
        weekNumbers.add(last.get(Calendar.WEEK_OF_YEAR));

        Calendar lastOfLast = Calendar.getInstance();
        lastOfLast.setTime(last.getTime());
        lastOfLast.setWeekDate(lastOfLast.get(Calendar.YEAR), lastOfLast.get(Calendar.WEEK_OF_YEAR), Calendar.FRIDAY);

        while (!last.after(lastOfLast)) {
            lastWeekOfMonth.add(last.getTime());
            last.add(Calendar.DATE, 1);
        }
    }

    private void setThirdWeekOfMonth(Date date) {
        Calendar third = Calendar.getInstance();
        third.setTime(date);
        third.add(Calendar.WEEK_OF_MONTH, 2);
        third.setWeekDate(third.get(Calendar.YEAR), third.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
        weekNumbers.add(third.get(Calendar.WEEK_OF_YEAR));

        Calendar lastOfThird = Calendar.getInstance();
        lastOfThird.setTime(third.getTime());
        lastOfThird.setWeekDate(lastOfThird.get(Calendar.YEAR), lastOfThird.get(Calendar.WEEK_OF_YEAR), Calendar.FRIDAY);

        while (!third.after(lastOfThird)) {
            thirdWeekOfMonth.add(third.getTime());
            third.add(Calendar.DATE, 1);
        }
    }

    private void setSecondWeekOfMonth(Date date) {
        Calendar second = Calendar.getInstance();
        second.setTime(date);
        second.add(Calendar.WEEK_OF_MONTH, 1);
        second.setWeekDate(second.get(Calendar.YEAR), second.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
        weekNumbers.add(second.get(Calendar.WEEK_OF_YEAR));

        Calendar lastOfSecond = Calendar.getInstance();
        lastOfSecond.setTime(second.getTime());
        lastOfSecond.setWeekDate(lastOfSecond.get(Calendar.YEAR), lastOfSecond.get(Calendar.WEEK_OF_YEAR), Calendar.FRIDAY);

        while (!second.after(lastOfSecond)) {
            secondWeekOfMonth.add(second.getTime());
            second.add(Calendar.DATE, 1);
        }
    }

    private void setFirstWeekOfMonth(Date date) {
        Calendar first = Calendar.getInstance();
        first.setTime(date);
        first.setWeekDate(first.get(Calendar.YEAR), first.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
        weekNumbers.add(first.get(Calendar.WEEK_OF_YEAR));

        Calendar lastOfFirst = Calendar.getInstance();
        lastOfFirst.setTime(first.getTime());
        lastOfFirst.setWeekDate(lastOfFirst.get(Calendar.YEAR), lastOfFirst.get(Calendar.WEEK_OF_YEAR), Calendar.FRIDAY);

        while (!first.after(lastOfFirst)) {
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

}
