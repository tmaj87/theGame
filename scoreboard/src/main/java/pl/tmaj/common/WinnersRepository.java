package pl.tmaj.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WinnersRepository extends JpaRepository<Winner, Long> {}
