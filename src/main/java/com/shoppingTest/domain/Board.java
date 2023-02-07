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
@EqualsAndHashCode(of = "boardNo")
@ToString
@Entity
@Table(name = "board")
public class Board {
    @Id
    @SequenceGenerator(name = "BOARD_SEQUENCE_GEN", sequenceName = "seq_board", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQUENCE_GEN")
    private Long boardNo;

    @NotBlank
    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 50, nullable = false)
    private String writer;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    private LocalDateTime updDate;
}
