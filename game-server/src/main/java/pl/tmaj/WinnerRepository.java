package pl.tmaj;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient("WinnerRepository")
public interface WinnerRepository {

    @RequestMapping(method = POST, value = "/winners", consumes = "application/json")
    void save(String content);
}
