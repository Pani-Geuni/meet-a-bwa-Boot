/**
 * 
 * @author 김예은
 *
 */

package com.mab.vote.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name="VOTE")
public class VoteVO {

	@Id //pk설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VOTE")
	@SequenceGenerator(sequenceName = "SEQ_VOTE", allocationSize = 1, name= "SEQ_VOTE")
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
	private Integer meet_no;
	
	@Column(name="activity_no")
	private Integer activity_no;
	
	@Column(name="private_state")
	private Integer private_state;
	
}
