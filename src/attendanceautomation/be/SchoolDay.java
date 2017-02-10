/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.be.enums.ESchoolDayName;
import java.util.ArrayList;

public class SchoolDay {

    private final ESchoolDayName schoolDayName;

    private ArrayList<SchoolLesson> lessonsInSchoolDay;

    public SchoolDay(ESchoolDayName day) {
        lessonsInSchoolDay = new ArrayList<>();
        schoolDayName = day;
    }

    /**
     *
     * @return schoolday
     */
    public ESchoolDayName getSchoolDayName() {
        return schoolDayName;
    }

    /**
     *
     * @return school lessons
     */
    public ArrayList<SchoolLesson> getLessons() {
        return lessonsInSchoolDay;
    }

    /**
     * Add a lesson to the schoolday
     *
     * @param lesson
     */
    public void addLesson(SchoolLesson lesson) {
        lessonsInSchoolDay.add(lesson);
    }

}
