package com.laconics.schoolService.DTO.save;

import com.laconics.schoolService.entity.Status;

public class TicketDisplayDTO {
    private String description;
    private Status status;
    private String part;
    private String user;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
