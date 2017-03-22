/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClassSemesterLesson;
import attendanceautomation.be.Student;
import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.gui.model.SchoolClassModel;
import java.util.ArrayList;

/**
 *
 * @author Skovgaard
 */
public class SubjectManager {

    public double totalSubjectsOfSCO(Student student, ESchoolSubject subject) {


        ArrayList absenceOfSubjects = new ArrayList();
        ArrayList amountOfSubjects = new ArrayList();
        
        
        for (NonAttendance lessons : student.getNonAttendance()) {
            
            
            
            if (lessons.getSchoolClassSemesterLesson().getSemesterSubject().getSubject().toString().equals(subject.toString())) {
                absenceOfSubjects.add(lessons.getSchoolClassSemesterLesson().getSemesterSubject());
//                System.out.println(subject.toString());
//                System.out.println("Number of absence in SCO: " + absenceOfSubjects.size());
            }
        }
        for (SchoolClassSemesterLesson absenceOfSubject : SchoolClassModel.getInstance().getCurrentSchoolClass().getSemesterLessons()) {
            if (absenceOfSubject.getSemesterSubject().getSubject().toString().equals(subject.toString())) {
                amountOfSubjects.add(absenceOfSubject.getSemesterSubject().getSubject());
            }
        }
        double studentAbsence = absenceOfSubjects.size();
        System.out.println(studentAbsence);
        double subjectAmout = amountOfSubjects.size();
        System.out.println(subjectAmout);
        double total = (studentAbsence / subjectAmout) * 100.0;
        
        System.out.println(total);
        
        return total;
    }
    
    

}
