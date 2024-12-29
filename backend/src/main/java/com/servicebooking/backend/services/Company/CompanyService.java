package com.servicebooking.backend.services.Company;

import com.servicebooking.backend.dto.AdDto;
import com.servicebooking.backend.dto.ReservationDto;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    Boolean PostAd(Long userId , AdDto adDto) throws IOException;
    public List<ReservationDto> getAllReservationByCompany(Long companyId);
}
