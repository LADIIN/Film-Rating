package com.epam.filmrating.model.entity;

import java.io.Serializable;

/**
 * Entity that represents user review on film.
 */
public class Review implements Identifiable, Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -6776244255449221551L;
    /**
     * ID database column.
     */
    public static final String ID = "id";
    /**
     * Rate database column.
     */
    public static final String RATE = "rate";
    /**
     * Content database column.
     */
    public static final String CONTENT = "content";
    /**
     * User ID database column.
     */
    public static final String USER_ID = "user_id";
    /**
     * Film ID database column.
     */
    public static final String FILM_ID = "film_id";
    /**
     * ID.
     */
    private Long id;
    /**
     * Rate.
     */
    private int rate;
    /**
     * Content.
     */
    private String content;
    /**
     * User ID.
     */
    private Long userId;
    /**
     * Film ID.
     */
    private Long filmId;
    /**
     * User.
     */
    private User user;

    /**
     * Default constructor.
     */
    public Review() {

    }

    /**
     * Constructor with parameters.
     *
     * @param id
     * @param rate
     * @param content
     * @param userId
     */
    public Review(Long id, int rate, String content, Long userId) {
        this.id = id;
        this.rate = rate;
        this.content = content;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        return rate == review.rate
                && id.equals(review.id)
                && content.equals(review.content);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Long.hashCode(id);
        result = prime * result + rate;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ id = ").append(id)
                .append(", rate = ").append(rate)
                .append(", content = ").append(content)
                .append(" }");
        return stringBuilder.toString();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    /**
     * Builder for Review.
     */
    public static class Builder {
        private Review review;

        public Builder() {
            review = new Review();
        }

        public Review build() {
            return review;
        }

        public Review.Builder setId(Long id) {
            review.setId(id);
            return this;
        }

        public Review.Builder setRate(int rate) {
            review.setRate(rate);
            return this;
        }

        public Review.Builder setContent(String content) {
            review.setContent(content);
            return this;
        }

        public Review.Builder setUserId(Long userId) {
            review.setUserId(userId);
            return this;
        }

        public Review.Builder setFilmId(Long filmId) {
            review.setFilmId(filmId);
            return this;
        }
    }

}
