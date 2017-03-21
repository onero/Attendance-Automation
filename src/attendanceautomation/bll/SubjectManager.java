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

    public double totalSubjectsOfSCO(Student student, ESchoolSubject subject) {

        ArrayList allSubjectLessonsList = new ArrayList();
        ArrayList subjectListToBeSorted = new ArrayList();
        
        
        for (NonAttendance lessons : student.getNonAttendance()) {
            
            
            
            if (lessons.getSchoolClassSemesterLesson().getSemesterSubject().getSubject().toString().equals(subject.toString())) {
                subjectListToBeSorted.add(lessons.getSchoolClassSemesterLesson().getSemesterSubject());
                System.out.println(subject.toString());
                System.out.println("Number of absence in SCO: " +subjectListToBeSorted.size());
            }
        }
        
        return subjectListToBeSorted.size() / 1 * 100;
    }
    

}
