package com.mab.activity.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Immutable
@Table(name="ACTIVITY_VOTE_LIST_VIEW")
@Slf4j
public class ActivityVoteListView implements Serializable{
	
	@Id
	@Column(name="vote_no")
	private String vote_no;
	
	@Column(name="vote_state")
	private String vote_state;
	
	@Column(name="vote_info")
	private String vote_info;
	
	@Column(name="vote_eod")
	private String vote_eod;
	
	@Column(name="vote_title")
	private String vote_title;
	
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="user_cnt")
	private Integer user_cnt;
	

}
