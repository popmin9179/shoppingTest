package com.shoppingTest.repository;

import com.shoppingTest.domain.CodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeGroupRepository extends JpaRepository<CodeGroup, String> {
}
