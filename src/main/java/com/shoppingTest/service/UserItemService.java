package com.shoppingTest.service;

import java.util.List;

import com.shoppingTest.domain.Item;
import com.shoppingTest.domain.Member;
import com.shoppingTest.domain.UserItem;

public interface UserItemService {

	public void register(Member member, Item item) throws Exception;

	public UserItem read(Long userItemNo) throws Exception;

	public List<UserItem> list(Long userNo) throws Exception;
	
}
