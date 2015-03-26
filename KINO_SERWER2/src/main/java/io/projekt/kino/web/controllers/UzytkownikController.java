package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.UzytkownikDTO;
import io.projekt.kino.entities.Uzytkownik;
import io.projekt.kino.services.UzytkownikService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// zastanawialem sie nad @RestController, ale chyba nie
@Controller
@RequestMapping(value = "/users/")
public class UzytkownikController {
	
	@Autowired
	private UzytkownikService userService;
	
	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<UzytkownikDTO> getUsers() {
		return userService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/login/{login}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean login(@PathVariable String login,
			@PathVariable String password) {
		 userService.login(login, password);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/authenticate/{login}/{password}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UzytkownikDTO authenticate(@PathVariable String login,
			@PathVariable String password) {
		return userService.login(login, password);
	}
	

	@RequestMapping(method = RequestMethod.DELETE, value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteUser(@PathVariable("userId") int userId) {
		userService.deleteUser(userId);
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer create(@RequestBody UzytkownikDTO uzytkownikDTO) {
		 return userService.create(uzytkownikDTO);
	}
	
	@RequestMapping(value="/{userId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean update(@RequestBody UzytkownikDTO uzytkownikDTO,@PathVariable Integer userId) {
		 uzytkownikDTO.setiDuser(userId);
		 userService.update(uzytkownikDTO);
		 return true;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public UzytkownikDTO getUser(@PathVariable Integer userId) {
		return userService.findUzytkownik(userId);
	}
	
	// dodatkowe metody ponizej na razie w fazie testów
	
	@RequestMapping(method = RequestMethod.GET, value = "users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Uzytkownik getUser2(@PathVariable Integer userId) {
		return userService.findUzytkownik2(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list2", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Uzytkownik> getUsers2() {
		return userService.findAll2();
	}

}