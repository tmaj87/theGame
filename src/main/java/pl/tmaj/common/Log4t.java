package pl.tmaj.common;

import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.getLogger;

public class Log4t {

    private final Logger logger;

    private Log4t(Object clazz) {
        String name = clazz.getClass().getSimpleName();
        this.logger = getLogger(name + "_id" + RandomString.ofLength(12));
    }

    public static Log4t getInstanceFor(Object clazz) {
        return new Log4t(clazz);
    }

    public void WARN(String string) {
        logger.log(WARNING, string);
    }

    public void INFO(String string) {
        logger.log(INFO, string);
    }
}
