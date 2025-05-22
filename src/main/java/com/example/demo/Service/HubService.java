package com.example.demo.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.DTO.HubDTO;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTO.OrderStatusUpdateDTo;
import com.example.demo.Entity.HubEntity;
import com.example.demo.Entity.HubLocations;
import com.example.demo.Entity.TrackingEntity;
import com.example.demo.Entity.TrackingEvents;
import com.example.demo.Mapper.Mapper;
import com.example.demo.Repository.HubLocationRepository;
import com.example.demo.Repository.HubRepository;
import com.example.demo.Repository.TrackingEventsRepository;
import com.example.demo.Repository.TrackingRepository;

@Service
public class HubService {

    @Autowired
    private TrackingEventsRepository trackingEventsRepository;

    @Autowired
    private HubRepository hubRepository;


    Mapper mapper=new Mapper();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TrackingRepository trackingRepository;


    @Autowired
    private HubLocationRepository hubLocationRepository;
    

    
    public Long generateRandomNumber()
    {
        Random random=new Random();
        Long number=1000000000L+ (long)(random.nextDouble() * 9000000000L);
        return number;
    }

   
    @RabbitListener(queues="hub.queue")
    public void rabbitMQListener(OrderDTO orderDto)
    {
        TrackingEntity trackIngEntity= new TrackingEntity();
        trackIngEntity.setOrderId(orderDto.getOrderId());
        trackIngEntity.setOrderStatus(orderDto.getOrderStatus());
        trackIngEntity.setEstmatedDeliveryTime(LocalDateTime.now().plusDays(1));
        trackIngEntity.setTrackingId(generateRandomNumber());
        trackingRepository.save(trackIngEntity);

        Long mobileId=orderDto.getMobileId();
        ResponseEntity<Long> hubId=restTemplate.getForEntity("https://product-0gme.onrender.com/getHubId/{mobileId}",Long.class, mobileId);

        HubEntity hubEntity=mapper.orderDTOTOHubEntity(orderDto);
        hubEntity.setHubId(hubId.getBody());
        hubRepository.save(hubEntity);
        
    }

    @RabbitListener(queues ="hub.delete.queue")
    public void deleteOrder(Long orderId)
    {
        hubRepository.deleteByOrderDtoOrderId(orderId);
        TrackingEntity trackingEntity=trackingRepository.findByOrderId(orderId);
        trackingEntity.setOrderStatus("Cancelled");
        trackingEntity.setEstmatedDeliveryTime(LocalDateTime.now());
        TrackingEvents trackingEvents=new TrackingEvents();
        trackingEvents.setTimestamp(LocalDateTime.now());
        trackingEvents.setStatus("Cancelled");
        trackingEvents.setTrackingId(trackingEntity.getTrackingId());
        trackingRepository.save(trackingEntity);
        trackingEventsRepository.save(trackingEvents);

    }


    public List<HubDTO> getAllOrders()
    {
        int[] hubIds={19645382,27984615,32175489,34981275,38512947,38910246,45871934,48931276,50921834,51728394,59031827
                        ,60391847,61238495,67584923,71298453,71459832,72461938,79284315,81245697,84321678,89374210};
        List<HubDTO> hubDTOs=new ArrayList();
        for(Integer id:hubIds)
        {
            Long hubId=id.longValue();
            List<HubEntity> hubEntity=hubRepository.findAllByHubId(hubId);
            ListIterator<HubEntity> hubEntites=hubEntity.listIterator();
        
        while(hubEntites.hasNext())
        {

            HubDTO hubDTO=mapper.hubEntityToDTO(hubEntites.next());
            hubDTOs.add(hubDTO);
        }
        }
    return hubDTOs;
        
    }

