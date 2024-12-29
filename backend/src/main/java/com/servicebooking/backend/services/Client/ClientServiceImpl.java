package com.servicebooking.backend.services.Client;


import com.servicebooking.backend.dto.AdDetailsForClientDto;
import com.servicebooking.backend.dto.AdDto;

import com.servicebooking.backend.dto.ReservationDto;
import com.servicebooking.backend.dto.ReviewDto;
import com.servicebooking.backend.entity.Ad;
import com.servicebooking.backend.entity.Reservation;
import com.servicebooking.backend.entity.Review;
import com.servicebooking.backend.entity.User;
import com.servicebooking.backend.enums.ReservationStatus;
import com.servicebooking.backend.enums.ReviewStatus;
import com.servicebooking.backend.repository.AdRepository;
import com.servicebooking.backend.repository.ReservationRepository;
import com.servicebooking.backend.repository.ReviewRepository;
import com.servicebooking.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private AdRepository adRepository;


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<AdDto> getAllAds(){
        return adRepository.findAll().stream().map(Ad::getDto).collect(Collectors.toList());
    }

    public List<AdDto> searchAdByName(String name){
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getDto).collect(Collectors.toList());
    }


    public Boolean ReserveAd(ReservationDto reservationDto){
        Optional<Ad> opAd = adRepository.findById(reservationDto.getAdId());
        Optional<User> opUser = userRepository.findById(reservationDto.getUserId());

        if (opAd.isPresent() && opUser.isPresent()){
            Reservation r = new Reservation();

            r.setBookDate(reservationDto.getBookDate());
            r.setUser(opUser.get());
            r.setReservationStatus(ReservationStatus.PENDING);

            r.setAd(opAd.get());
            r.setCompany(opAd.get().getUser());
            r.setReviewStatus(ReviewStatus.FALSE);

            reservationRepository.save(r);
            return true;

        }
        return false;
    }

    public AdDetailsForClientDto adDetailsForClientDto(Long adId)
    {
        Optional<Ad> opAdDto = adRepository.findById(adId);
        AdDetailsForClientDto ad = new AdDetailsForClientDto();
        if (opAdDto.isPresent()){
            ad.setDto(opAdDto.get().getDto());

            List<Review> reviewList = reviewRepository.findAllByAdId(adId);
            ad.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));

        }
        return ad;
    }


    public List<ReservationDto> getAllByUserId(Long userId)
    {
        return reservationRepository.findAllByUserId(userId).stream().map(Reservation::getDto).collect(Collectors.toList());
    }

    public Boolean giveReview(ReviewDto reviewDto)
    {
        Optional<User> optionalUser = userRepository.findById(reviewDto.getUserId());
        Optional<Reservation> optionalbooking = reservationRepository.findById(reviewDto.getBookId());

        if (optionalbooking.isPresent() && optionalUser.isPresent())
        {
            Review r = new Review();
            r.setReviewDate(new Date());
            r.setReview(reviewDto.getReview());
            r.setRating(reviewDto.getRating());

            r.setUser(optionalUser.get());
            r.setAd(optionalbooking.get().getAd());

            reviewRepository.save(r);

            Reservation booking = optionalbooking.get();
            booking.setReviewStatus(ReviewStatus.TRUE);

            reservationRepository.save(booking);
            return true;
        }
        return false;
    }
}
