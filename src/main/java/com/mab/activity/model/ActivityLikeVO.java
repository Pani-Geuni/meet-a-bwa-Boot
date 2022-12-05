/**
 * @author 김예은
 */

package com.mab.activity.model;

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
@Table(name="ACTIVITY_LIKE")
public class ActivityLikeVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "SEQ_ACTIVITY_L")
	@SequenceGenerator(sequenceName = "SEQ_ACTIVITY_L",allocationSize = 1,name = "SEQ_ACTIVITY_L")
	@Column(name="activity_like_no")
	private String activity_like_no;
	
	@Column(name="activity_no")
	private String activity_no;
	
	@Column(name="user_no")
	private String user_no;
	
}
