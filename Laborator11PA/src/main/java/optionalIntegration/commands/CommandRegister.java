package optionalIntegration.commands;

import compulsory.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class CommandRegister implements Command {

    private String name;
    final Logger log = LoggerFactory.getLogger(CommandRegister.class);
    final String uri = "http://localhost:8081/";

    public CommandRegister(String name) {
        this.name = name;
    }

    @Override
    public String run() {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    uri, HttpMethod.POST, new HttpEntity<Person>(new Person("vlad")),
                    new ParameterizedTypeReference<String>(){}
            );
            String result = response.getBody();
            return "succ";
    }
}
