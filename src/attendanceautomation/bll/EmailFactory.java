/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

public class EmailFactory {

    private static String email;

    private static final String EMAIL_DOMAIN = "@easv.dk";

    /**
     * Create a new email combined of firstName+ID+emailDomain
     *
     * @param ID
     * @param firstName
     */
    private static void createNewEmail(int ID, String firstName) {
        email = firstName + ID + EMAIL_DOMAIN;
    }

    /**
     * Create a new SchoolEmail for a student
     *
     * @param ID
     * @param firstName
     * @return
     */
    public static String getnewEmail(int ID, String firstName) {
        createNewEmail(ID, firstName);
        return email;
    }

}
