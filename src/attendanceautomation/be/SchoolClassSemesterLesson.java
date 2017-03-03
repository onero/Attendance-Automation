/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.Date;

public class SchoolClassSemesterLesson {

    private final int ID;

    private final SchoolSemesterSubject semesterSubject;

    private final Date date;

    public SchoolClassSemesterLesson(int id, SchoolSemesterSubject schoolClassSemesterSubject, Date date) {
        ID = id;
        semesterSubject = schoolClassSemesterSubject;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public SchoolSemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public Date getDate() {
        return date;
    }

}
