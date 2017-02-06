/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.be;

public abstract class Person {

    private int ID;

    private String firstName;

    private String lastName;

    private final String fullName = firstName + " " + lastName;

    private int phoneNumer;

    private String email;

}
