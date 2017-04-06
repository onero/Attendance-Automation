/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.dal.LoginDAO;
import attendanceautomation.gui.model.SchoolClassModel;
import java.sql.SQLException;

/**
 *
 * @author Rasmus
 */
public class LoginManager {

    private static LoginManager instance;

    private final LoginDAO loginDAO;

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    private LoginManager() {
        loginDAO = LoginDAO.getInstance();
    }

    /**
     * Gets a list of students from LoginDAO and checks if the parsed user
     * exist.
     *
     * @param userEmail
     * @return
     */
    public boolean checkIfUserExist(String userEmail) throws SQLException {
        return SchoolClassModel.getInstance().isUserInDB(userEmail);
    }

    /**
     * Check if the user is a teacher
     *
     * @param userName
     * @return
     */
    public boolean checkIfUserIsTeacher(String userName) {
        return SchoolClassManager.getInstance().isTeacher(userName);
    }

    /**
     * Valiate student password
     *
     * @param password
     * @return
     */
    public boolean validateStudentPassword(String password) {
        return password.equals(loginDAO.getMockStudentPassword());
    }

    /**
     * Valiate teacher password
     *
     * @param password
     * @return
     */
    public boolean validateTeacherPassword(String password) {
        return password.equals(loginDAO.getMockTeacherPassword());
    }
}
