package com.shoppingTest.common.security.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "series")
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Id
    @Column(length = 64)
    private String series;

    @Column(length = 64)
    private String username;

    @Column(length = 64)
    private String token;

    @CreationTimestamp
    @Column(name = "last_userd")
    private Timestamp lastUsed;
}
