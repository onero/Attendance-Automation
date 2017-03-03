/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.be.Student;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;
import java.util.List;

public class SchoolClassManager {

    private static SchoolClassManager instance;

    private final AttendanceAutomationDAOFacade AADAOFacade;

    public static SchoolClassManager getInstance() {
        if (instance == null) {
            instance = new SchoolClassManager();
        }
        return instance;
    }

    private SchoolClassManager() {
        AADAOFacade = AttendanceAutomationDAOFacade.getInstance();
    }

    /**
     * Get the updated studentInfo from DB
     *
     * @return
     */
    public List<Student> getUpdatedStudents() {
        return AADAOFacade.getUpdatedStudentInfo();
    }

    /**
     * Get all SchoolClass data
     *
     * @param id
     * @return
     */
    public SchoolClass getAllSchoolClassDataForSpecificSchoolClass(int id) {
        return AADAOFacade.getSchoolClassDataForSpecificSchoolClass(id);
    }

    /**
     * Update attendance for student
     *
     * @param newNonAttendance
     */
    public void addNewAttendance(NonAttendance newNonAttendance) {
        AADAOFacade.addNewNonAttendance(newNonAttendance);
    }

    /**
     * Update attendance for student
     *
     * @param newNonAttendance
     */
    public void removeNonAttendance(NonAttendance newNonAttendance) {
        AADAOFacade.removeNonAttendanceFromSpecificStudent(newNonAttendance);
    }

}
