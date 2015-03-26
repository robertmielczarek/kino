package io.projekt.kino.repositories;

import io.projekt.kino.entities.Znizka;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ZnizkaRepository extends JpaRepository<Znizka, Integer> {

}
