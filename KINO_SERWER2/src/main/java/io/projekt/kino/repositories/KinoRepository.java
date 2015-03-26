package io.projekt.kino.repositories;

import io.projekt.kino.entities.Kino;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface KinoRepository extends JpaRepository<Kino,Integer>{
 
}
