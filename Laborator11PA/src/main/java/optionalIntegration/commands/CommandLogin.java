package optionalIntegration.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandLogin implements Command {
    private String name;

    public CommandLogin(String name) {
        this.name = name;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.getForEntity(uri + name, String.class);

        if(response.getStatusCode().is2xxSuccessful()) {
            return "succ";
        }
        else {
            return "exception";
        }
    }
}
