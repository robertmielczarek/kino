package io.projekt.kino.dtos;

import java.util.ArrayList;
import java.util.List;

public class FilmDTO {

    private Integer iDMovie;
    private String title;
    private String genre;
    private String lenght;
    private String description;
    private List<Integer> seansIds;
    
	public Integer getiDMovie() {
		return iDMovie;
	}
	public void setiDMovie(Integer iDMovie) {
		this.iDMovie = iDMovie;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getLenght() {
		return lenght;
	}
	public void setLenght(String lenght) {
		this.lenght = lenght;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Integer> getSeansIds() {
		if(seansIds==null){
			seansIds=new ArrayList<>();
		}
		return seansIds;
	}
	public void setSeansIds(List<Integer> seansIds) {
		this.seansIds = seansIds;
	}
}
