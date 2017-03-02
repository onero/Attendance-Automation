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
    
    public static LoginModel getInstance() {
        if (instance == null) {
            instance = new LoginModel();
        }
        return instance;
    }
    
    /**
     * Contacts the LoginManager by sending the  and verifies the user.
     * @return 
     */
    public boolean verifyLogin(String username, String password) {
        //Temporarily solved by only checking the password
//        if (password.matches(TEACHER_PASSWORD) || password.matches(STUDENT_PASSWORD)) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }
}
