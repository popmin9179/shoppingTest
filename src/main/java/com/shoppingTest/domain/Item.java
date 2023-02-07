package com.shoppingTest.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item {
    @Id
    @SequenceGenerator(name = "ITEM_SEQUENCE_GEN", sequenceName = "seq_item", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQUENCE_GEN")
    private Long itemId;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String itemName;

    private Integer price;

    @Column(length = 250)
    private String description;

    @Transient
    private MultipartFile picture;

    @Column(length = 200)
    private String pictureUrl;

    @Transient
    private MultipartFile preview;

    @Column(length = 200)
    private String previewUrl;

    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    private LocalDateTime updDate;
}
