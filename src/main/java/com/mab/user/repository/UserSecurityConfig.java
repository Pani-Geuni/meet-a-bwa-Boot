package com.mab.user.repository;

/**
 * @author 강경석
 */
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mab.user.service.UserService;

@EnableWebSecurity(debug = true)
@Configuration
@Order(1)
public class UserSecurityConfig {

	@Bean
	public UserDetailsService userUserDetailsService() {
		return new UserService();
	}

	// BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체 (BCrypt라는 해시 함수를 이용하여 패스워드를 암호화 한다.)
	// 회원 비밀번호 등록시 해당 메서드를 이용하여 암호화해야 로그인 처리시 동일한 해시로 비교한다.
	@Bean
	public BCryptPasswordEncoder passwordEncoder1() {
		return new BCryptPasswordEncoder();// - 생성자의 인자 값(verstion, strength, SecureRandom instance)을 통해서 해시의 강도를
		// 조절할 수 있습니다.
	}

	public void configure(WebSecurity web) throws Exception {

		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider1() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userUserDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder1());

		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
		http.authenticationProvider(authenticationProvider1());

		http.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/", "/test/", "/api/v2/**", "/v3/api-docs", "/static/**", "/swagger*/**",
						"/api/v1/auth/**", "/h2-console/**", "/favicon.ico", "/swagger-ui.html", "/swagger/**",
						"/swagger-resources/**", "webjars/**", "/v2/api-docs", "/user/insertOK", "/js/**", "/css/**",
						"/images/**", "/error")
				.permitAll() // 해당 경로들은 접근을 허용
				.antMatchers("/master/**").hasRole("M")
				.and()
				.formLogin() // 로그인 폼은
				.loginPage("/") // 해당 주소로 로그인 페이지를 호출한다.
				.loginProcessingUrl("/meet-a-bwa/m_loginOK.do") // 해당 URL로 요청이 오면 스프링 시큐리티가 가로채서 로그인처리를 한다. -> loadUserByName
				.successForwardUrl("/meet-a-bwa/loginSuccess") // 성공시 요청을 처리할 핸들러
				.failureForwardUrl("/meet-a-bwa/loginFail") // 실패시 요청을 처리할 핸들러
				.permitAll().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/meet-a-bwa/m_logoutOK.do")) // 로그아웃
																													// URL
				.logoutSuccessUrl("/") // 성공시 리턴 URL
				.invalidateHttpSession(true) // 인증정보를 지우하고 세션을 무효화
				.deleteCookies("JSESSIONID", "user_no", "user_image") // JSESSIONID 쿠키 삭제
				.permitAll();

		http.csrf().disable(); // csrf 토큰을 활성화

		return http.build();
	}
}
