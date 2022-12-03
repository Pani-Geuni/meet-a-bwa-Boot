/**
 * @author 전판근
 */


package com.mab.meet.model;

import java.sql.Timestamp;

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
@Table(name="VOTE")
public class MeetVoteVO {

	@Id
	@Column(name="vote_no")
	private String vote_no;
	
	@Column(name="vote_title")
	private String vote_title;
	
	@Column(name="vote_content")
	private String vote_content;
	
	@Column(name="vote_eod")
	private Timestamp vote_eod;
	
	@Column(name="vote_state")
	private String vote_state;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="private_state")
	private String private_state;
}
