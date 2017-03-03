/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

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

    public SchoolClass getAllSchoolClassDataForSpecificSchoolClass(int id) {
        return AADAOFacade.getSchoolClassDataForSpecificSchoolClass(id);
    }

}
