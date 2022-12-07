package com.mab.meet.model;

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
@Table(name="USERINFO")
public class LoginUserInfoVO {

	@Id
	@Column(name="user_no")
	private String user_no;
	
	@Column(name="user_birth")
	private Date user_birth;
	
	@Column(name="user_gender")
	private String user_gender;
	
}
