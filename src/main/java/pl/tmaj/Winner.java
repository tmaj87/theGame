package pl.tmaj;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Winner {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected Winner() {
    }

    public Winner(String name) {
        this.name = name;
    }
}