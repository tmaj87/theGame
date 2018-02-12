package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@RestController
public class Scoreboard {

    @Value("${welcome.message}")
    private String welcomeMessage;

//    private RestTemplate restTemplate;
//
//    public Scoreboard(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        // this service will request list from WinnersRepository
        // and display it in html5-like way
        return welcomeMessage;
    }
}
