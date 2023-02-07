package com.shoppingTest.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "historyNo")
@ToString
@Entity
@Table(name = "charge_coin_history")
public class ChargeCoin {
    @Id
    @SequenceGenerator(name = "CHARGE_SEQUENCE_GEN", sequenceName = "seq_charge_coin_history", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHARGE_SEQUENCE_GEN")
    private Long historyNo;

    private Long userNo;
    private int amount;

    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    private LocalDateTime updDate;
}
