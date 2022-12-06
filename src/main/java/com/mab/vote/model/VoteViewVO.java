/**
 * 
 * @author 김예은
 *
 */

package com.mab.vote.model;

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
//@AllArgsConstructor
//@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name="VOTEVIEW")
public class VoteViewVO {

	@Column(name="vote_no", insertable = false, updatable = false)
	private String vote_no;
	
	@Column(name="vote_title")
	private String vote_title;
	
	@Column(name="vote_info")
	private String vote_info;
	
	@Column(name="vote_eod")
	private String vote_eod;
	
	@Column(name="vote_state")
	private String vote_state;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="activity_no")
	private String activity_no;
	
	@Id //pk설정
	@Column(name="content_no")
	private String content_no;
	
	@Column(name="vote_content")
	private String vote_content;
	
	@Column(name="private_state")
	private String private_state;
	
}
