package com.shoppingTest.service;

import com.shoppingTest.domain.Item;

import java.util.List;

public interface ItemService {
    // 등록 처리
    public void register(Item item) throws Exception;

    // 상세 화면
    public Item read(Long itemId) throws Exception;

    // 수정 처리
    public void modify(Item item) throws Exception;

    // 삭제 처리
    public void remove(Long itemId) throws Exception;

    // 목록 화면
    public List<Item> list() throws Exception;

    // 미리보기 이미지 표시
    public String getPreview(Long itemId) throws Exception;
}
