package pl.tmaj.common;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;

public class Log4t {

    private final Logger logger;

    public Log4t(String name) {
        this.logger = getLogger(name, name);
    }

    public void WARN(String string) {
        logger.log(WARNING, string);
    }

    public void INFO(String string) {
        logger.log(INFO, string);
    }
}
