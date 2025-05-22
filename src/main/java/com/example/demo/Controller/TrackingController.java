package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.TrackingEntity;
import com.example.demo.Entity.TrackingEvents;
import com.example.demo.Service.TrackingEventsService;
import com.example.demo.Service.TrackingService;

@RestController
public class TrackingController {


    @Autowired
    private TrackingService trackingService;

    @Autowired
    private TrackingEventsService trackingEventsService;

    @GetMapping("/getTrackingDetails")
    public TrackingEntity getOrderTrackingDetais(@RequestParam("orderId") Long orderId)
    {
        return trackingService.getTrackingDetails(orderId);
    }

    @GetMapping("/getAllTrackingEvents")
    public List<TrackingEvents> getAllTrackingEvents(@RequestParam("trackingId") Long trackingId)
    {
        return trackingEventsService.getTrackingEvents(trackingId);
    }

}
