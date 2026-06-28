package com.daksh.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ServiceOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String description;

    private int price;

    private int duration;
    private Long salonId;

    private Long categoryId;

    private String image;
}
