package pl.tmaj;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient("WinnerRepository")
interface WinnerRepository {

    @RequestMapping(method = GET, value = "/winners")
    Resources<Winner> getAll();
}
