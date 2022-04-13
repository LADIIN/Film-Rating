package com.epam.filmrating.model.dao.impl;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.model.dao.AbstractDao;
import com.epam.filmrating.model.dao.mapper.impl.ReviewRowMapper;
import com.epam.filmrating.model.entity.Review;

import java.sql.Connection;
import java.util.List;

/**
 * Review DAO.
 */
public class ReviewDaoImpl extends AbstractDao<Review> {
    /**
     * Table name in database.
     */
    private static final String TABLE_NAME = "reviews";
    /**
     * Query to select all reviews on film.
     */
    private static final String SELECT_ALL_BY_FILM_ID = "SELECT * FROM reviews WHERE is_deleted = false and film_id = ?;";
    /**
     * Query to insert review.
     */
    private static final String INSERT_REVIEW = "INSERT INTO reviews (film_id, user_id, rate, content) VALUES (?,?,?,?);";
    /**
     * Query to count reviews on film.
     */
    private static final String COUNT_FILM_REVIEWS = "SELECT count(*) FROM reviews WHERE is_deleted = false and film_id = ?;";

    /**
     * Query to select average rate on film.
     */
    private static final String SELECT_AVERAGE_RATE = "SELECT avg(rate) FROM reviews WHERE is_deleted = false and film_id = ?";

    private static final String IS_REVIEW_EXIST = "SELECT count(1) FROM reviews WHERE is_deleted = false and user_id = ? and film_id = ?;";

    /**
     * Constructor.
     *
     * @param connection
     */
    public ReviewDaoImpl(Connection connection) {
        super(new ReviewRowMapper(), connection, TABLE_NAME);
    }

    @Override
    public boolean add(Review review) throws DaoException {
        return executeUpdateInsertQuery(INSERT_REVIEW, review.getFilmId(),
                review.getUserId(),
                review.getRate(),
                review.getContent());
    }

    /**
     * Finding reviews on film.
     *
     * @param filmId
     * @return List of reviews.
     * @throws DaoException
     */
    public List<Review> findAllByFilmId(Long filmId) throws DaoException {
        return executeSelectQuery(SELECT_ALL_BY_FILM_ID, filmId);
    }

    /**
     * Counting all reviews on film.
     *
     * @param filmId
     * @return amount of reviews.
     * @throws DaoException
     */
    public int countAllFilmReviews(Long filmId) throws DaoException {
        return executeSelectFunctionQuery(COUNT_FILM_REVIEWS, filmId).intValue();
    }

    /**
     * Calculating average film rate.
     *
     * @param filmId
     * @return average film rate
     * @throws DaoException
     */
    public double calculateAverageFilmRate(Long filmId) throws DaoException {
        return executeSelectFunctionQuery(SELECT_AVERAGE_RATE, filmId).doubleValue();
    }

    /**
     * Checks is user review on film exist.
     *
     * @param userId
     * @param filmId
     * @return 1 if exist and 0 otherwise.
     * @throws DaoException
     */
    public int isReviewExist(Long userId, Long filmId) throws DaoException {
        return executeSelectFunctionQuery(IS_REVIEW_EXIST, userId, filmId).intValue();
    }


}
