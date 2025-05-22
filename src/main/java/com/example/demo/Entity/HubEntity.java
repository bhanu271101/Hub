package com.example.demo.Entity;

import com.example.demo.DTO.OrderDTO;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class HubEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private Long hubId;
    
    @Embedded
    private OrderDTO orderDto;

   

    

    public OrderDTO getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDTO orderDto) {
        this.orderDto = orderDto;
    }

    public Long getHubId() {
        return hubId;
    }

    public void setHubId(Long hubId) {
        this.hubId = hubId;
    }

   

}
