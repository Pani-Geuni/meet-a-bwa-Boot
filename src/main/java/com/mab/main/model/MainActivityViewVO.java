package com.mab.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Immutable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "MAINACTIVITYVIEW")
public class MainActivityViewVO {

	@Id	// PK 설정
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="activity_image")
	private String activity_image;
	
	@Column(name="activity_name")
	private String activity_name;
	
	@Column(name="activity_info")
	private String activity_info;
	
	@Column(name="activity_county")
	private String activity_county;
	
	@Column(name="activity_interest")
	private String activity_interest;
	
	@Column(name="activity_gender")
	private String activity_gender;
	
	@Column(name="activity_nop")
	private Integer activity_nop;
	
	@Column(name="activity_age")
	private Integer activity_age;
	
	@Column(name="activity_date")
	private String activity_date;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="like_cnt")
	private Integer like_cnt;
	
	@Column(name="user_cnt")
	private Integer user_cnt;
	
}
