package pl.tmaj.common;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("WinnerRepository")
public interface WinnerRepository {

    @GetMapping(value = "/winners")
    Resources<Winner> getAll();
}
