package io.projekt.kino.repositories;

import java.util.List;

import io.projekt.kino.entities.Film;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.lang.String;

@RepositoryRestResource
public interface FilmRepository extends JpaRepository<Film,Integer>{
 
}
