package com.example.nn.androidreportprojectapp.models;

import java.util.UUID;

/**
 * Created by NN on 6.1.2016.
 */
public class Project {

    private UUID id;
    private String name;
    private String webId;

    public Project(UUID id, String name ,String webId) {
        this.id = id;
        this.webId = webId;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebId() {
        return webId;
    }

    public void setWebId(String webId) {
        this.webId = webId;
    }
}
