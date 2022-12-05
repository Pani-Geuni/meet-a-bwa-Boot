package com.mab.comment.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name="BOARDCOMMENT")
public class CommentVO {

	@Id
	@Column(name="comment_no")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_comment")
	@SequenceGenerator(sequenceName = "seq_comment", allocationSize = 1, name = "seq_comment")
	private String comment_no;
	
	@Column(name="mother_no")
	private String mother_no;
	
	@Column(name="comment_date")
	private Date comment_date;
	
	@Column(name="comment_content")
	private String comment_content;
	
	@Column(name="board_no")
	private String board_no;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="meet_no")
	private String meet_no;
	
	@Column(name="delete_state")
	private String delete_state;
	
}
