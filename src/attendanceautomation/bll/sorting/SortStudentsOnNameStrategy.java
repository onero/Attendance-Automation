/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll.sorting;

import attendanceautomation.be.Student;
import java.util.Collections;
import java.util.List;

public class SortStudentsOnNameStrategy implements ISortStrategy<Student> {

    @Override
    public void sort(List<Student> data) {
        Collections.sort(data);
    }

}
