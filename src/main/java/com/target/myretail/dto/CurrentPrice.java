package com.target.myretail.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@JsonSerialize
@Entity (name = "current_price")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentPrice {

    @Id
    @Column( name="product_id", updatable = false, nullable = false)
    private Long productId;

    @NotNull
    private Double value;

    @NotNull
    @Column( name="currency_code")
    private String currencyCode;
}
