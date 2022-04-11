package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.ReviewDaoImpl;
import com.epam.filmrating.model.entity.Review;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.pool.TransactionManager;
import com.epam.filmrating.model.validator.ReviewValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to {@link ReviewDaoImpl} and operations with {@link Review}.
 */
public class ReviewService {
    /**
     * Transaction manager.
     */
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    /**
     * Finds all reviews on film.
     *
     * @param filmId
     * @return {@link List} of {@link Review}
     * @throws ServiceException
     */
    public List<Review> findAllByFilmId(Long filmId) throws ServiceException {
        List<Review> reviews;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            ReviewDaoImpl reviewDao = new ReviewDaoImpl(connection);
            reviews = reviewDao.findAllByFilmId(filmId);
            UserService userService = new UserService();
            for (Review review : reviews) {
                Optional<User> userOptional = userService.findById(review.getUserId());
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    review.setUser(user);
                }
            }
        } catch (TransactionException | DaoException e) {
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return reviews;
    }

    /**
     * Adds review on film.
     *
     * @param rate
     * @param content
     * @param filmId
     * @param userId
     * @return true if is added and false otherwise.
     * @throws ServiceException
     */
    public boolean add(int rate, String content, Long filmId, Long userId) throws ServiceException {
        boolean isAdded = false;
        ReviewValidator reviewValidator = new ReviewValidator();
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            ReviewDaoImpl reviewDao = new ReviewDaoImpl(connection);
            Review review = new Review.Builder()
                    .setRate(rate)
                    .setContent(content)
                    .setFilmId(filmId)
                    .setUserId(userId).build();

            if (reviewValidator.isValid(review)) {
                reviewDao.add(review);
                isAdded = true;
            }
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isAdded;
    }

    /**
     * Returns amount of reviews on film.
     *
     * @param filmId
     * @return int
     * @throws ServiceException
     */
    public int countFilmReviews(Long filmId) throws ServiceException {
        int amount = 0;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            ReviewDaoImpl reviewDao = new ReviewDaoImpl(connection);
            amount = reviewDao.countAllFilmReviews(filmId);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return amount;
    }

    /**
     * Return average film reviews rate.
     *
     * @param filmId
     * @return double
     * @throws ServiceException
     */
    public double getAverageFilmRate(Long filmId) throws ServiceException {
        double averageRate = 0;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            ReviewDaoImpl reviewDao = new ReviewDaoImpl(connection);
            averageRate = reviewDao.calculateAverageFilmRate(filmId);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return averageRate;
    }

    /**
     * Deletes review.
     * @param id
     * @return true if is deleted and false otherwise.
     * @throws ServiceException
     */
    public boolean deleteReview(Long id) throws ServiceException {
        boolean isDeleted = false;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            ReviewDaoImpl reviewDao = new ReviewDaoImpl(connection);
            isDeleted = reviewDao.delete(id);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        }
        return isDeleted;
    }

}
