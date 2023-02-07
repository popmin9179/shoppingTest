package com.shoppingTest.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(of="historyNo")
@ToString
@Entity
@Table(name="pay_coin_history")
public class PayCoin {

	@Id
	@SequenceGenerator(name = "PAY_SEQUENCE_GEN", sequenceName = "seq_pay_coin_history", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAY_SEQUENCE_GEN")
	private Long historyNo;
	
	private Long userNo;
	private Long itemId;
	
	@Transient
	private String itemName;
	
	private int amount;
	
	@CreationTimestamp
	private LocalDateTime regDate;
	@UpdateTimestamp
	private LocalDateTime updDate;

}
