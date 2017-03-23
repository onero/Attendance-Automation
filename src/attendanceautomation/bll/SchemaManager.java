/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import java.util.Calendar;
import java.util.Date;

public class SchemaManager {

    public Date getFirstDateOfWeek(Date date, int weeksToAdd) {
        Calendar first = Calendar.getInstance();
        first.setTime(date);
        first.add(Calendar.WEEK_OF_MONTH, weeksToAdd);
        first.setWeekDate(first.get(Calendar.YEAR), first.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);

        return first.getTime();
    }

    public Date getLastDateOfWeek(Calendar first) {
        Calendar lastOfFirst = Calendar.getInstance();
        lastOfFirst.setTime(first.getTime());
        lastOfFirst.setWeekDate(lastOfFirst.get(Calendar.YEAR), lastOfFirst.get(Calendar.WEEK_OF_YEAR), Calendar.FRIDAY);

        return lastOfFirst.getTime();
    }

}
