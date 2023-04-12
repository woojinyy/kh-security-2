package com.example.demo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug=true)//요청이지나가는 filter정보 확인 가능
@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled=true)//권한을 체크하겠다는 설정 추가
public class SecurityConfig {
	
	public SecurityFilterChain filterChain (HttpSecurity http)throws Exception{
		AuthenticationManagerBuilder auth=
				http.getSharedObject(AuthenticationManagerBuilder.class);
		auth.inMemoryAuthentication()
		//메소드가 데프리케이트 대상인 이유는 테스트 용도로만 사용하라는 경고의미
		//보안상 안전하지 않다
		.withUser(User.withDefaultPasswordEncoder()
				.username("user")
				.password("123")
				.roles("USER")
				)
		.withUser(User.withDefaultPasswordEncoder()
				.username("admin")
				.password("1234")
				.roles("ADMIN")
						);
		http.csrf().disable();
		http.authorizeRequests()
		//localhost:5000/user로 요쳥하면 403
		.antMatchers("/user/**").authenticated()//인증만 되면 들어갈 수 있다
		//ADMIN이나 MANAGER권한만 들어온다
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN')or hasRole('ROLE_MANAGER')")
		//ADMIN권한 있는 사람만 들어온다
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		
		.anyRequest().permitAll()//위 세가지가 아닌 경우 모두 허용
		.and()
		.formLogin()
		.loginPage("/loginForm")//2차 단위테스트
		.loginProcessingUrl("login")
		.defaultSuccessUrl("/");
		
		return http.build();
	}//end of filterChin
}


/* 테스트 시나리오
 * loclhost:5000 요청은 Role(권한)과 상관 없이 열림
 * loclhost:5000/user
 * loclhost:5000/admin
 * loclhost:5000/manager 는 로그인 페이지로 이동
 * 
 * 첫번째 =>user일때
 *  loclhost:5000/user 출력
 * loclhost:5000/admin 403
 * loclhost:5000/manage 403
 * 
 * 두번째=> admin
 *  loclhost:5000/user 403
 * loclhost:5000/admin	 출력
 * loclhost:5000/manage	출력
 * 
 * 세번째 => manager
 * * loclhost:5000/user  403
 * loclhost:5000/admin  403
 * loclhost:5000/manage  출력
 */