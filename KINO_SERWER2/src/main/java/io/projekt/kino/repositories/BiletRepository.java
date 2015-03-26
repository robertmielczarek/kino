package io.projekt.kino.repositories;

import java.util.Date;

import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Film;
import io.projekt.kino.services.BiletService;

import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import antlr.StringUtils;

import java.util.List;

import javax.validation.constraints.Null;

@RepositoryRestResource
public interface BiletRepository extends JpaRepository<Bilet, Integer>,
		JpaSpecificationExecutor<Bilet> {

	final static String query = "select COALESCE(COUNT(*),0) from Bilet b ";
	final static String querySum = "select COALESCE(SUM(b.price),0.0) from Bilet b ";
	final static String where = "WHERE b.iDTicket IS NOT NULL ";
	final static String from = "AND b.sellingDate >= :startDate ";
	final static String to = "AND b.sellingDate <= :endDate ";
	final static String between = "AND b.sellingDate BETWEEN :startDate AND :endDate ";
	final static String film = "AND b.iDShow.iDMovie.iDMovie = :movieId ";
	final static String kino = "AND b.iDShow.iDAuditorium.iDCinema.iDCinema = :cinemaId ";
	final static String filmgenre = "AND b.iDShow.iDMovie.genre = :genre ";
	final static String cashier = "AND b.iDCashier.iDuser = :cashierId ";
	final static String sold = "AND b.ticketStatus = 'Sold' ";
	final static String day = "AND DAYOFWEEK(sellingDate)=:dayOfWeek ";

	@Query(query + where + film + kino + between + sold)
	Integer getSoldTicketsByMovie(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("movieId") int movieId,
			@Param("cinemaId") int cinemaId);

	@Query(query + where + film + between + sold)
	Integer getSoldTicketsByMovie(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("movieId") int movieId);

	@Query(query + where + filmgenre + kino + sold + between)
	Integer getSoldTicketsByGenre(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("genre") String genre,
			@Param("cinemaId") int cinemaId);

	@Query(query + where + filmgenre + between + sold)
	Integer getSoldTicketsByGenre(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("genre") String genre);

	@Query(query + where + kino + between + sold)
	Integer getSoldTicketsByCinema(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("cinemaId") int cinemaId);

	@Query(query + where + between + sold)
	Integer getSoldTicketsByCinema(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query(querySum + where + kino + between + sold)
	Double getMoneyEarnedByCinema(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("cinemaId") int cinemaId);

	@Query(querySum + where + between + sold)
	Double getMoneyEarnedByCinema(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query(value = query + where + between + kino + day + sold)
	Integer getSoldTicketsByDay(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("cinemaId") int cinemaId,
			@Param("dayOfWeek") int dayOfWeek);

	@Query(value = query + where + between + day + sold)
	Integer getSoldTicketsByDay(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("dayOfWeek") int dayOfWeek);

	@Query(query + where + cashier + between + sold)
	Integer getSoldTicketsByCashier(@Param("startDate") Date startDate,
			@Param("endDate") Date endDate, @Param("cashierId") int cashierId);

}
