package com.epam.filmrating.model.validator;

import com.epam.filmrating.model.entity.Review;

public class ReviewValidator {
    private static final int MAX_CONTENT_LENGTH = 1000;
    private static final int MIN_CONTENT_LENGTH = 3;
    private static final int MIN_RATE = 0;
    private static final int MAX_RATE = 10;

    public boolean isValid(Review review) {
        return isRateValid(review.getRate()) && isContentValid(review.getContent());
    }

    private boolean isRateValid(int rate) {
        return rate >= MIN_RATE && rate <= MAX_RATE;
    }

    private boolean isContentValid(String content) {
        return content != null && content.length() > MIN_CONTENT_LENGTH && content.length() < MAX_CONTENT_LENGTH;
    }

}
