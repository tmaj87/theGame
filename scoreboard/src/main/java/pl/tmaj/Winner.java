package pl.tmaj;

public class Winner {

    private Long id;
    private String name;

    public Winner(String name, Long id) {
        this.name = name;
        this.id = id;
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
        return name;
    }
}
