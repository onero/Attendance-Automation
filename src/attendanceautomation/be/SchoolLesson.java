/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.Date;

public class SchoolLesson {

    private final SchoolSemesterSubject semesterSubject;

    private final Date lessonDate;

    public SchoolLesson(SchoolSemesterSubject semesterSubject, Date lessonDate) {
        this.semesterSubject = semesterSubject;
        this.lessonDate = lessonDate;
    }

    public SchoolSemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public Date getLessonDate() {
        return lessonDate;
    }

}
