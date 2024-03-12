package org.example.dto;

import org.example.dto.PartialDTO;

import java.util.List;
import java.util.Map;

public class ResponseDto {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Boolean getTrainerStatus() {
        return trainerStatus;
    }

    public void setTrainerStatus(Boolean trainerStatus) {
        this.trainerStatus = trainerStatus;
    }

    public List<PartialDTO> getPartialDTOS() {
        return partialDTOS;
    }

    public void setPartialDTOS(List<PartialDTO> partialDTOS) {
        this.partialDTOS = partialDTOS;
    }

    private String firstname;
    private String lastname;

    private Boolean trainerStatus = false;

    List<PartialDTO> partialDTOS;


}
