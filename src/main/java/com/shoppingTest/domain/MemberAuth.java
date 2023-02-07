package com.shoppingTest.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "member_auth")
public class MemberAuth {

    @Id
    @SequenceGenerator(name = "MEMBER_AUTH_SEQUENCE_GEN", sequenceName = "seq_member_auth", initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_AUTH_SEQUENCE_GEN")
    private Long userAuthNo;

    @Column(name = "user_no")
    private Long userNo;

    @Column(length = 50)
    private String auth;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updDate;
}