    public String updateOrderStatus(OrderStatusUpdateDTo orderStatusUpdateDTo)
    {
        String status=orderStatusUpdateDTo.getOrderStatus();
        HubEntity hubEntity=hubRepository.findByOrderDtoOrderId(orderStatusUpdateDTo.getOrderId());
        TrackingEntity trackingEntity=trackingRepository.findByOrderId(orderStatusUpdateDTo.getOrderId());
        HubLocations hubLocations=hubLocationRepository.findByHubId(hubEntity.getHubId());
        List<TrackingEvents> trackingEvent=trackingEventsRepository.findAllByTrackingId(trackingEntity.getTrackingId());
        boolean alreadyExist=false;
        boolean accpet=true;
                for (TrackingEvents trackingEventss : trackingEvent) {
                    if(trackingEventss.getLocation().equals(hubLocations.getLocationName()) && trackingEventss.getStatus().equals(status))
                    {
                        alreadyExist=true;
                    }
                    if(hubLocations.getLocationName().equals(trackingEventss.getLocation()))
                    {
                        accpet=false;
                        System.out.println(status);
                        
                    }
                }
        switch (status) {
            case "Accept":
                    if(!alreadyExist)
                    {
                        TrackingEvents trackingEvents=new TrackingEvents();
                        trackingEvents.setLocation(hubLocations.getLocationName());
                        trackingEvents.setStatus(status);
                        trackingEvents.setTimestamp(LocalDateTime.now());
                        trackingEvents.setTrackingId(trackingEntity.getTrackingId());
                        trackingEventsRepository.save(trackingEvents);
                        if (hubLocations.getNextHubId()==0) {
                        
                            hubEntity.getOrderDto().setOrderStatus("Ready for delivery");
                            hubRepository.save(hubEntity);
                            trackingEntity.setOrderStatus("Ready for delivery");
                            trackingRepository.save(trackingEntity);
                            orderStatusUpdateDTo.setOrderStatus("Ready for delivery");
                            rabbitTemplate.convertAndSend("order-exchange","order.queue",orderStatusUpdateDTo);
        
                        }
                        return "Order accepted successfully";
                    }
                break;
            case "Send":
            if(hubLocations.getNextHubId()!=0 && !accpet)
            {
                TrackingEvents trackingEvents1=new TrackingEvents();
                trackingEvents1.setLocation(hubLocations.getLocationName());
                trackingEvents1.setStatus(status);
                trackingEvents1.setTimestamp(LocalDateTime.now());
                trackingEvents1.setTrackingId(trackingEntity.getTrackingId());
                trackingEventsRepository.save(trackingEvents1); 
                hubEntity.setHubId(hubLocations.getNextHubId());
                hubRepository.save(hubEntity);
                return "Order Sent successfully";
            }
            break;

            case "Out for delivery":
            if(!hubEntity.getOrderDto().getOrderStatus().equals("Out for delivery") && hubEntity.getOrderDto().getOrderStatus().equals("Ready for delivery"))
            {
                hubEntity.getOrderDto().setOrderStatus(status);
                hubRepository.save(hubEntity);
                orderStatusUpdateDTo.setOrderStatus(status);
                trackingEntity.setOrderStatus(status);
                TrackingEvents trackingEvents1=new TrackingEvents();
                trackingEvents1.setLocation(hubLocations.getLocationName());
                trackingEvents1.setStatus(status);
                trackingEvents1.setTimestamp(LocalDateTime.now());
                trackingEvents1.setTrackingId(trackingEntity.getTrackingId());
                trackingEventsRepository.save(trackingEvents1); 
                rabbitTemplate.convertAndSend("order-exchange","order.queue",orderStatusUpdateDTo);
                return "Out for delivery";
            }
            break;
            case "Delivered":
            if(hubEntity.getOrderDto().getOrderStatus().equals("Out for delivery") && !hubEntity.getOrderDto().getOrderStatus().equals("Delivered"))
            {
                hubEntity.getOrderDto().setOrderStatus(status);
                hubRepository.save(hubEntity);
                orderStatusUpdateDTo.setOrderStatus(status);
                trackingEntity.setOrderStatus(status);
                trackingEntity.setEstmatedDeliveryTime(LocalDateTime.now());
                TrackingEvents trackingEvents1=new TrackingEvents();
                trackingEvents1.setLocation(hubLocations.getLocationName());
                trackingEvents1.setStatus(status);
                trackingEvents1.setTimestamp(LocalDateTime.now());
                trackingEvents1.setTrackingId(trackingEntity.getTrackingId());
                trackingEventsRepository.save(trackingEvents1); 
                rabbitTemplate.convertAndSend("order-exchange","order.queue",orderStatusUpdateDTo);
                return "Order delivered successfully";
            }
            break;
            default:
                break;
        }
        return "Already "+status;
        
}

    
}
