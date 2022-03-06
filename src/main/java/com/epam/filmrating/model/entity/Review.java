package com.epam.filmrating.model.entity;

public class Review implements Identifiable {
    private Long id;
    private int rate;
    private String content;
    private Long userId;
    private Long filmId;
    private User user;

    public Review() {

    }

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
        return null;
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
