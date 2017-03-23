/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import java.util.HashMap;

public class Academy {

    private final int ID;

    private final String name;

    private final HashMap<Integer, String> locations;

    public Academy(int ID, String name) {
        this.ID = ID;
        this.name = name;
        locations = new HashMap<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void addAllLocation(HashMap<Integer, String> newLocations) {
        locations.putAll(newLocations);
    }

    public HashMap<Integer, String> getLocations() {
        return locations;
    }

}
