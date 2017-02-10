/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.be.enums.ETeacher;
import java.util.HashMap;

public class SchoolLesson {

    private final HashMap<ESchoolSubject, ETeacher> lesson;

    private boolean isAttendend;

    public SchoolLesson(ESchoolSubject subject, ETeacher teacher) {
        lesson = new HashMap<>();
        lesson.put(subject, teacher);
        isAttendend = true;
    }

    /**
     *
     * @return isAttended
     */
    public boolean getIsAttendend() {
        return isAttendend;
    }

    /**
     * Update the attended status
     *
     * @param isAttendend
     */
    public void setIsAttendend(boolean isAttendend) {
        this.isAttendend = isAttendend;
    }

    /**
     *
     * @return lesson
     */
    public HashMap<ESchoolSubject, ETeacher> getLesson() {
        return lesson;
    }

}
