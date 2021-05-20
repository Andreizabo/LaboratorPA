package optionalIntegration.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Command {
    Logger log = LoggerFactory.getLogger(CommandRegister.class);
    String uri = "https://localhost:443/person/";

    String run();
}
