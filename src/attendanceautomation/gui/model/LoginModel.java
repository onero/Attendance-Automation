/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

/**
 *
 * @author Mathias
 */
public class LoginModel {
    
    private static LoginModel instance;
    
    private final String TEACHER_PASSWORD = "teacher";
    private final String STUDENT_PASSWORD = "student";
    private final String STUDENT_USERNAME = "teacheruser";
    private final String TEACHER_USERNAME = "studentuser";
    
    public static LoginModel getInstance() {
        if (instance == null) {
            instance = new LoginModel();
        }
        return instance;
    }
    
    /**
     * Contacts the LoginManager by sending the  and verifies the user.
     * @param username
     * @param password
     * @return 
     */
    public boolean verifyLogin(String username, String password) {
        return true;
    }
    
    public boolean isUserStudent(String username) {
        return // matches the username with a student via the loginmanager.
                username.equals(STUDENT_USERNAME);
    }
}
