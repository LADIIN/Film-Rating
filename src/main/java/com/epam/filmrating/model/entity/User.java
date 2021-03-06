package com.epam.filmrating.model.entity;

import java.io.Serializable;

/**
 * User entity.
 */
public class User implements Identifiable, Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 3472428246668392254L;
    /**
     * ID database column.
     */
    public static final String ID = "id";
    /**
     * Login database column.
     */
    public static final String LOGIN = "login";
    /**
     * Email database column
     */
    public static final String EMAIL = "email";
    /**
     * Status database column.
     */
    public static final String STATUS = "status";
    /**
     * Is admin database column.
     */
    public static final String IS_ADMIN = "is_admin";
    /**
     * Is blocked database column.
     */
    public static final String IS_BLOCKED = "is_blocked";

    private Long id;
    private String login;
    private String email;
    private int status;
    private boolean isAdmin;
    private boolean isBlocked;


    /**
     * Constructor with parameters.
     * @param id
     * @param login
     * @param email
     * @param isAdmin
     * @param isBlocked
     */
    public User(Long id, String login, String email, boolean isAdmin, boolean isBlocked) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isBlocked = isBlocked;
    }

    /**
     * Default constructor.
     */
    public User() {

    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && status == user.status
                && isAdmin == user.isAdmin
                && isBlocked == user.isBlocked
                && login.equals(user.login)
                && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Long.hashCode(id);
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + status;
        result = prime * result + Boolean.hashCode(isAdmin);
        result = prime * result + Boolean.hashCode(isBlocked);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{id = ").append(id)
                .append(", login = ").append(login)
                .append(", email = ").append(email)
                .append(", status = ").append(status)
                .append(", isAdmin = ").append(isAdmin)
                .append(", isBlocked = ").append(isBlocked)
                .append("}");
        return stringBuilder.toString();

    }

    /**
     * Builder of User.
     */
    public static class Builder {
        private User user;

        public Builder() {
            user = new User();
        }

        public User build() {
            return user;
        }

        public Builder setId(long id) {
            user.setId(id);
            return this;
        }

        public Builder setLogin(String login) {
            user.setLogin(login);
            return this;
        }

        public Builder setEmail(String email) {
            user.setEmail(email);
            return this;
        }

        public Builder setStatus(int status) {
            user.setStatus(status);
            return this;
        }

        public Builder setAdmin(boolean isAdmin) {
            user.setAdmin(isAdmin);
            return this;
        }

        public Builder setBlocked(boolean isBlocked) {
            user.setBlocked(isBlocked);
            return this;
        }
    }

}
