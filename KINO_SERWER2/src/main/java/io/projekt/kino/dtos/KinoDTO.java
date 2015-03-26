package io.projekt.kino.dtos;

import java.util.ArrayList;
import java.util.List;

public class KinoDTO {
	    private Integer iDCinema;
	    private String city;
		private String name;
	    private List<Integer> uzytkownikList;
	    private List<Integer> salaList;
	    
	    public Integer getiDCinema() {
			return iDCinema;
		}
		public void setiDCinema(Integer iDCinema) {
			this.iDCinema = iDCinema;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Integer> getUzytkownikList() {
			if(uzytkownikList==null)
				uzytkownikList=new ArrayList<Integer>();
			return uzytkownikList;
		}
		public void setUzytkownikList(List<Integer> uzytkownikList) {
			this.uzytkownikList = uzytkownikList;
		}
		public List<Integer> getSalaList() {
			if(salaList==null)
				salaList=new ArrayList<Integer>();
			return salaList;
		}
		public void setSalaList(List<Integer> salaList) {
			this.salaList = salaList;
		}

}
