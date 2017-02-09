/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.ArrayList;

public class SchoolWeek {

    private final ESchoolWeekNumber weekNumber;

    private ArrayList<SchoolDay> weekSchoolDays;

    public SchoolWeek(ESchoolWeekNumber number) {
        weekNumber = number;
    }

    /**
     *
     * @return SchoolDay
     */
    public ArrayList<SchoolDay> getSchoolDays() {
        return weekSchoolDays;
    }

    /**
     *
     * @param schoolDays
     */
    public void setSchoolDays(ArrayList<SchoolDay> schoolDays) {
        weekSchoolDays = schoolDays;
    }

}
