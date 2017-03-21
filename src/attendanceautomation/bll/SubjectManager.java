/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.ESchoolSubject;
import java.util.ArrayList;

/**
 *
 * @author Skovgaard
 */
public class SubjectManager {

    public void totalSubjectsOfSCO(Student student) {

        ArrayList SCO = new ArrayList<>();

        for (NonAttendance lessons : student.getNonAttendance()) {

            if (lessons.getSchoolClassSemesterLesson().getSemesterSubject().getSubject().equals(ESchoolSubject.SCO)) {
                SCO.add(lessons.getSchoolClassSemesterLesson().getSemesterSubject());

                System.out.println(lessons.getSchoolClassSemesterLesson().getSemesterSubject().getSubject().toString());

            }
        }
    }
    public void absenceInSCO(Student student){
        
    }

}
