/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.Student;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;
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

        String date = "2017-02-06";

        int schoolClassID = daoFacade.getSchoolClassID(teacherID, date);
        return schoolClassManager.getStudentsWithDataFromSchoolClass(schoolClassID);
    }
}
