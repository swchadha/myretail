package com.target.myretail.dao;

import com.target.myretail.dto.CurrentPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentPriceRepository extends CrudRepository<CurrentPrice, Long> {
}
