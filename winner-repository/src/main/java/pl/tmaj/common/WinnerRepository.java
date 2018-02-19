package pl.tmaj.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WinnerRepository extends JpaRepository<Winner, Long> {}
