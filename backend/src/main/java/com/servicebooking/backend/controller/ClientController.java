package com.servicebooking.backend.controller;


import com.servicebooking.backend.dto.ReservationDto;
import com.servicebooking.backend.dto.ReviewDto;
import com.servicebooking.backend.services.Client.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {


    @Autowired
    private ClientServiceImpl clientService;

    @GetMapping("/ads")
    public ResponseEntity<?> allAds()
    {
        return ResponseEntity.ok(clientService.getAllAds());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> adByName(@PathVariable String name){
        return ResponseEntity.ok(clientService.searchAdByName(name));
    }

    @PostMapping("/reserve")
    public ResponseEntity<?> reserveAd(@RequestBody ReservationDto reservationDto){
        boolean flag = clientService.ReserveAd(reservationDto);
        if (flag)
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> displayAd(@PathVariable long adId){
        return ResponseEntity.ok(clientService.adDetailsForClientDto(adId));
    }

    @GetMapping("/my-bookings/{userId}")
    public ResponseEntity<?> allReservation(@PathVariable Long userId)
    {
        return ResponseEntity.ok(clientService.getAllByUserId(userId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDto reviewDto)
    {
        Boolean flag = clientService.giveReview(reviewDto);
        if (flag)
            return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
