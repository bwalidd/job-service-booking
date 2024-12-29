package com.servicebooking.backend.entity;


import com.servicebooking.backend.dto.ReviewDto;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;


@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date reviewDate;

    private Long rating;

    private String review;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ad_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Ad ad;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public ReviewDto getDto(){
        ReviewDto r = new ReviewDto();
        r.setId(id);
        r.setReview(review);
        r.setRating(rating);
        r.setReviewDate(reviewDate);
        r.setUserId(user.getId());
        r.setClientName(user.getName());
        r.setAdId(ad.getId());
        r.setServiceName(ad.getServiceName());

        return r;
    }
}
