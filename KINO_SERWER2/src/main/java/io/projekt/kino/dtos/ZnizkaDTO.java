package io.projekt.kino.dtos;

public class ZnizkaDTO {
	private Integer iDDiscount;
	private String name;
	private double value;

	public Integer getiDDiscount() {
		return iDDiscount;
	}

	public void setiDDiscount(Integer iDDiscount) {
		this.iDDiscount = iDDiscount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}
