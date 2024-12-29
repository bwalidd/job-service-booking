package com.servicebooking.backend.services.Client;

import com.servicebooking.backend.dto.AdDto;

import java.util.List;

public interface ClientService {

    public List<AdDto> getAllAds();

//    public List<AdDto> findAllByServiceNameContaining(String name);
}
