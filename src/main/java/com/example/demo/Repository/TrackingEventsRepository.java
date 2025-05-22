package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.TrackingEvents;

import jakarta.transaction.Transactional;

@Repository
public interface TrackingEventsRepository  extends JpaRepository<TrackingEvents,Long>{

    List<TrackingEvents> findAllByTrackingId(Long trackingId);

    @Transactional
    @Modifying
    void deleteAllByTrackingId(Long trackingId);

}
