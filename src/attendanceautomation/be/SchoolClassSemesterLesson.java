/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.Date;

public class SchoolClassSemesterLesson {

    private final int ID;

    private final int schoolClassSemesterSubjectID;

    private final Date date;

    public SchoolClassSemesterLesson(int id, int schoolClassSemesterSubjectID, Date date) {
        ID = id;
        this.schoolClassSemesterSubjectID = schoolClassSemesterSubjectID;
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public int getSchoolClassSemesterSubjectID() {
        return schoolClassSemesterSubjectID;
    }

    public Date getDate() {
        return date;
    }

}
