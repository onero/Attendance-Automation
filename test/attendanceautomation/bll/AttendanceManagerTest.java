/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.bll;

import java.util.ArrayList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author gta1
 */
public class AttendanceManagerTest {

    public AttendanceManagerTest() {
    }

    /**
     * Test of computeAllAttendance method, of class AttendanceManager.
     */
    @Test
    public void testComputeAllAttendance() {
        AttendanceManager instance = new AttendanceManager();
        ArrayList<PieChart.Data> listToCalculate = new ArrayList<>();

        //We add a student with 5%
        Data adam = new PieChart.Data("Adam", 5);
        //We add a student with 15%
        Data rasmus = new PieChart.Data("Rasmus", 15);
        listToCalculate.add(adam);
        listToCalculate.add(rasmus);

        //We now expect the returned students to have
        ArrayList<PieChart.Data> expectedList = new ArrayList<>();
        //25%
        expectedList.add(new Data("Adam", 25));
        //And 75%
        expectedList.add(new Data("Rasmus", 75));

        double expResult = expectedList.get(0).getPieValue();

        ArrayList<PieChart.Data> calculatedList = instance.computeAllAttendance(listToCalculate);

        double result = calculatedList.get(0).getPieValue();

        assertEquals(expResult, result, 0);
    }
}
