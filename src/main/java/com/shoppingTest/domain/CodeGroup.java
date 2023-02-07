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
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode(of = "groupCode")
@Table(name = "code_group")
public class CodeGroup {

    @NotBlank
    @Id
    @Column(length = 3)
    private String groupCode;

    @NotBlank
    @Column(length = 30, nullable = false)
    private String groupName;

    @Column(length = 1)
    private String userYn = "Y";

    @CreationTimestamp
    private LocalDateTime regDate;

    @UpdateTimestamp
    private LocalDateTime updDate;

    @OneToMany
    @JoinColumn(name = "groupCode")
    private List<CodeDetail> codeDetails;
}
