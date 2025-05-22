package com.example.demo.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TrackingEntity {


    @Id
    private Long trackingId;

    private Long orderId;

    private String orderStatus;

    private LocalDateTime estmatedDeliveryTime;

    public Long getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Long trackingId) {
        this.trackingId = trackingId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

  

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getEstmatedDeliveryTime() {
        return estmatedDeliveryTime;
    }

    public void setEstmatedDeliveryTime(LocalDateTime estmatedDeliveryTime) {
        this.estmatedDeliveryTime = estmatedDeliveryTime;
    }


}
