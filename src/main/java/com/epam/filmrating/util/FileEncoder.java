package com.epam.filmrating.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

/**
 * Encodes file with {@link Base64} encoder.
 */
public class FileEncoder {

    /**
     * Encodes file to String.
     *
     * @param filepath
     * @return encoded file.
     */
    public String encode(String filepath) {
        String encodedString = null;
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(filepath));
            encodedString = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedString;
    }
}
