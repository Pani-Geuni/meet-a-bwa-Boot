package com.mab.meetboard.model;

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
@Table(name="MEETBOARD")
public class MBUserVO {

	@Id
	@Column(name="board_no")
	private String board_no;
	
	@Column(name="board_title")
	private String board_title;
	
	@Column(name="board_content")
	private String board_content;
	
	@Column(name="board_image")
	private String board_image;
	
	@Column(name="board_date")
	private Date board_date;
	
	@Column(name="notice_state")
	private String notice_state;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="user_name")
	private String user_name;
}
