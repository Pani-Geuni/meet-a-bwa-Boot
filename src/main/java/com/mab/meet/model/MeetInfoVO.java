/**
 * @author 전판근
 */

package com.mab.meet.model;

import java.util.Date;

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
@Table(name="meet_join_user_view")
public class MeetInfoVO {
	
	@Id
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="meet_image")
	private String meet_image;
	
	@Column(name="meet_name")
	private String meet_name;
	
	@Column(name="meet_emblem")
	private String meet_emblem;
	
	@Column(name="meet_info")
	private String meet_info;
	
	@Column(name="meet_city")
	private String meet_city;
	
	@Column(name="meet_county")
	private String meet_county;
	
	@Column(name="meet_interest_name")
	private String meet_interest_name;
	
	@Column(name="meet_gender")
	private String meet_gender;
	
	@Column(name="meet_nop")
	private Integer meet_nop;
	
	@Column(name="meet_age")
	private Integer meet_age;
	
	@Column(name="meet_date")
	private Date meet_date;
	
	@Column(name="private_state")
	private String private_state;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="user_nickname")
	private String user_nickname;
	
	@Column(name="like_cnt")
	private String like_cnt;
	
	@Column(name="user_cnt")
	private String user_cnt;
	
}
