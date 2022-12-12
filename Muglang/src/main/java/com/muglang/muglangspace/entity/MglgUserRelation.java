package com.muglang.muglangspace.entity;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_MGLG_USER_RELATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert

public class MglgUserRelation implements Serializable{
	@Id
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private MglgUser mglgUser;
	
	private int followerId;
	private String followDate;
}
