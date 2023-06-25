package com.laconics.schoolService.DTO.save;

public class TicketSaveDTO {

    private Long part;


    private String description;

    public Long getPart() {
        return part;
    }

    public void setPart(Long part) {
        this.part = part;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
