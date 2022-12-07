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
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
@Table(name="ACTIVITY_MAIN_VIEW")
@Slf4j
public class ActivityView implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ACTIVITY")
	@SequenceGenerator(sequenceName = "SEQ_ACTIVITY",allocationSize = 1,name = "SEQ_ACTIVITY")
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="activity_image")
	private String activity_image;
	
	@Column(name="activity_name")
	private String activity_name;
	
	@Column(name="activity_info")
	private String activity_info;
	
	@Column(name="activity_city")
	private String activity_city;
	
	@Column(name="activity_county")
	private String activity_county;
	
	@Column(name="activity_interest")
	private String activity_interest;
	
	@Column(name="activity_gender")
	private String activity_gender;
	
	@Column(name="activity_nop")
	private Integer activity_nop;
	
	@Column(name="activity_age")
	private String activity_age;
	
	@Column(name="activity_date")
	private String activity_date;
	
	@Transient
	@Column(name="recruitment_stime")
	private Date recruitment_stime;
	
//	@Transient
	@Column(name="recruitment_etime")
	private Date recruitment_etime;
	
//	@Transient
	@Column(name="activity_stime")
	private Date activity_stime;
	
//	@Transient
	@Column(name="activity_etime")
	private Date activity_etime;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="private_state")
	private String private_state;
	
	@Column(name="user_nickname")
	private String user_nickname;

}
