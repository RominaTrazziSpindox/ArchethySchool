package org.archethy.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Teacher extends Person {

    // Proprietà
    private int teacherId;
    private String teachingSubject;

    // Costruttore senza parametri
    public Teacher() {
        super();
    }

    // Costruttore con parametri che eredita dalla classe superiore\superclasse
    public Teacher(int id, String firstname, String lastname, String teachingSubject) {
        super(firstname, lastname);
        this.teacherId = id;
        this.teachingSubject = teachingSubject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "TeacherId=" + teacherId +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", teachingSubject='" + teachingSubject + '\'' +
                '}';
    }

}





