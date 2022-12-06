/**
 * 
 * @author 김예은
 *
 */

package com.mab.event.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.Immutable;

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
@Table(name="EVENT")
public class EventVO {

	@Id //pk설정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVENT")
	@SequenceGenerator(sequenceName = "SEQ_EVENT", allocationSize = 1, name= "SEQ_EVENT")
	@Column(name="event_no", insertable = false, updatable = false)
	private String event_no;
	
	@Column(name="event_title")
	private String event_title;
	
	@Column(name="event_content")
	private String event_content;
	
	@Column(name="event_date")
	private String event_date;
	
	@Column(name="event_d_day")
	private String event_d_day;
	
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="user_no")
	private String user_no;
	

	@Transient
	@Column(name="vote_eod_sql")
	private Timestamp event_d_day_sql;
	
}
