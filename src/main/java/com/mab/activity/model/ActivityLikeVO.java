package com.mab.activity.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="ACTIVITY_LIKE")
@Slf4j
public class ActivityLikeVO implements Serializable { 


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ACTIVITY_L")
	@SequenceGenerator(sequenceName = "SEQ_ACTIVITY_L",allocationSize = 1,name = "SEQ_ACTIVITY_L")
	@Column(name="activity_like_no")
	private String activity_like_no;
	
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="user_no")
	private String user_no;
	
}
