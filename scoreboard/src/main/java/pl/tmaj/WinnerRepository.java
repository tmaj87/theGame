package pl.tmaj;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("WinnerRepository")
public interface WinnerRepository {

    @GetMapping(value = "/winners")
    Resources<Winner> findAll();

    @GetMapping(value = "/winners?size=1&sort=id,desc")
    Resources<Winner> getLatest();
}
