package pl.tmaj;

import java.util.Objects;

public class Winner {

    private String name;

    public Winner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Winner winner = (Winner) o;
        return Objects.equals(name, winner.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
