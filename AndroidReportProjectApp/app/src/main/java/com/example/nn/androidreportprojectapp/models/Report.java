package com.example.nn.androidreportprojectapp.models;

import java.util.UUID;

/**
 * Created by NN on 6.1.2016.
 */
public class Report {

    private UUID id;
    private String description;
    private String startDate;
    private String endDate;

    public Report(UUID id, String endDate, String startDate, String description) {
        this.id = id;
        this.endDate = endDate;
        this.startDate = startDate;
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

}
