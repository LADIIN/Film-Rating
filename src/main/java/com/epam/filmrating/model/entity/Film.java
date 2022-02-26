package com.epam.filmrating.model.entity;

import java.io.Serializable;
import java.time.Year;

public class Film implements Identifiable, Serializable {
    private static final long serialVersionUID = 1234567L;
    private Long id;
    private String title;
    private FilmType type;
    private Genre genre;
    private Year year;
    private String director;
    private Country country;
    private double rating;
    private String posterPath;

    public Film(Long id, String title, FilmType type, Genre genre,
                Year year, String director, Country country, double rating, String posterPath) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.genre = genre;
        this.year = year;
        this.director = director;
        this.country = country;
        this.rating = rating;
        this.posterPath = posterPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Film film = (Film) o;
        return Double.compare(film.rating, rating) == 0
                & id.equals(film.id)
                && title.equals(film.title)
                && genre == film.genre
                && year.equals(film.year)
                && director.equals(film.director)
                && country == film.country;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Long.hashCode(id);
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        result = prime * result + ((director == null) ? 0 : director.hashCode());
        result = prime * result + Double.valueOf(rating).hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{title = ").append(title)
                .append(", type = ").append(type)
                .append(", genre = ").append(genre)
                .append(", year = ").append(year)
                .append(", director = ").append(director)
                .append(", country = ").append(country)
                .append(", rating = ").append(rating)
                .append(", posterPath = ").append(posterPath)
                .append("}");
        return stringBuilder.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public FilmType getType() {
        return type;
    }

    public void setType(FilmType type) {
        this.type = type;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public Long getId() {
        return id;
    }
}