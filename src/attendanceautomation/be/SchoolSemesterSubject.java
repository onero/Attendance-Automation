/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.be.enums.ESemester;
import attendanceautomation.be.enums.ETeacher;

public class SchoolSemesterSubject {

    private final int ID;

    private final SchoolClass schoolClass;

    private final ESemester semester;

    private final ESchoolSubject subject;

    private final ETeacher teacher;

    public SchoolSemesterSubject(int id, SchoolClass newSchoolClass, ESemester newSemester, ESchoolSubject newSubject, ETeacher newTeacher) {
        ID = id;
        schoolClass = newSchoolClass;
        semester = newSemester;
        subject = newSubject;
        teacher = newTeacher;
    }

    public int getID() {
        return ID;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public ESemester getSemester() {
        return semester;
    }

    public ESchoolSubject getSubject() {
        return subject;
    }

    public ETeacher getTeacher() {
        return teacher;
    }

}
