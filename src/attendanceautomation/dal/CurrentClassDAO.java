/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.dal;

import attendanceautomation.be.Student;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rasmus
 */
public class CurrentClassDAO {

    private List<Student> mockQuaterToNineYes;
    private List<Student> mockQuaterToNineNo;

    private List<Student> mockNineOClockYes;

    private List<Student> mockQuaterAfterNinceNo;

    public CurrentClassDAO() {
        mockQuaterToNineYes = fillQuaterToNineYes();
        mockQuaterToNineNo = fillQuaterToNineNo();

        mockNineOClockYes = fillNineOClockYes();
        mockQuaterAfterNinceNo = fillQuaterAfterNineNo();
    }

    /**
     * Creates list of students present before Nine.
     *
     * @return
     */
    private List<Student> fillQuaterToNineYes() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "Rasmus", "Lindved", ""));
        list.add(new Student(2, "Adam", "Hansen", ""));
        list.add(new Student(3, "Bo", "Sinclair", ""));
        list.add(new Student(4, "Casper", "Rødgaard", ""));
        list.add(new Student(5, "Emil", "Johansen", ""));
        list.add(new Student(6, "Frederik", "Dyrberg", ""));
        return list;
    }

    /**
     * Creates a list of students present at Nine.
     *
     * @return
     */
    private List<Student> fillNineOClockYes() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "Rasmus", "Lindved", ""));
        list.add(new Student(2, "Adam", "Hansen", ""));
        list.add(new Student(3, "Bo", "Sinclair", ""));
        list.add(new Student(4, "Casper", "Rødgaard", ""));
        list.add(new Student(5, "Emil", "Johansen", ""));
        list.add(new Student(6, "Frederik", "Dyrberg", ""));
        list.add(new Student(7, "Jacob", "Enemark", ""));
        list.add(new Student(8, "Kenneth", "Sørensen", ""));
        list.add(new Student(9, "Mads", "Lorentsen", ""));
        list.add(new Student(10, "Mathias", "Plougmann", ""));
        list.add(new Student(11, "Mickei", "Jensen", ""));
        list.add(new Student(12, "Nicolai", "Larsen", ""));
        list.add(new Student(13, "Simon", "Hansen", ""));
        list.add(new Student(14, "Stefan", "Olsen", ""));
        list.add(new Student(15, "Stephan", "FuhlenDorff", ""));
        return list;
    }

    /**
     * Creates a list of all the students not present.
     *
     * @return
     */
    private List<Student> fillQuaterAfterNineNo() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(1, "Casper", "Jensen", ""));
        list.add(new Student(2, "Jens", "Nissen", ""));
        list.add(new Student(3, "Joan", "Christensen", ""));
        list.add(new Student(4, "Lukas", "Larsen", ""));
        list.add(new Student(5, "Mathias", "Skovgaard", ""));
        list.add(new Student(6, "Michael", "Ibsen", ""));
        list.add(new Student(7, "Miklas", "Kruchov", ""));
        list.add(new Student(8, "Patrick", "Hansen", ""));
        list.add(new Student(9, "Simon", "Birkedal", ""));
        list.add(new Student(10, "Stephan", "Rosengreen", ""));
        list.add(new Student(11, "Thomas", "Hansen", ""));
        return list;
    }

    /**
     * Mock data for currentClass.
     *
     * @param mockSwitch
     * @return
     */
    public List<Student> findMockStudents(int mockSwitch) {
        switch (mockSwitch) {
            case 1: {
                return mockQuaterToNineYes;
            }
            case 2: {
                return mockNineOClockYes;
            }
            case 3: {
                return mockQuaterAfterNinceNo;
            }
            case 4: {
                return mockQuaterToNineNo;
            }
            default: {
                return null;
            }
        }
    }

    private List<Student> fillQuaterToNineNo() {
        List<Student> list = new ArrayList<>();
        list.add(new Student(7, "Jacob", "Enemark", ""));
        list.add(new Student(8, "Kenneth", "Sørensen", ""));
        list.add(new Student(9, "Mads", "Lorentsen", ""));
        list.add(new Student(10, "Mathias", "Plougmann", ""));
        list.add(new Student(11, "Mickei", "Jensen", ""));
        list.add(new Student(12, "Nicolai", "Larsen", ""));
        list.add(new Student(13, "Simon", "Hansen", ""));
        list.add(new Student(14, "Stefan", "Olsen", ""));
        list.add(new Student(15, "Stephan", "FuhlenDorff", ""));
        list.add(new Student(1, "Casper", "Jensen", ""));
        list.add(new Student(2, "Jens", "Nissen", ""));
        list.add(new Student(3, "Joan", "Christensen", ""));
        list.add(new Student(4, "Lukas", "Larsen", ""));
        list.add(new Student(5, "Mathias", "Skovgaard", ""));
        list.add(new Student(6, "Michael", "Ibsen", ""));
        list.add(new Student(7, "Miklas", "Kruchov", ""));
        list.add(new Student(8, "Patrick", "Hansen", ""));
        list.add(new Student(9, "Simon", "Birkedal", ""));
        list.add(new Student(10, "Stephan", "Rosengreen", ""));
        list.add(new Student(11, "Thomas", "Hansen", ""));
        return list;
    }
}
