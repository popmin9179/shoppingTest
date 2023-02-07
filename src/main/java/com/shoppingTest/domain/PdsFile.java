package com.shoppingTest.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="pds_file")
public class PdsFile {

	@Id
	@SequenceGenerator(name = "PDS_FILE_SEQUENCE_GEN", sequenceName = "seq_pds_file", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PDS_FILE_SEQUENCE_GEN")
	private Long pdsFileId;
	
	@Column(length = 200)
	private String fullName;
	
	private Integer downCnt = 0;
	
	@CreationTimestamp
	private LocalDateTime regDate;
	@UpdateTimestamp
	private LocalDateTime updDate;
	
}
