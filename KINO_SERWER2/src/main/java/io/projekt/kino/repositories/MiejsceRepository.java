package io.projekt.kino.repositories;

import io.projekt.kino.entities.Miejsce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MiejsceRepository extends JpaRepository<Miejsce, Integer>{

}
