package com.mab.main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name="MAINMEETVIEW")
public class MainMeetViewVO {

	@Id	// PK 설정
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="meet_image")
	private String meet_image;
	
	@Column(name="meet_name")
	private String meet_name;
	
	@Column(name="meet_info")
	private String meet_info;
	
	@Column(name="meet_county")
	private String meet_county;
	
	@Column(name="meet_interest_name")
	private String meet_interest_name;
	
	@Column(name="meet_gender")
	private String meet_gender;
	
	@Column(name="meet_nop")
	private Integer meet_nop;
	
	@Column(name="meet_age")
	private String meet_age;
	
	@Column(name="meet_date")
	private String meet_date;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="like_cnt")
	private String like_cnt;
	
	@Column(name="user_cnt")
	private Integer user_cnt;
	
	@Transient
	@Column(name="meet_age_arr")
	private String[] meet_age_arr;
	
	@Transient
	@Column(name="like_meet_list")
	private String[] like_meet_list;
	
}
