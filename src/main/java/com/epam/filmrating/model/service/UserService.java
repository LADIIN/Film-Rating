package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.UserDaoImpl;
import com.epam.filmrating.model.entity.Film;
import com.epam.filmrating.model.entity.Review;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.pool.TransactionManager;
import com.epam.filmrating.model.validator.UserValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * Provides access to {@link UserDaoImpl} and operations with {@link User}.
 */
public class UserService {
    /**
     * Transaction manager.
     */
    private final TransactionManager transactionManager = TransactionManager.getInstance();

    /**
     * Returns {@link User} if login and password are valid.
     *
     * @param login
     * @param password
     * @return {@link Optional} of {@link User}
     * @throws ServiceException
     */
    public Optional<User> login(String login, String password) throws ServiceException {
        Optional<User> user;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            user = userDao.findUserByLoginAndPassword(login, password);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return user;
    }

    /**
     * Register {@link User}.
     *
     * @param login
     * @param password
     * @param email
     * @return true if is registered and false otherwise.
     * @throws ServiceException
     */
    public boolean register(String login, String password, String email) throws ServiceException {
        boolean isRegistered = false;
        UserValidator userValidator = new UserValidator();
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);

            User user = new User.Builder()
                    .setLogin(login)
                    .setEmail(email)
                    .build();
            if (userValidator.isValid(user, password)) {
                isRegistered = userDao.add(user, password);
            }
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isRegistered;
    }

    /**
     * Checks is user with login or email exists.
     *
     * @param login
     * @param email
     * @return true if exist and false otherwise.
     * @throws ServiceException
     */
    public boolean isUserAlreadyExist(String login, String email) throws ServiceException {
        int amount;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            amount = userDao.countUsersWithLoginOrEmail(login, email);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return amount != 0;
    }

    /**
     * Finds {@link User} by ID.
     *
     * @param id
     * @return {@link Optional} of {@link User}
     * @throws ServiceException
     */
    public Optional<User> findById(Long id) throws ServiceException {
        Optional<User> userOptional = Optional.empty();
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            userOptional = userDao.findById(id);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return userOptional;
    }

    /**
     * Sets user block status.
     *
     * @param id
     * @return true if is updated and false otherwise.
     * @throws ServiceException
     */
    public boolean setBlockStatus(Long id) throws ServiceException {
        boolean isUpdated = false;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            Optional<User> userOptional = userDao.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean isBlocked = user.isBlocked();
                user.setBlocked(!isBlocked);
                isUpdated = userDao.update(user);
                transactionManager.commit();
            }
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isUpdated;
    }

    /**
     * Finds group of users for page.
     *
     * @param currentPage
     * @param usersOnPage
     * @return {@link List} of {@link User}
     * @throws ServiceException
     */
    public List<User> findGroupForPage(int currentPage, int usersOnPage) throws ServiceException {
        List<User> users;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            int offset = (currentPage - 1) * usersOnPage;
            users = userDao.getGroup(offset, usersOnPage);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return users;
    }

    /**
     * Returns amount of users.
     *
     * @return int
     * @throws ServiceException
     */
    public int countUsers() throws ServiceException {
        int amount = 0;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            amount = userDao.countAll();
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
     * Raises user status.
     *
     * @param userId
     * @param status
     * @throws ServiceException
     */
    public void raiseStatusById(Long userId, int status) throws ServiceException {
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            userDao.raiseStatus(userId, status);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    /**
     * Updates user status by review rate. If integer part of films rating equals to user review rate,
     * user status raises.
     *
     * @param filmId
     * @throws ServiceException
     */
    public void updateUsersStatusByReviewRate(Long filmId) throws ServiceException {
        FilmService filmService = new FilmService();
        ReviewService reviewService = new ReviewService();
        UserService userService = new UserService();

        Optional<Film> filmOptional = filmService.findById(filmId);
        Film film = filmOptional.orElseThrow(IllegalArgumentException::new);
        double rating = film.getRating();

        List<Review> reviews = reviewService.findAllByFilmId(filmId);
        for (Review review : reviews) {
            if (review.getRate() == (int) rating) {
                userService.raiseStatusById(review.getUserId(), 1);
            }
        }
    }

    /**
     * Changes user role.
     *
     * @param id
     * @return true id is changed and false otherwise.
     * @throws ServiceException
     */
    public boolean changeRole(Long id) throws ServiceException {
        boolean isUpdated = false;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            Optional<User> userOptional = userDao.findById(id);

            if (userOptional.isPresent()) {
                User user = userOptional.get();
                boolean isAdmin = user.isAdmin();
                user.setAdmin(!isAdmin);
                isUpdated = userDao.update(user);
                transactionManager.commit();
            }
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isUpdated;
    }

    /**
     * Changes user status.
     *
     * @param id
     * @param status
     * @return true if is changed and false otherwise.
     * @throws ServiceException
     */
    public boolean changeStatus(Long id, int status) throws ServiceException {
        boolean isUpdated = false;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            isUpdated = userDao.updateStatus(id, status);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return isUpdated;
    }

    /**
     * Searches users by login.
     *
     * @param login
     * @return {@link List} of {@link User}
     * @throws ServiceException
     */
    public List<User> searchUsersByLogin(String login) throws ServiceException {
        List<User> users;
        try {
            transactionManager.initializeTransaction();
            Connection connection = transactionManager.getConnection();
            UserDaoImpl userDao = new UserDaoImpl(connection);
            users = userDao.searchByLogin(login);
            transactionManager.commit();
        } catch (TransactionException | DaoException e) {
            transactionManager.rollback();
            throw new ServiceException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        return users;
    }

}
