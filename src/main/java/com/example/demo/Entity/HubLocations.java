package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HubLocations {


    @Id
    private Long hubId;

    private String LocationName;

    private Long nextHubId;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getNextHubId() {
        return nextHubId;
    }

    public void setNextHubId(Long nextHubId) {
        this.nextHubId = nextHubId;
    }

    public Long getHubId() {
        return hubId;
    }

    public void setHubId(Long hubId) {
        this.hubId = hubId;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    

}
