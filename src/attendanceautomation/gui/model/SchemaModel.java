/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.model;

import java.util.ArrayList;
import java.util.List;

public class SchemaModel {

    private final List<Integer> firstWeekFebruary = new ArrayList<>();
    private final List<Integer> secondWeekFebruary = new ArrayList<>();
    private final List<Integer> thirdWeekFebruary = new ArrayList<>();
    private final List<Integer> lastWeekFebruary = new ArrayList<>();

    public SchemaModel() {
        firstWeekFebruary.add(30);
        firstWeekFebruary.add(31);
        firstWeekFebruary.add(1);
        firstWeekFebruary.add(2);
        firstWeekFebruary.add(3);

        secondWeekFebruary.add(6);
        secondWeekFebruary.add(7);
        secondWeekFebruary.add(8);
        secondWeekFebruary.add(9);
        secondWeekFebruary.add(10);

        thirdWeekFebruary.add(13);
        thirdWeekFebruary.add(14);
        thirdWeekFebruary.add(15);
        thirdWeekFebruary.add(16);
        thirdWeekFebruary.add(17);

        lastWeekFebruary.add(20);
        lastWeekFebruary.add(21);
        lastWeekFebruary.add(22);
        lastWeekFebruary.add(23);
        lastWeekFebruary.add(24);
    }

    public List<Integer> getFirstWeekFebruary() {
        return firstWeekFebruary;
    }

    public List<Integer> getSecondWeekFebruary() {
        return secondWeekFebruary;
    }

    public List<Integer> getThirdWeekFebruary() {
        return thirdWeekFebruary;
    }

    public List<Integer> getLastWeekFebruary() {
        return lastWeekFebruary;
    }

}
