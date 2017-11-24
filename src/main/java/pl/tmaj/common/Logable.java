package pl.tmaj.common;

public abstract class Logable {
    public final Log4j log4j = new Log4j(this);
}
