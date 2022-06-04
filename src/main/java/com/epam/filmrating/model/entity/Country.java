package com.epam.filmrating.model.entity;

import java.io.Serializable;

/**
 * Country entity.
 *
 * @author Darkovich Vladislav.
 */
public class Country implements Identifiable, Serializable {
    /**
     * ID column in database.
     */
    public static final String ID = "ID";
    /**
     * Name column in database. .
     */
    public static final String NAME = "country";

    /**
     * ID.
     */
    private Long id;
    /**
     * Name.
     */
    private String name;

    /**
     * Constructor.
     *
     * @param id
     * @param name
     */
    public Country(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Constructor without id.
     *
     * @param name
     */
    public Country(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return null;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setting name.
     *
     * @param name
     */
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
        Country country = (Country) o;
        return id.equals(country.id) && name.equals(country.name);
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
        return String.format("Country: {id = %d, name = %s", id, name);
    }
}
