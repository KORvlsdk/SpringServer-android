package com.example.ch7_8_test.config;

import com.example.ch7_8_test.service.MemberService;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FirebaseAuth firebaseAuth;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //앱, 통신 테스트 용으로 잠시 끄기
        http.csrf().disable();
        // 시큐리티 문법 형식 3.2.
//           .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/**").authenticated()
////                                .requestMatchers("/**").permitAll()
//        )
//                .addFilterBefore(new FirebaseTokenFilter(userDetailsService, firebaseAuth),
//                        UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(configurer -> configurer
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                );
//        http.formLogin()
//                .loginPage("/members/login")
//                .defaultSuccessUrl("/")
//                .usernameParameter("email")
//                .failureUrl("/members/login/error")
//                .and()
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
//                .logoutSuccessUrl("/")
//        ;
//
        // 시큐리티 문법 형식 2.5.2
        http.authorizeRequests()
//                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll()
                .mvcMatchers("/**").authenticated()
//                .mvcMatchers("/admin/**").hasRole("ADMIN")
                // 웹에서는 인증시
//                .anyRequest().authenticated()
                // 앱에서 통신만 확인용으로 사용.
//                .anyRequest().permitAll()
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

}