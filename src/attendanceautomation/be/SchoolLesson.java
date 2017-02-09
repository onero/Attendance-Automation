/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.HashMap;

public class SchoolLesson {

    private HashMap<ESchoolSubject, ETeacher> lesson;

    public SchoolLesson(ESchoolSubject subject, ETeacher teacher) {
        lesson.put(subject, teacher);
    }

    /**
     *
     * @return lesson
     */
    public HashMap<ESchoolSubject, ETeacher> getLesson() {
        return lesson;
    }

}
