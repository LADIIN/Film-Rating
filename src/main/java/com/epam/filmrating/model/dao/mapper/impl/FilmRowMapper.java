package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.*;
import com.epam.filmrating.util.FileEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;


public class FilmRowMapper implements RowMapper<Film> {
    private final FileEncoder base64StringConverter = new FileEncoder();

    //TODO:rebuild to Builder pattern;
    @Override
    public Film resultToObject(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(Film.FILM_ID);
        FilmType type = FilmType.fromString(resultSet.getString(Film.TYPE));
        String title = resultSet.getString(Film.TITLE);
        Year year = Year.of(resultSet.getInt(Film.YEAR));
        Long genreId = resultSet.getLong(Film.GENRE_ID);
        String genreName = resultSet.getString(Film.GENRE);
        Genre genre = new Genre(genreId, genreName);
        String director = resultSet.getString(Film.DIRECTOR);
        Long countryId = resultSet.getLong(Film.COUNTRY_ID);
        String name = resultSet.getString(Film.COUNTRY);
        Country country = new Country(countryId, name);
        double rating = resultSet.getDouble(Film.RATING);
        String posterPath = resultSet.getString(Film.POSTER_PATH);
        String posterImage = base64StringConverter.encode(posterPath);

        return new Film.Builder()
                .setId(id)
                .setTitle(title)
                .setType(type)
                .setYear(year)
                .setGenre(genre)
                .setDirector(director)
                .setCountry(country)
                .setRating(rating)
                .setPosterPath(posterPath)
                .setPosterImage(posterImage).build();

    }
}
