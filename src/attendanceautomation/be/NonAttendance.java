/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

public class NonAttendance {

    private final SchoolClassSemesterLesson schoolClassSemesterLesson;

    private final int studentID;

    public NonAttendance(SchoolClassSemesterLesson newLesson, int newStudentID) {
        schoolClassSemesterLesson = newLesson;
        studentID = newStudentID;
    }

    public SchoolClassSemesterLesson getSchoolClassSemesterLesson() {
        return schoolClassSemesterLesson;
    }

    public int getStudentID() {
        return studentID;
    }

}
