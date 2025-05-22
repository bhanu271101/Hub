package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.HubLocations;


@Repository
public interface HubLocationRepository extends JpaRepository<HubLocations,Long>{

    HubLocations findByHubId(Long hubId);


}
