/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.gui.model.SchoolClassModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SchemaManager {

    private static SchemaManager instance;

    public static SchemaManager getInstance() {
        if (instance == null) {
            instance = new SchemaManager();
        }
        return instance;
    }

    /**
     * Get all dates from lessons
     *
     * @return
     */
    public List<Calendar> getAllLessonDates() {
        List<Calendar> dates = new ArrayList<>();
        List<SchoolClassSemesterLesson> lessons = SchoolClassModel.getInstance().getCurrentSchoolClass().getSemesterLessons();

        for (SchoolClassSemesterLesson lesson : lessons) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lesson.getDate());
            dates.add(calendar);
        }
        return dates;
    }

    public Date getFirstDateOfWeek(Calendar monthWeek) {
        Calendar first = Calendar.getInstance();
        first.add(Calendar.YEAR, monthWeek.get(Calendar.YEAR));
        first.add(Calendar.MONTH, monthWeek.get(Calendar.MONTH));
        first.add(Calendar.WEEK_OF_MONTH, monthWeek.get(Calendar.WEEK_OF_MONTH));
        first.setWeekDate(first.get(Calendar.YEAR), first.get(Calendar.WEEK_OF_YEAR), Calendar.MONDAY);
        return first.getTime();
    }

    public Date getLastDateOfWeek(Calendar monthWeek) {
        Calendar last = Calendar.getInstance();
        last.add(Calendar.YEAR, monthWeek.get(Calendar.YEAR));
        last.add(Calendar.MONTH, monthWeek.get(Calendar.MONTH));
        last.add(Calendar.WEEK_OF_MONTH, monthWeek.get(Calendar.WEEK_OF_MONTH));
        last.setWeekDate(last.get(Calendar.YEAR), last.get(Calendar.WEEK_OF_YEAR), Calendar.FRIDAY);
        return last.getTime();
    }

}
