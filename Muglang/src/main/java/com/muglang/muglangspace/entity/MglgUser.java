package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="T_MGLG_USER")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Data
public class MglgUser {
	@Id
	private int userId;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String phone;
	private String email;
	private String address;
	private String bio;
	private String userBanYn = "N";
	private LocalDateTime regDate = LocalDateTime.now();
	@Column
	@ColumnDefault("'ROLE_USER'")
	private String userRole;
	@Transient
	private String searchCondition;
	@Transient
	private String searchKeyword;
	@Transient
	private int reportCnt;
	
}
