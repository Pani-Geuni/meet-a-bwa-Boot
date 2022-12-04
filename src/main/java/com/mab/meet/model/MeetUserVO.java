/**
 * @author 전판근
 */


package com.mab.meet.model;

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
@Table(name="MEETREGISTEREDMEMBER")
public class MeetUserVO {

	@Id
	@Column(name="register_no")
	private String register_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="user_nickname")
	private String user_nickname; 
	
	@Column(name="oper_state")
	private String oper_state;
}
