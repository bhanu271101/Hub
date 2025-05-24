package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.HubDTO;
import com.example.demo.DTO.OrderStatusUpdateDTo;
import com.example.demo.Service.HubService;

@RestController
public class HubController {



    @Autowired
    private HubService hubService;


    @GetMapping("/getAllOrders")
    public List<HubDTO> getAllOrders()
    {
        return hubService.getAllOrders();
    }

    @GetMapping("/cronjob")
    public String dummyForCronjob()
    {
        return "Cronjob ran successfully";
    }

    @PutMapping("/updateOrderStatus")
    public String updateOrderStatus(@RequestBody OrderStatusUpdateDTo orderStatusUpdateDTo)
    {
        return hubService.updateOrderStatus(orderStatusUpdateDTo);
    }
    
}
