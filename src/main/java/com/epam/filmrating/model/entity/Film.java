package com.epam.filmrating.model.entity;

import java.io.Serializable;
import java.time.Year;

/**
 * Film entity.
 */
public class Film implements Identifiable, Serializable {
    public static final String FILM_ID = "id";
    public static final String TYPE = "type";
    public static final String TITLE = "title";
    public static final String GENRE_ID = "genre_id";
    public static final String GENRE = "genre";
    public static final String YEAR = "year";
    public static final String DIRECTOR = "director";
    public static final String COUNTRY_ID = "country_id";
    public static final String COUNTRY = "country";
    public static final String RATING = "rating";
    public static final String POSTER_PATH = "poster_path";

    private static final long serialVersionUID = 1L;

    /**
     * ID.
     */
    private Long id;
    /**
     * Title.
     */
    private String title;
    /**
     * Type.
     */
    private FilmType type;
    /**
     * Genre.
     */
    private Genre genre;
    /**
     * Year.
     */
    private Year year;
    /**
     * Director.
     */
    private String director;
    /**
     * Country.
     */
    private Country country;
    /**
     * Rating.
     */
    private double rating;
    /**
     * PosterPath.
     */
    private String posterPath;
    /**
     * Encoded poster image.
     */
    private String posterImage;

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
                .append("}");
        return stringBuilder.toString();
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Setting id.
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setting title.
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return genre
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * @return type
     */
    public FilmType getType() {
        return type;
    }

    /**
     * Setting type.
     *
     * @param type
     */
    public void setType(FilmType type) {
        this.type = type;
    }

    /**
     * Setting genre.
     *
     * @param genre
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * @return year
     */
    public Year getYear() {
        return year;
    }

    /**
     * Setting year.
     *
     * @param year
     */
    public void setYear(Year year) {
        this.year = year;
    }

    /**
     * @return director
     */
    public String getDirector() {
        return director;
    }

    /**
     * Setting director.
     *
     * @param director
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * @return country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Setting country.
     *
     * @param country
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Setting rating
     *
     * @param rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * @return posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Setting poster path.
     *
     * @param posterPath
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * @return posterImage
     */
    public String getPosterImage() {
        return posterImage;
    }

    /**
     * @param posterImage
     */
    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    /**
     * Film Builder.
     */
    public static class Builder {
        private Film film;

        public Builder() {
            film = new Film();
        }

        public Film build() {
            return film;
        }

        public Film.Builder setId(long id) {
            film.setId(id);
            return this;
        }

        public Film.Builder setTitle(String title) {
            film.setTitle(title);
            return this;
        }

        public Film.Builder setType(FilmType type) {
            film.setType(type);
            return this;
        }

        public Film.Builder setGenre(Genre genre) {
            film.setGenre(genre);
            return this;
        }

        public Film.Builder setYear(Year year) {
            film.setYear(year);
            return this;
        }

        public Film.Builder setDirector(String director) {
            film.setDirector(director);
            return this;
        }

        public Film.Builder setCountry(Country country) {
            film.setCountry(country);
            return this;
        }

        public Film.Builder setRating(Double rating) {
            film.setRating(rating);
            return this;
        }

        public Film.Builder setPosterPath(String posterPath) {
            film.setPosterPath(posterPath);
            return this;
        }

        public Film.Builder setPosterImage(String posterImage) {
            film.setPosterImage(posterImage);
            return this;
        }

    }
}
