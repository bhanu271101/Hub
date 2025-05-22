package com.example.demo.Mapper;

import com.example.demo.DTO.HubDTO;
import com.example.demo.DTO.OrderDTO;
import com.example.demo.Entity.HubEntity;

public class Mapper {



    public HubEntity orderDTOTOHubEntity(OrderDTO orderDTO)
    {
        HubEntity hubEntity=new HubEntity();
        hubEntity.setOrderDto(orderDTO);
        return hubEntity;
    }

    public HubDTO hubEntityToDTO(HubEntity hubEntity)
    {
        HubDTO hubDTO=new HubDTO();
        hubDTO.setOrderDto(hubEntity.getOrderDto());
        return hubDTO;
    }

}
