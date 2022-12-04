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
@Table(name="VOTERESULT")
public class VoteResultVO {

	@Id //pk설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VOTE_RESULT")
	@SequenceGenerator(sequenceName = "SEQ_VOTE_RESULT", allocationSize = 1, name= "SEQ_VOTE_RESULT")
	@Column(name="vote_result_no", insertable = false, updatable = false)
	private String vote_result_no;
	
	@Column(name="vote_no")
	private String vote_no;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="content_no")
	private String content_no;
	
}
