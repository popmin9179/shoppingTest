package com.shoppingTest.service;

import com.shoppingTest.domain.ChargeCoin;
import com.shoppingTest.domain.PayCoin;

import java.util.List;

public interface CoinService {
    public void charge(ChargeCoin chargeCoin) throws Exception;

    public List<ChargeCoin> list(Long userNo) throws Exception;

    // 사용자의 상품 구매 내역을 반환한다.
    public List<PayCoin> listPayHistory(Long userNo) throws Exception;
}
