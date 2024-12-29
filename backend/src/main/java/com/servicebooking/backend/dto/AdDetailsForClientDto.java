package com.servicebooking.backend.dto;

import java.util.List;

public class AdDetailsForClientDto {

    private AdDto dto;
    private List<ReviewDto> reviewDtoList;


    public AdDto getDto() {
        return dto;
    }

    public void setDto(AdDto dto) {
        this.dto = dto;
    }

    public List<ReviewDto> getReviewDtoList() {
        return reviewDtoList;
    }

    public void setReviewDtoList(List<ReviewDto> reviewDtoList) {
        this.reviewDtoList = reviewDtoList;
    }
}
