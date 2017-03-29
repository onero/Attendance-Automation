/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import attendanceautomation.be.NonAttendance;
import attendanceautomation.be.Student;
import attendanceautomation.dal.AttendanceAutomationDAOFacade;
import attendanceautomation.dal.CurrentClassDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rasmus
 */
public class CurrentClassManager {

    private final AttendanceAutomationDAOFacade daoFacade;
    private final SchoolClassManager schoolClassManager;
    private final CurrentClassDAO currentClassDAO;

    public CurrentClassManager() {
        daoFacade = AttendanceAutomationDAOFacade.getInstance();
        schoolClassManager = SchoolClassManager.getInstance();
        currentClassDAO = new CurrentClassDAO(); //This is for mock data.
    }

    /**
     * Returns a list of students from the current schoolClass off the current
     * day.
     *
     * @param teacherID
     * @return
     */
    public List<Student> getStudentsFromCurrentSchoolClass(int teacherID) {
        DateFormat dateFormatTime = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String dateAsString = dateFormatTime.format(date);
        String[] hoursAndMinutes = dateAsString.split(":");
        String hours, minutes;
        hours = hoursAndMinutes[0];
        minutes = hoursAndMinutes[1];

        String mockDate = "2017-02-24";
//        //These lines are for when the actual date is needed.
//        DateFormat dateFormatYear = new SimpleDateFormat("yyyy-MM-dd");
//        String yearAsString = dateFormatYear.format(date);

        String eigthHoursBefore = mockDate + " " + findEitghHoursBefore(Integer.parseInt(hours), Integer.parseInt(minutes));
        String halfHourAfter = mockDate + " " + findHalfHourAfter(Integer.parseInt(hours), Integer.parseInt(minutes));

        List<Integer> schoolClassID = daoFacade.getSchoolClassID(teacherID, eigthHoursBefore, halfHourAfter);
        if (schoolClassID.isEmpty()) {
            System.out.println("Failed to get schoolClassID");
            return new ArrayList<Student>();
        }
        return schoolClassManager.getStudentsWithDataFromSchoolClassForSpecificDate(schoolClassID.get(schoolClassID.size() - 1), mockDate);
    }

    /**
     * Search the parsed list of students to see if they were absent or present
     * depending on the parsed boolean. True if looking for present. False if
     * looking for absent.
     *
     * @param listOfCurrentClassStudents
     * @param empty
     * @return
     */
    public List<Student> findStudentsAbsentOrPresent(List<Student> listOfCurrentClassStudents, boolean empty) {
        List<Student> listOfStudents = new ArrayList<>();
        for (int i = 0; i < listOfCurrentClassStudents.size(); i++) {
            ArrayList<NonAttendance> listOfNonAttendence = listOfCurrentClassStudents.get(i).getNonAttendance();

            if (listOfNonAttendence.isEmpty() == empty) {
                listOfStudents.add(listOfCurrentClassStudents.get(i));
            }
        }
        return listOfStudents;
    }

    /**
     * Calls the dao to find the mockStudent for currentClassView.
     *
     * @param mockSwitch
     * @return
     */
    public List<Student> findMockStudents(int mockSwitch) {
        return currentClassDAO.findMockStudents(mockSwitch);
    }

    /**
     * Takes the parsed time and converts it to a string that is half an hour
     * behind time.
     *
     * @param hours
     * @param minutes
     * @return
     */
    private String findEitghHoursBefore(int hours, int minutes) {
        String halfHourBefore = "";
        hours -= 8;
        halfHourBefore = formatTime(halfHourBefore, hours, minutes);
        return halfHourBefore += minutes;
    }

    /**
     * Takes the parsed times and converts it to string that is half an hour
     * ahead of time.
     *
     * @param hours
     * @param minutes
     * @return
     */
    private String findHalfHourAfter(int hours, int minutes) {
        String halfHourAfter = "";
        minutes += 30;
        if (minutes >= 60) {
            hours++;
            minutes -= 60;
        }
        halfHourAfter = formatTime(halfHourAfter, hours, minutes);
        return halfHourAfter += minutes;
    }

    /**
     * Formats the given parameters to be a valid time of day.
     *
     * @param time
     * @param hours
     * @param minutes
     * @return
     */
    private String formatTime(String time, int hours, int minutes) {
        if (hours < 10) {
            time = "0";
        }
        time += hours + ":";
        if (minutes < 10) {
            time += 0;
        }
        return time;
    }
}
