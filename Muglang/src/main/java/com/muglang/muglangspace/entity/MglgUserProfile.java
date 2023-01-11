package com.muglang.muglangspace.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="T_MGLG_USER_PROFILE")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@Data
public class MglgUserProfile {
    @Id
    @OneToOne
    @JoinColumn(name="USER_ID")
    private MglgUser mglgUser;
	private String userProfileNm;
	private String userProfileOriginNm;
	private String userProfilePath;
	private String userProfileCate;
	@Transient
	private String userProfileStatus;
	@Transient
	private String newUserProfileNm;
}
