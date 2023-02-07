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

@Getter
@Setter
@EqualsAndHashCode(of = "noticeNo")
@ToString
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @SequenceGenerator(name = "NOTICE_SEQUENCE_GEN", sequenceName = "seq_notice", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_SEQUENCE_GEN")
    private Long noticeNo;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String title;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    private LocalDateTime updDate;
}
