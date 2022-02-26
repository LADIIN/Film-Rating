package com.epam.filmrating.model.service;

import com.epam.filmrating.exception.DaoException;
import com.epam.filmrating.exception.ServiceException;
import com.epam.filmrating.exception.TransactionException;
import com.epam.filmrating.model.dao.impl.UserDaoImpl;
import com.epam.filmrating.model.entity.User;
import com.epam.filmrating.model.pool.TransactionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private final TransactionManager transactionManager = TransactionManager.getInstance();

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
            LOGGER.error("Error caused by {}", e.getMessage());
            throw new ServiceException("Error caused by", e);
        } finally {
            transactionManager.endTransaction();
        }
        return user;
    }

    public static void main(String[] args) throws ServiceException {
        UserService userService = new UserService();
        Optional<User> user = userService.login("admin", "admin");
        user.ifPresent(System.out::println);
    }

}
