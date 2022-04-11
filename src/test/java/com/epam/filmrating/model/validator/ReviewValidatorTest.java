package com.epam.filmrating.model.validator;


import org.junit.Assert;
import org.junit.Test;

public class ReviewValidatorTest {
    private static final int CORRECT_RATE = 9;
    private static final int NEGATIVE_RATE = -9;
    private static final int OUT_OF_RANGE_RATE = 123;

    private static final String CORRECT_CONTENT = "There is content.";
    private static final String SHORT_CONTENT = "sd";
    private static final String BLANK_CONTENT = "    ";
    private static final String LONG_CONTENT = "ZhgCH3Gfs1ytwL7x1G8hkONjbFkSyRBs1LbI9EJGkoLgJkUHt27wgBNaa7Ci8bhMODr9ZqOw" +
            "GZdWjXq9QhaaD6O1J1nZp4RFKGNPqBauynDiNS0lSKHNdzzrHO3NbshSyfxQxALcycjbfRXPAxCerEAyuTx0IqkM7nwESIsoCFRcrGKpzKj" +
            "NtctYd6C289cuAtfBeU4sie5qrRIP55qgyqMR8QwaNgGwYgzjJqbhWHs6mq03ZGeBBUNGowfAgEBj1DGLvfJsp8w4hWNXM4ShisNpOipzy0" +
            "oUcCOjjzyMGaxcRq8UcJfvnHQy2shLuXiQ8uKOath5r9IR61B2xj28FBJ7HA5tIVz4an8Poocliimm3IW4XF13BRMIcz8z6nGD50wkPDVdF" +
            "HjufdGOpHPg0jutYO2kQ4fmcKMSuXRSAk3h0Tx0NvlLLBv7z8N1fAtOSFuNhwBrLjwwau1w5t8io6UX4rASebL0IquXYWhXPpVQaCumtDyC" +
            "XeExH5GiKB7Yl4hR8BHgBsQ6bkV373o39xeXpKgmyjDIiWfzYbymotWK3K3TM0auyvqe7lOq15V9BTM8rXEOBXQtBm9iNhYB0P6R9rPKYHc" +
            "FlEq1ybO0TiGvV0wUXzbmM4VRftDE2vNeduPS0yXHtJPXT3e1m6CIAREgCaN4Bv5t3m2i3dOyqJvNU88ClkHh8opWoG2YsghLMeL9jGPgh" +
            "lnUUg9HbDdWqyQkKF8zKtcsnbGGcRHWNTedZxBIJQjE1KChUjXGPd7KNfcvFHnnRg8mmFSvrh1U55u4GzARpQWUoabsufYypNr24REJ1SBa" +
            "otbv8UW8aN5sEZmBZnyvQnVtf4VK2st9rYA1uycGFRAPW9EbCtibPmLPx3usSvzoMG1JfSOxgXDiORAA13GNuguyRzD0VgoS8j6rPCdsIjb" +
            "7O0VLNUyYPVBEzKnPVoWBknUIa03tzRFbf9mB4GosPkkqpR2dtGnBBhb2iFjShOtbpFRpmALDsdsofodyfhuiyfheoihDIUSHAISdh";


    @Test
    public void testIsRateValidShouldReturnTrueWhenIsCorrect() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isRateValid(CORRECT_RATE);
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testIsRateValidShouldReturnFalseWhenIsNegative() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isRateValid(NEGATIVE_RATE);
        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsRateValidShouldReturnFalseWhenIsOutOfRange() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isRateValid(OUT_OF_RANGE_RATE);
        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsContentValidShouldReturnTrueWhenIsCorrect() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isContentValid(CORRECT_CONTENT);
        //then
        Assert.assertTrue(actual);
    }

    @Test
    public void testIsContentValidShouldReturnFalseWhenIsShort() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isContentValid(SHORT_CONTENT);
        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsContentValidShouldReturnFalseWhenIsNull() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isContentValid(null);
        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsContentValidShouldReturnFalseWhenIsLong() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isContentValid(LONG_CONTENT);
        //then
        Assert.assertFalse(actual);
    }

    @Test
    public void testIsContentValidShouldReturnFalseWhenIsBlank() {
        //given
        ReviewValidator reviewValidator = new ReviewValidator();
        //when
        boolean actual = reviewValidator.isContentValid(BLANK_CONTENT);
        //then
        Assert.assertFalse(actual);
    }

}


