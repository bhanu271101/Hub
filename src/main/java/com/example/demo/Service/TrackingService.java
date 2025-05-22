package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.TrackingEntity;
import com.example.demo.Repository.TrackingRepository;

@Service
public class TrackingService {


    @Autowired
    private TrackingRepository trackingRepository;
 
    public TrackingEntity getTrackingDetails(Long orderId)
    {
        return trackingRepository.findByOrderId(orderId);
    }

}
