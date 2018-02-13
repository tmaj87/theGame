package pl.tmaj;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("WinnerRepository")
interface WinnerRepository {

    @GetMapping(value = "/winners")
    Resources<Winner> getAll();
}
