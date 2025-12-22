package org.archethy.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Person {

    // Proprietà visibili alle classi figlie
    protected String firstname;
    protected String lastname;


    // Costruttore senza parametri
    public Person() {

    }

    // Costruttore con parametri
    public Person(String firstname, String lastname) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
    }


}
