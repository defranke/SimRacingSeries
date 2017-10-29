package com.bazinga.SimRacingSeries_Backend.model;

import org.springframework.data.annotation.Id;

public class SeriesDO {

    @Id
    private String id;

    private String slugName;

    private String name;

    public SeriesDO() {

    }

    public SeriesDO(String aName) {
        this.name = aName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName;
    }
}
