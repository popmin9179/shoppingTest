package com.shoppingTest.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.shoppingTest.domain.Item;
import com.shoppingTest.domain.Member;
import com.shoppingTest.domain.PayCoin;
import com.shoppingTest.domain.UserItem;
import com.shoppingTest.exception.NotEnoughCoinException;
import com.shoppingTest.repository.MemberRepository;
import com.shoppingTest.repository.PayCoinRepository;
import com.shoppingTest.repository.UserItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserItemServiceImpl implements UserItemService {

	private final UserItemRepository userItemRepository;
	
	private final PayCoinRepository payCoinRepository;
	
	private final MemberRepository memberRepository;
	
	@Transactional
	@Override
	public void register(Member member, Item item) throws Exception {
		Long userNo = member.getUserNo();
	   
		Long itemId = item.getItemId();
		int price = item.getPrice();
		
		UserItem userItem = new UserItem();
		userItem.setUserNo(userNo);
		userItem.setItemId(itemId);
		
		PayCoin payCoin = new PayCoin();
		payCoin.setUserNo(userNo);
		payCoin.setItemId(itemId);
		payCoin.setAmount(price);
		
		Member memberEntity = memberRepository.getOne(userNo);
		
		int coin = memberEntity.getCoin();
		int amount = payCoin.getAmount();

		// 사용자의 코인이 부족한지 체크한다.
		if(coin < price) {
			throw new NotEnoughCoinException("There is Not Enough Coin.");
		}

		memberEntity.setCoin(coin - amount);
	
		memberRepository.save(memberEntity);
		
		payCoinRepository.save(payCoin);
		
		userItemRepository.save(userItem);
	}

	@Override
	public UserItem read(Long userItemNo) throws Exception {
		List<Object[]> valueArrays = userItemRepository.readUserItem(userItemNo);
		
		Object[] valueArray = valueArrays.get(0);
		
		UserItem userItem = new UserItem();
		
		userItem.setUserItemNo((Long)valueArray[0]);
		userItem.setUserNo((Long)valueArray[1]);
		userItem.setItemId((Long)valueArray[2]);
		userItem.setRegDate((LocalDateTime)valueArray[3]);
		userItem.setItemName((String)valueArray[4]);
		userItem.setPrice((int)valueArray[5]);
		userItem.setDescription((String)valueArray[6]);
		userItem.setPictureUrl((String)valueArray[7]);
		
		return userItem;
	}

	@Override
	public List<UserItem> list(Long userNo) throws Exception {
		List<Object[]> valueArrays = userItemRepository.listUserItem(userNo);
		
		List<UserItem> userItemList = new ArrayList<UserItem>();
		for(Object[] valueArray : valueArrays) {
			UserItem userItem = new UserItem();
			
			userItem.setUserItemNo((Long)valueArray[0]);
			userItem.setUserNo((Long)valueArray[1]);
			userItem.setItemId((Long)valueArray[2]);
			userItem.setRegDate((LocalDateTime)valueArray[3]);
			userItem.setItemName((String)valueArray[4]);
			userItem.setPrice((int)valueArray[5]);
			userItem.setDescription((String)valueArray[6]);
			userItem.setPictureUrl((String)valueArray[7]);
			
			userItemList.add(userItem);
		}
		
		return userItemList;
	}
	
}
