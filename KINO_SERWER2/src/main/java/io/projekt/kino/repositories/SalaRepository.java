package io.projekt.kino.repositories;

import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SalaRepository extends  JpaRepository<Sala,Integer> {

}
