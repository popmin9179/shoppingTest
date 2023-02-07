package com.shoppingTest.repository;

import com.shoppingTest.domain.ChargeCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChargeCoinRepository extends JpaRepository<ChargeCoin, Long> {
}
