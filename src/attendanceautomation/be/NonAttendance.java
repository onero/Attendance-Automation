/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

public class NonAttendance {

    private final SchoolWeek weekWithoutAttendance;

    private final SchoolDay dayWithoutAttendance;

    private final SchoolLesson lessonWithoutAttendance;

    public NonAttendance(SchoolWeek weekWithoutAttendance, SchoolDay dayWithoutAttendance, SchoolLesson lessonWithoutAttendance) {
        this.weekWithoutAttendance = weekWithoutAttendance;
        this.dayWithoutAttendance = dayWithoutAttendance;
        this.lessonWithoutAttendance = lessonWithoutAttendance;
    }

    public SchoolWeek getWeekWithoutAttendance() {
        return weekWithoutAttendance;
    }

    public SchoolDay getDayWithoutAttendance() {
        return dayWithoutAttendance;
    }

    public SchoolLesson getLessonWithoutAttendance() {
        return lessonWithoutAttendance;
    }

}
