/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.Date;

public class NonAttendance {

    private final Date date;

    public NonAttendance(Date date) {
        this.date = date;
    }

    /**
     *
     * @return date
     */
    public Date getDate() {
        return date;
    }

}
