package com.shoppingTest.repository;

import com.shoppingTest.domain.Pds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PdsRepository extends JpaRepository<Pds, Long> {
	
}
