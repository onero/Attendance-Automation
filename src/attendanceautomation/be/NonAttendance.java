/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

public class NonAttendance {

    private final SchoolSemesterSubject semesterSubject;

    private final int studentID;

    public NonAttendance(SchoolSemesterSubject newSubject, int newStudentID) {
        semesterSubject = newSubject;
        studentID = newStudentID;
    }

    public SchoolSemesterSubject getSemesterSubject() {
        return semesterSubject;
    }

    public int getStudentID() {
        return studentID;
    }

}
