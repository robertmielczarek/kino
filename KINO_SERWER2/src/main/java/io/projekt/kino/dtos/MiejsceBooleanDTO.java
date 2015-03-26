package io.projekt.kino.dtos;

import java.util.LinkedList;
import java.util.List;

public class MiejsceBooleanDTO {

	private MiejsceDTO miejsceDTO;
	private Boolean isBooked;
	

	public Boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Boolean isBooked) {
		this.isBooked = isBooked;
	}

	public MiejsceDTO getMiejsceDTO() {
		return miejsceDTO;
	}

	public void setMiejsceDTO(MiejsceDTO miejsceDTO) {
		this.miejsceDTO = miejsceDTO;
	}

}
