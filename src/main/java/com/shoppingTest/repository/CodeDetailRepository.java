package com.shoppingTest.repository;

import com.shoppingTest.domain.CodeDetail;
import com.shoppingTest.domain.CodeDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeDetailRepository extends JpaRepository<CodeDetail, CodeDetailId> {
    @Query("select max(cd.sortSeq) from CodeDetail cd where cd.groupCode = ?1")
    public List<Object[]> getMaxSortSeq(String groupCode);

    @Query("select cd from CodeDetail  cd where cd.groupCode = ?1")
    public List<CodeDetail> getCodeList(String groupCode);
}
