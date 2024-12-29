package com.servicebooking.backend.controller;


import com.servicebooking.backend.dto.AdDto;
import com.servicebooking.backend.dto.ReservationDto;
import com.servicebooking.backend.entity.Ad;
import com.servicebooking.backend.services.Company.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyServiceImpl companyService;

    @PostMapping("/ad/{userId}")
    public ResponseEntity<?> postAd(@PathVariable Long userId, @ModelAttribute AdDto adDto) throws IOException {
        Boolean post = companyService.PostAd(userId,adDto);
        if (post)
            return ResponseEntity.status(HttpStatus.OK).build();
        else
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

    }

    @GetMapping("/all-ads/{user}")
    public ResponseEntity<?> getAllAdsByUserId(@PathVariable Long user){
        return ResponseEntity.ok(companyService.getAllAds(user));
    }

    @GetMapping("/ad/{id}")
    public ResponseEntity<?> getAdById(@PathVariable Long id){
        AdDto ad = companyService.getAdById(id);
        if (ad != null)
        {
            return ResponseEntity.ok(ad);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/edit-ad/{id}")
    public ResponseEntity<?> editAd(@PathVariable Long id,@ModelAttribute AdDto adDto) throws IOException {
        boolean success = companyService.EditAd(id,adDto);
        if (success)
            return ResponseEntity.ok(adDto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete-ad/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        Boolean flag = companyService.DeleteAd(id);
        if (flag)
            return ResponseEntity.ok("Ad deleted");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<List<ReservationDto>> getAllReservation(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getAllReservationByCompany(id));
    }

    @GetMapping("/book-status/{resId}/{status}")
    public ResponseEntity<?> changeStatus(@PathVariable Long resId,@PathVariable String status)
    {
        Boolean flag = companyService.changeStatus(resId,status);
        if (flag)
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
