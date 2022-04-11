package com.epam.filmrating.model.entity;

import java.io.Serializable;

/**
 * Genre of Film.
 *
 * @author Vladisalv Darkovich.
 */
public class Genre implements Identifiable, Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 2870689670316821374L;

    /**
     * ID.
     */
    private Long id;
    /**
     * Name.
     */
    private String name;

    /**
     * Constructor with id and name.
     *
     * @param id
     * @param name
     */
    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor with name.
     *
     * @param name
     */
    public Genre(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return id.equals(genre.id) && name.equals(genre.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Long.hashCode(id);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return String.format("Genre: {id = %d, name = %s }", id, name);
    }
}
