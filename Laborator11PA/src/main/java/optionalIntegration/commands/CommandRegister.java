package optionalIntegration.commands;

import optional.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class CommandRegister implements Command {

    private String name;

    public CommandRegister(String name) {
        this.name = name;
    }

    @Override
    public String run()
    {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{ \"name\": \"" + name + "\" }", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        return "succ";
    }
}
