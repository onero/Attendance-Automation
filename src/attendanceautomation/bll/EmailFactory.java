/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

public class EmailFactory {

    private static String email;

    private static void createNewEmail(int ID, String firstName) {
        email = firstName + ID + "@easv.dk";
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
