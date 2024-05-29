package com.example.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "image")
@Table(name = "image")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sequence;
    private String userId;
    private int itemNumber;
    private String image;

    public ImageEntity(int itemNumber, String image, String userId){
        this.userId = userId;
        this.itemNumber = itemNumber;
        this.image = image;
    }
}
