package com.servicebooking.backend.repository;

import com.servicebooking.backend.dto.AdDto;
import com.servicebooking.backend.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad,Long> {


   List<Ad> findAllByUserId(Long userId);

   public List<Ad> findAllByServiceNameContaining(String name);
}
