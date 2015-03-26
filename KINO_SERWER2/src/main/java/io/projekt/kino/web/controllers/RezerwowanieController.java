package io.projekt.kino.web.controllers;

import io.projekt.kino.dtos.BiletDTO;
import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.exceptions.InvalidCredentialsException;
import io.projekt.kino.exceptions.RezerwacjaCancelledException;
import io.projekt.kino.exceptions.SeatTakenException;
import io.projekt.kino.services.AdditionalService;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.projekt.kino.services.RezerwowanieService;

@Controller
@RequestMapping(value="/rezerwowanie/")
public class RezerwowanieController {
	
	@InitBinder
	private void dataBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@Autowired
	private RezerwowanieService rezerwowanieService;
	
	@RequestMapping(method = RequestMethod.POST, value="/reserve/{Id_Seat}/{Id_Show}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean reserveTemporary(@PathVariable int Id_Seat,
			@PathVariable int Id_Show) {
		return rezerwowanieService.reserveTemporary(Id_Seat,Id_Show);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/unreserve/{Id_Seat}/{Id_Show}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void unreserveTemporary(@PathVariable int Id_Seat,
			@PathVariable int Id_Show) {
		 rezerwowanieService.unreserveTemporary(Id_Seat,Id_Show);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/book/{customerName}/{customerLastName}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Integer bookTickets(@RequestBody List<BiletDTO> biletyDTO, 
			@PathVariable String customerName, 
			@PathVariable String customerLastName, 
			@RequestParam("customerEmail") String customerEmail) throws SeatTakenException {
		return rezerwowanieService.bookTickets(biletyDTO,customerName	, customerLastName,customerEmail);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/sell", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BiletDTO> sellTickets(@RequestBody List<BiletDTO> biletyDTO) throws Exception{
		return rezerwowanieService.sellTickets(biletyDTO);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/cancel/{customerLastName}/{bookingId}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean cancelBooking(@PathVariable String customerLastName,@PathVariable int bookingId) throws InvalidCredentialsException, RezerwacjaCancelledException, SeatTakenException{
		return rezerwowanieService.cancelBooking(customerLastName,bookingId);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/realize/{customerLastName}/{bookingId}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<BiletDTO> realizeBooking(@PathVariable String customerLastName,@PathVariable int bookingId) throws InvalidCredentialsException, RezerwacjaCancelledException, SeatTakenException{
		return rezerwowanieService.realizeBooking(customerLastName,bookingId);
	}

}