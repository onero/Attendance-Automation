/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import attendanceautomation.be.enums.ESchoolSubject;
import attendanceautomation.be.enums.ETeacher;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class SchoolLesson {

    private final List<java.util.Map.Entry<ESchoolSubject, ETeacher>> lesson;

    public SchoolLesson(ESchoolSubject schoolSubject, ETeacher teacher) {
        lesson = new ArrayList<>();
        Entry<ESchoolSubject, ETeacher> newLesson = new SimpleEntry<>(schoolSubject, teacher);
        lesson.add(newLesson);
    }

    /**
     *
     * @return subject
     */
    public ESchoolSubject getSubject() {
        return lesson.get(0).getKey();
    }

    /**
     *
     * @return teacher
     */
    public ETeacher getTeacher() {
        return lesson.get(0).getValue();
    }

}
