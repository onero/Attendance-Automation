/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

public class IDFactory {

    private static int currentID = 0;

    private static void increaseCurrentID() {
        currentID++;
    }

    /**
     *
     * @return a new ID
     */
    public static int getNewID() {
        increaseCurrentID();
        return currentID;
    }

}
