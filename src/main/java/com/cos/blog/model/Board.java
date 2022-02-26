package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity	//User 클래스가 MySQL에 테이블이 생성이 된다.
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob	//대용량 데이터
	private String content;
	
	@ColumnDefault("0")
	private int count;
	
	@ManyToOne(fetch=FetchType.EAGER)	//Many=Board, User=One N:1. fetch 생략가능, 기본 fetch=FetchType.EAGER
	@JoinColumn(name="userId")
	private User user;	//DB는 오블젝트를 저장할 수 없다.(FK), 자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy="board", fetch=FetchType.EAGER)	//mappedBy 연관관계의 주인이 아니다.(DB의 컬럼을 만들지 않는다.) fetch 생략가능 기본은 fetch=FetchType.LAZY
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
