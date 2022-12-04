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
@Table(name="VOTECONTENT")
public class VoteContentVO {

	@Id //pk설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VOTE_CONTENT")
	@SequenceGenerator(sequenceName = "SEQ_VOTE_CONTENT", allocationSize = 1, name= "SEQ_VOTE_CONTENT")
	@Column(name="content_no", insertable = false, updatable = false)
	private String content_no;
	
	@Column(name="vote_content")
	private String vote_content;
	
	@Column(name="vote_no")
	private String vote_no;
	
}
