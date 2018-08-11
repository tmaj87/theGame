package pl.tmaj;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("WinnerRepository")
public interface WinnerRepository {

    @PostMapping(value = "/winners")
    void save(Winner winner);
}
