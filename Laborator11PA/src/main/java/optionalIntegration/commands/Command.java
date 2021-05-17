package optionalIntegration.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Command {
    Logger log = LoggerFactory.getLogger(CommandRegister.class);
    String uri = "http://localhost:8082/person/";

    String run();
}
