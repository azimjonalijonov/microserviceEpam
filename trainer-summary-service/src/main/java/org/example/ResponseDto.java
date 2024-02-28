package org.example;

import java.time.LocalDateTime;
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

    public Map<Long, List<PartialDTO>> getMap() {
        return map;
    }

    public void setMap(Map<Long, List<PartialDTO>> map) {
        this.map = map;
    }

    private String firstname;
    private String lastname;

    private Boolean trainerStatus = false;

    Map<Long, List<PartialDTO>> map;


}
