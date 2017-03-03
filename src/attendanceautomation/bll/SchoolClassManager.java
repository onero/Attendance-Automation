/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.SchoolClass;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;

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
    public void saveNewAttendance(NonAttendance newNonAttendance) {
        AADAOFacade.saveNewNonAttendance(newNonAttendance);
    }

    /**
     * Update attendance for student
     *
     * @param newNonAttendance
     */
    public void removeNonAttendance(NonAttendance newNonAttendance) {
        AADAOFacade.removeNonAttendanceFromSpecificStudent(newNonAttendance);
    }

    /**
     * Update student attendance
     *
     * @param studentID
     */
    public void updateStudentAttendance(int studentID) {

    }

}
