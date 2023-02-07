package com.shoppingTest.service;

import com.shoppingTest.domain.Member;

import java.util.List;

public interface MemberService {

    public long countAll() throws Exception;

    public void setupAdmin(Member member) throws Exception;

    public void register(Member member) throws Exception;

    public Member read(Long userNo) throws Exception;

    public void modify(Member member) throws Exception;

    public void remove(Long userNo) throws Exception;

    public List<Member> list() throws Exception;

    // 사용자 보유 코인을 반환한다.
    public int getCoin(Long userNo) throws Exception;
}
