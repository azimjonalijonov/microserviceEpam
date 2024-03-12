package org.example.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@CompoundIndexes({
        @CompoundIndex(name = "trainer_name_index", def = "{'firstName': 1, 'lastName': 1}")
})
public class TrainerSummaryEntity {
    @Id
    private ObjectId id;
    private String username;
    private String firstname;
    private String lastname;
    private Boolean isActive;

    public Map<String, Map<String, Number>> getSummaryList() {
        return summaryList;
    }

    public void setSummaryList(Map<String, Map<String, Number>> summaryList) {
        this.summaryList = summaryList;
    }

    Map<String, Map<String, Number>> summaryList;


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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getId() {
        return id;
    }
}
