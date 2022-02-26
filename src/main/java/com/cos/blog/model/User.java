package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
//@DynamicInsert //insert시에 null인 컬럼을 제외함.
@Entity	//User 클래스가 MySQL에 테이블이 생성이 된다.
public class User {
	
	@Id	//Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다. Oracle:SEQUENCE, MySQL:AUTO_INCREMENT
	private int id;
	
	@Column(nullable=false, length=30, unique=true)
	private String username;
	
	@Column(nullable=false, length=100)
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	//@ColumnDefault("'user'")	//홑따옴표 -> 문자라고 인식.
	//private String role;	//Enum을 쓰는게 좋다. admin, user..
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@CreationTimestamp	//시간이 자동으로 입력
	private Timestamp createDate;
	
	
}
