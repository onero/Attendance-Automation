/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.Student;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rasmus
 */
public class CurrentClassManager {

    private final AttendanceAutomationDAOFacade daoFacade;
    private final SchoolClassManager schoolClassManager;

    public CurrentClassManager() {
        daoFacade = AttendanceAutomationDAOFacade.getInstance();
        schoolClassManager = SchoolClassManager.getInstance();
    }

    /**
     * Returns a list of students from the current schoolClass off the current
     * day.
     *
     * @param teacherID
     * @return
     */
    public List<Student> getStudentsFromCurrentSchoolClass(int teacherID) {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43

        String date = "2017-02-06 09:00";

        int schoolClassID = daoFacade.getSchoolClassID(teacherID, date);
        if (schoolClassID == 0) {
            System.out.println("Failed to get schoolClassID");
        }
        return schoolClassManager.getStudentsWithDataFromSchoolClass(schoolClassID);
    }

    /**
     * Search the parsed list of students to see if they were absence.
     *
     * @param listOfCurrentClassStudents
     * @return
     */
    public List<Student> findStudentsAbsence(List<Student> listOfCurrentClassStudents) {
        List<Student> listOfStudentsAbsence = new ArrayList<>();
        for (int i = 0; i < listOfCurrentClassStudents.size(); i++) {
            ArrayList<NonAttendance> listOfNonAttendence = listOfCurrentClassStudents.get(i).getNonAttendance();

            if (!listOfNonAttendence.isEmpty()) {
                listOfStudentsAbsence.add(listOfCurrentClassStudents.get(i));
            }
        }
        return listOfStudentsAbsence;
    }

    /**
     * HUGE VIOLATION OF DRY!!!!!!! TODO RKL: Refactor to no longer violate DRY.
     *
     * @param listOfCurrentClassStudents
     * @return
     */
    public List<Student> findStudentsPresent(List<Student> listOfCurrentClassStudents) {
        List<Student> listOfStudentsPresent = new ArrayList<>();
        for (int i = 0; i < listOfCurrentClassStudents.size(); i++) {
            ArrayList<NonAttendance> listOfNonAttendence = listOfCurrentClassStudents.get(i).getNonAttendance();
            if (listOfNonAttendence.isEmpty()) {
                listOfStudentsPresent.add(listOfCurrentClassStudents.get(i));
            }
        }
        return listOfStudentsPresent;
    }
}
