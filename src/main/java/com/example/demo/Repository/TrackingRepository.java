package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.TrackingEntity;

import jakarta.transaction.Transactional;

@Repository
public interface TrackingRepository extends JpaRepository<TrackingEntity,Long>{
    


    TrackingEntity findByOrderId(Long orderId);

    @Transactional
    @Modifying
    void deleteByOrderId(Long orderId);
}
