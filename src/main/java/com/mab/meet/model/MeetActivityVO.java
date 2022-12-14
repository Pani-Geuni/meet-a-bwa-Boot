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
@Table(name="activity_join_view")
public class MeetActivityVO {

	@Id
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
	private Date activity_date;
	
	@Column(name="recruitment_stime")
	private Date recruitment_stime;
	
	@Column(name="recruitment_etime")
	private Date recruitment_etime;
	
	@Column(name="activity_stime")
	private Date activity_stime;
	
	@Column(name="activity_etime")
	private Date activity_etime;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="private_state")
	private String private_state;
	
	@Column(name="like_cnt")
	private Integer like_cnt;
	
	@Column(name="user_cnt")
	private Integer user_cnt;
	
}
