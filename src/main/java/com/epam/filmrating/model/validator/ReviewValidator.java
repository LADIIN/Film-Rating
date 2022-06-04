package com.epam.filmrating.model.validator;

import com.epam.filmrating.model.entity.Review;

/**
 * Validates {@link Review}.
 */
public class ReviewValidator {
    /**
     * Min rate.
     */
    private static final int MIN_RATE = 0;
    /**
     * Max rate.
     */
    private static final int MAX_RATE = 10;
    /**
     * Regex of {@link Review} content.
     */
    private static final String CONTENT_REGEX = "[\\p{L}u0-9][\\p{L}u0-9 _.,!:\"\\-'()?$]{3,1000}";

    /**
     * @param review
     * @return true if {@link Review} is valid and false otherwise.
     */
    public boolean isValid(Review review) {
        return isRateValid(review.getRate()) && isContentValid(review.getContent());
    }

    /**
     * @param rate
     * @return true if rate is valid and false otherwise.
     */
    public boolean isRateValid(int rate) {
        return rate >= MIN_RATE && rate <= MAX_RATE;
    }

    /**
     * @param content
     * @return true if content is valid and false otherwise.
     */
    public boolean isContentValid(String content) {
        return content != null && content.matches(CONTENT_REGEX);
    }
}
