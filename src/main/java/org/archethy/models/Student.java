package org.archethy.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
public class Student extends Person {

    private int studentId;
    private String studentNumber;
    private LocalDate dateOfBirth;

    // Costruttore senza parametri
    public Student() {
        super();

    }

    // Costruttore con parametri che eredita dalla classe astratta \ superclasse
    public Student(String firstname, String lastname, Integer id, LocalDate dateOfBirth, String studentNumber) {
        super(firstname, lastname);
        this.studentId = id;
        this.dateOfBirth = dateOfBirth;
        this.studentNumber = studentNumber;
    }


    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}