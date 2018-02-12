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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Winner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}