package io.projekt.kino.repositories;

import io.projekt.kino.entities.Seans;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.projekt.kino.entities.Film;

import java.util.List;
import java.util.Date;

@RepositoryRestResource
public interface SeansRepository extends JpaRepository<Seans, Integer> {

	final static String query = "SELECT s from Seans s ";
	final static String where = "WHERE s.iDShow IS NOT NULL ";
	final static String filmgenre = "AND s.iDMovie.genre = :genre ";
	final static String filmtitle = "AND LOWER(s.iDMovie.title) LIKE :title ";
	final static String date = "AND s.date BETWEEN :startDate AND :endDate ";
	final static String kino = "AND s.iDAuditorium.iDCinema.iDCinema = :cinemaId ";

	@Query(query + where + filmtitle + kino)
	List<Seans> getShowsByTitle(@Param("title") String title,
			@Param("cinemaId") int cinemaId);

	@Query(query + where + date + kino)
	List<Seans> getShowsByTime(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("cinemaId") int cinemaId);

	@Query(query + where + filmgenre + kino)
	List<Seans> getShowsByGenre(@Param("genre") String genre,
			@Param("cinemaId") int cinemaId);

}
