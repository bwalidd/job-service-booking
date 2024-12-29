package com.servicebooking.backend.services.Company;


import com.servicebooking.backend.dto.AdDto;
import com.servicebooking.backend.dto.ReservationDto;
import com.servicebooking.backend.entity.Ad;
import com.servicebooking.backend.entity.Reservation;
import com.servicebooking.backend.entity.User;
import com.servicebooking.backend.enums.ReservationStatus;
import com.servicebooking.backend.repository.AdRepository;
import com.servicebooking.backend.repository.ReservationRepository;
import com.servicebooking.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public Boolean PostAd(Long userId , AdDto adDto) throws IOException {
        Optional<User> u = userRepository.findById(userId);

        if(u.isPresent()){
            Ad ad = new Ad();

            ad.setId(adDto.getId());
            ad.setPrice(adDto.getPrice());
            ad.setServiceName(adDto.getServiceName());
            ad.setDescription(adDto.getDescription());
            ad.setUser(u.get());
            ad.setImg(adDto.getImg().getBytes());
            System.out.println("-------- "+adDto.getDescription());
            System.out.println("-------- "+ad.getDescription());
            adRepository.save(ad);
            return true;
        }
        return false;
    }

    public List<AdDto> getAllAds(Long userId){
        return adRepository.findAllByUserId(userId).stream().map(Ad::getDto).collect(Collectors.toList());
    }

    public AdDto getAdById(Long AdId){
        Optional<Ad> ad = adRepository.findById(AdId);
        if (ad.isPresent()){
            return ad.get().getDto();
        }
        return null;
    }

    public Boolean EditAd(Long adId,AdDto adDto) throws IOException {
        Optional<Ad> ad = adRepository.findById(adId);
        if (ad.isPresent()){
            Ad upAd = ad.get();
            upAd.setDescription(adDto.getDescription());
            upAd.setPrice(adDto.getPrice());
            upAd.setServiceName(adDto.getServiceName());
            if (adDto.getImg() != null){
                upAd.setImg(adDto.getImg().getBytes());
            }
            adRepository.save(upAd);
            return true;
        }
        return false;
    }

    public Boolean DeleteAd(Long adId)
    {
        Optional<Ad> ad = adRepository.findById(adId);
        if (ad.isPresent())
        {
            adRepository.delete(ad.get());
            return true;
        }
        return false;
    }

    public List<ReservationDto> getAllReservationByCompany(Long companyId){
        return reservationRepository.findAllByCompanyId(companyId)
                .stream().map(Reservation::getDto).collect(Collectors.toList());
    }

    public Boolean changeStatus(Long reservationId,String status){
        Optional<Reservation> opReservation = reservationRepository.findById(reservationId);

        if (opReservation.isPresent())
        {
            Reservation res = opReservation.get();
            if (Objects.equals(status,"Approved")){
                res.setReservationStatus(ReservationStatus.APPROVED);
            } else if (Objects.equals(status,"Rejected")) {
                res.setReservationStatus(ReservationStatus.REJECTED);
            }
            reservationRepository.save(res);
            return true;
        }
        return false;
    }


}
