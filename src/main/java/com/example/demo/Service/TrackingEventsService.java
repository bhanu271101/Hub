package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.TrackingEvents;
import com.example.demo.Repository.TrackingEventsRepository;

@Service
public class TrackingEventsService {


    @Autowired
    private TrackingEventsRepository trackingEventsRepository;

    public List<TrackingEvents> getTrackingEvents(Long trackingId)
    {
        return trackingEventsRepository.findAllByTrackingId(trackingId);
    }

}
