package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.ReviewRowMapper;
import com.epam.filmrating.model.entity.Review;

import java.sql.Connection;
import java.util.List;

public class ReviewDaoImpl extends AbstractDao<Review> {
    private static final String TABLE_NAME = "reviews";
    private static final String SELECT_ALL_BY_FILM_ID = "select * from reviews where is_deleted = false and film_id = ?;";
    private static final String INSERT_REVIEW = "insert into reviews (film_id, user_id, rate, content) values (?,?,?,?);";
    private static final String COUNT_FILM_REVIEWS = "select count(*) from reviews where is_deleted = false and film_id = ?;";
    private static final String SUM_FILM_RATES = "select sum(rate) from reviews where is_deleted = false and film_id = ?";

    public ReviewDaoImpl(Connection connection) {
        super(new ReviewRowMapper(), connection, TABLE_NAME);
    }

    public List<Review> findAllByFilmId(Long id) throws DaoException {
        return executeSelectQuery(SELECT_ALL_BY_FILM_ID, id);
    }

    @Override
    public boolean add(Review review) throws DaoException {
        return executeUpdateInsertQuery(INSERT_REVIEW, review.getFilmId(),
                review.getUserId(),
                review.getRate(),
                review.getContent());
    }

    public int countAllFilmReviews(Long filmId) throws DaoException {
        return executeSelectFunctionQuery(COUNT_FILM_REVIEWS, filmId);
    }

    public int sumFilmRates(Long filmId) throws DaoException {
        return executeSelectFunctionQuery(SUM_FILM_RATES,filmId);
    }

}
