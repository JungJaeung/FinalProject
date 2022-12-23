package com.muglang.muglangspace.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity										//테이블에 값을 넣어줌 // JAP가 관리
@Table(name="T_MGLG_USER")					//테이블명 = T_MGLG_USER
@NoArgsConstructor							//기본 생성자
@AllArgsConstructor							//필드를 모두 받는 생성자
@Builder									//객체 따로 안만들고 자유롭게 값 세팅 가능 / lombok
@DynamicInsert								//DEFAULT 지정 가능	
@Data										//SETTER, GETTER, toString() 만들어 줌 / lombok
@SequenceGenerator(							//식별자 할당
		name="MglgUserSequenceGenarator",	//MySQL 시퀀스 전략명
		sequenceName="T_MGLG_USER_SEQ",		//시퀀스 전략 테이블명
		initialValue=1,						//시작값=1
		allocationSize=1					//할당값=1 => 1씩 증가
)
public class MglgUser {
	@Id
	@GeneratedValue(								//userId에 시퀀스 전략 사용
			strategy=GenerationType.SEQUENCE,		//제네레이션 타입은 시퀀스로
			generator="MglgUserSequenceGenarator"	//제네레이터는 MglgUserSquenceGenatator 사용
	)
	private int userId;								//사용자 구분을 위한 아이디, PRIMARYKEY
	private String userName;						//사용자 닉네임
	private String password;						//패스워드 없음
	private String firstName;						//사용자 이름
	private String lastName;						//사용자 성 
	private String phone;							//사용자 전화번호
	private String email;							//사용자 이메일
	private String address;							//사용자 주소
	private String bio;								//사용자 상태 메세지
	private String userBanYn = "N";					//사용자 추방 유무
	private LocalDateTime regDate;					//사용자 가입 날짜
	@Column
	@ColumnDefault("'ROLE_USER'")		
	private String userRole;						//사용자 권한
	private String userSnsId;						//소셜 로그인 업체가 제공한 아이디
	
	@Transient
	private String searchCondition;					//검색 조건
	@Transient
	private String searchKeyword;					//검색 키워드
	@Transient
	private int reportCnt;							//신고당한 횟수
	
}
