package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.HubEntity;

import jakarta.transaction.Transactional;

@Repository
public interface HubRepository extends JpaRepository<HubEntity,Long>{


   List<HubEntity> findAllByHubId(Long hubId);

   HubEntity findByOrderDtoOrderId(Long orderId);

   HubEntity findByHubId(Long hubId);

   @Transactional
   @Modifying
   void deleteByOrderDtoOrderId(Long orderId);

}
