package pl.tmaj;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Scoreboard {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        // this service will request list from WinnersRepository
        // and display it in html5-like way
        return "winners list";
    }
}
