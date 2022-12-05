/**
 * @author 김예은
 */

package com.mab.meet.model;

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
@Table(name="MEETLIKE")
public class MeetLikeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_MEET_L")
	@SequenceGenerator(sequenceName = "SEQ_MEET_L",allocationSize = 1,name = "SEQ_MEET_L")
	@Column(name="meet_like_no")
	private String meet_like_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="user_no")
	private String user_no;
	
}
