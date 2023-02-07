package com.shoppingTest.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "userNo")
@ToString
@Entity
@Table(name = "member")
public class Member {

    @Id
    @SequenceGenerator(name = "MEMBER_SEQUENCE_GEN", sequenceName = "seq_member", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQUENCE_GEN")
    @Column(name = "user_no")
    private Long userNo;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String userId;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String userPw;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String userName;

    @Column(length = 3,nullable = false)
    private String job;

    private int coin;

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_no")
    private List<MemberAuth> authList = new ArrayList<MemberAuth>();

    public void addAuth(MemberAuth auth){
        authList.add(auth);
    }

    public void clearAuthList() {
        authList.clear();
    }
}
