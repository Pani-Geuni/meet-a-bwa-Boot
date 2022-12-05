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
@Table(name="ACTIVITYREGISTEREDMEMBER")
@Slf4j
public class ActivityRegisteredMemberVO implements Serializable { 


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ACTIVITYREGISTEREDMEMBER")
	@SequenceGenerator(sequenceName = "SEQ_ACTIVITYREGISTEREDMEMBER",allocationSize = 1,name = "SEQ_ACTIVITYREGISTEREDMEMBER")
	@Column(name="register_no")
	private String register_no;
	
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="oper_state")
	private String oper_state;
	
}
