package com.example.demo.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;

@Entity//jpa사용시 테이블 생성해준다 확인할 것= application.yml create 모드인지 확인
@Data//getter,setter생성해줌
public class User {
	@Id //primary key
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String username; //username sprigsecurity에서 인터셉트하기때문에 username은 바꾸면 안된다
	private String password;
	private String email;
	private String role;//ROLE_USER,ROLE_MANAGER,ROLE_ADMIN
	@CreationTimestamp
	private Timestamp createDate;
	//jpa에서는 쿼리문 안쓴다 
	//회원가입에 사용할 생성자 추가
	@Builder//순서 필요 없어 전체필드 다 안써도 괜찮아 
	public User(String username, String password, String email, String role, Timestamp createDate) {
		this.username = username;
		this.password= password;
		this.email=email;
		this.role=role;
		this. createDate=createDate;
	}
}
