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

public class CommandFriend implements Command {
    private String friend1;
    private String friend2;

    final String uri = "http://localhost:8082/friends/";

    public CommandFriend(String friend1, String friend2) {
        this.friend1 = friend1;
        this.friend2 = friend2;
    }

    @Override
    public String run() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{ \"name1\": \"" + friend1 + "\", \"name2\": \"" + friend2 +"\" }", headers);
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

        if(response.getStatusCode().is2xxSuccessful()) {
            return "succ";
        }
        else {
            return "exception";
        }
    }
}
