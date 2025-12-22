package org.archethy.models;


public class Teacher extends Person {

    // Proprietà
    private String teachingSubject;

    // Costruttore senza parametri
    public Teacher() {
        super();
    }

    // Costruttore con parametri che eredita dalla classe superiore\superclasse
    public Teacher(String firstname, String lastname, String teachingSubject) {
        super(firstname, lastname);
        this.teachingSubject = teachingSubject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", teachingSubject='" + teachingSubject + '\'' +
                '}';
    }
}
