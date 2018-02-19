package pl.tmaj.common;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("WinnerRepository")
public interface WinnerRepository {

    @Retryable
    @PostMapping(value = "/winners")
    void save(Winner winner);
}
