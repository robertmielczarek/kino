package io.projekt.kino.repositories;

import io.projekt.kino.entities.Uzytkownik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Integer>
{
//	@Query("select u from user u where u.login = :login and u.password = :password")
//	Uzytkownik findByLoginAndPassword(String login, String password);
	
//	User findByEmail(String email);
//
//	User findByUserName(@Param("userName") String userName);
//
//	User findByUserNameAndPassword(String userName, String password);

	//	@Query("select u from user u where u.role.role = :role")
}
