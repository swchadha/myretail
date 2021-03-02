package com.target.myretail.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.target.myretail.dto.CurrentPrice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize
public class ProductInfo {

    private Long id;

    private String name;

    @JsonProperty("current_price")
    private Price currentPrice;
}
