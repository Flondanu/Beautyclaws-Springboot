package com.beautyclaws.beautyclaws.api;

public class APIResponseRequest {



    private int statusCode;

    private String statusDescription;

    private long createdResourceId;


    public APIResponseRequest(int statusCode, String statusDescription, long createdResourceId) {

        this.statusCode = statusCode;
        this.statusDescription = statusDescription;
        this.createdResourceId = createdResourceId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public long getCreatedResourceId() {
        return createdResourceId;
    }

    public void setCreatedResourceId(long createdResourceId) {
        this.createdResourceId = createdResourceId;
    }
}
