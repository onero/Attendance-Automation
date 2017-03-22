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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Generates an ArrayList of lessons. Calcs the absence of a specific subject in
 * procentage.
 *
 * @author Skovgaard
 */
public class SubjectManager {

    DecimalFormat df = new DecimalFormat("#.##");

    public double subjectAbsenceCalculation(Student student, ESchoolSubject subject) {

        ArrayList absenceOfSubjects = new ArrayList();
        ArrayList amountOfSubjects = new ArrayList();

        // For each subject.
        for (NonAttendance lessonsOfOneSubject : student.getNonAttendance()) {
            // there is equals to the name of the enum subject. (made in toStrings.)
            if (lessonsOfOneSubject.getSchoolClassSemesterLesson().getSemesterSubject().getSubject().toString().equals(subject.toString())) {
                absenceOfSubjects.add(lessonsOfOneSubject.getSchoolClassSemesterLesson().getSemesterSubject());
                // Add the subjects to the absenceOfSubjects list.
            }
        }
        // For each subject.
        for (SchoolClassSemesterLesson totalLessons : SchoolClassModel.getInstance().getCurrentSchoolClass().getSemesterLessons()) {
            // there is equals to the name of the enum subject. (made in toStrings.)
            if (totalLessons.getSemesterSubject().getSubject().toString().equals(subject.toString())) {
                // Add the subject to the amountOfSubjects list.
                amountOfSubjects.add(totalLessons.getSemesterSubject().getSubject());
            }
        }
        //Makes it to be a double.
        double studentAbsence = absenceOfSubjects.size();
        double subjectAmount = amountOfSubjects.size();
        double total = (studentAbsence / subjectAmount) * 100.0;

        total = decimalFormatter(total);

        return total;
    }

    /**
     * Formats a double with two decimals.
     *
     * @param total
     * @return
     */
    private double decimalFormatter(double total) {
        String percentFormatted = df.format(total);
        try {
            //Parse formatted string to double
            total = df.parse(percentFormatted).doubleValue();
        } catch (ParseException ex) {
            System.out.println("Cannot convert number " + ex);
        }
        return total;
    }

}
