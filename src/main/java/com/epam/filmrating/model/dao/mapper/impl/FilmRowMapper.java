package com.epam.filmrating.model.dao.mapper.impl;

import com.epam.filmrating.model.dao.TableColumns;
import com.epam.filmrating.model.dao.mapper.RowMapper;
import com.epam.filmrating.model.entity.Country;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.FilmType;
import com.epam.filmrating.model.entity.Genre;
import com.epam.filmrating.util.FileEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;


public class FilmRowMapper implements RowMapper<Film> {
    private final FileEncoder base64StringConverter = new FileEncoder();

    //TODO:rebuild to Builder pattern;
    @Override
    public Film resultToObject(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(TableColumns.FILM_ID);
        FilmType type = FilmType.fromString(resultSet.getString(TableColumns.TYPE));
        String title = resultSet.getString(TableColumns.TITLE);
        Genre genre = Genre.fromString(resultSet.getString(TableColumns.GENRE));
        Year year = Year.of(resultSet.getInt(TableColumns.YEAR));
        String director = resultSet.getString(TableColumns.DIRECTOR);
        Country country = Country.fromString(resultSet.getString(TableColumns.COUNTRY));
        double rating = resultSet.getDouble(TableColumns.RATING);
        String posterPath = resultSet.getString(TableColumns.POSTER_PATH);
        String posterImage = base64StringConverter.encode(posterPath);

        return new Film.Builder()
                .setId(id)
                .setTitle(title)
                .setType(type)
                .setGenre(genre)
                .setYear(year)
                .setDirector(director)
                .setCountry(country)
                .setRating(rating)
                .setPosterPath(posterPath)
                .setPosterImage(posterImage).build();

    }
}
