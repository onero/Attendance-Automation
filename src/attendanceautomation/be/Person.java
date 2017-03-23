/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Person {

    private final int ID;

    private final String firstName;

    private final String lastName;

    private final String Email;

    private final String fullName;

    public Person(int ID, String firstName, String lastName, String Email) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Email = Email;
        this.fullName = firstName + " " + lastName;
    }

    public int getID() {
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return Email;
    }

    public String getFullName() {
        return fullName;
    }

    public StringProperty getFullNameProperty() {
        StringProperty propertyFullName = new SimpleStringProperty(fullName);
        return propertyFullName;
    }

    public StringProperty getEmailProperty() {
        StringProperty propertyEmail = new SimpleStringProperty(Email);
        return propertyEmail;
    }

}
