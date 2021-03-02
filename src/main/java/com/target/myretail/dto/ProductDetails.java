package com.target.myretail.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@JsonSerialize
@Entity( name = "product_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails {

    @Id
    private Long id;

    private String name;
}
