package org.archethy.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student extends Person {

    private String studentNumber;
    private String dateOfBirth;

    // Costruttore senza parametri
    public Student() {
        super();

    }

    // Costruttore con parametri che eredita dalla classe astratta \ superclasse
    public Student(String firstname, String lastname, String dateOfBirth, String studentNumber) {
        super(firstname, lastname);
        this.dateOfBirth = dateOfBirth;
        this.studentNumber = studentNumber;
    }


    @Override
    public String toString() {
        return "Student{" +
                "dateOfBirth='" + dateOfBirth + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}