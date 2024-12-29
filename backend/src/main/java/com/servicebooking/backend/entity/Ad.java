package com.servicebooking.backend.entity;


import com.servicebooking.backend.dto.AdDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Ads")
@Data
public class Ad {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Integer price;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    private String description;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public AdDto getDto(){
        AdDto ad = new AdDto();

        ad.setId(id);
        ad.setReturnedImg(img);
        ad.setServiceName(serviceName);
        ad.setCompany_name(user.getName());
        ad.setPrice(price);
        ad.setDescription(description);


        return ad;
    }
}
