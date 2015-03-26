package io.projekt.kino.repositories;

import io.projekt.kino.entities.Rezerwacja;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RezerwacjaRepostiory extends JpaRepository<Rezerwacja, Integer> {

}
