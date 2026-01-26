package com.project.demo.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.member.repository.MemberRepositoryAdvance;
import com.project.demo.redis.RedisUserInfoService;
import com.project.demo.security.filter.JwtAuthFilter;
import com.project.demo.security.filter.LoginFilter;
import com.project.demo.security.handler.CustomLogOutHandler;
import com.project.demo.security.handler.CustomOauth2LoginFailer;
import com.project.demo.security.handler.CustomOauth2LoginSuccesser;
import com.project.demo.security.service.CustomOauth2Service;
import com.project.demo.security.service.CustomUserDetailService;
import com.project.demo.utility.jwt.JwtUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberRepositoryAdvance memberRepository;
    private final ObjectMapper objectMapper;
    private final RedisUserInfoService redisUserInfoService;
    private final JwtUtility jwtUtility;
    private final WebConfig webConfig;

    private final static String[] freePath = {
            "/member/request/signIn", "/member/logout","/mail/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception{

        security.formLogin(login->login.disable());
        security.httpBasic(httpBasic->httpBasic.disable());
        security.csrf(csrf->csrf.disable());
        security.cors(cors->cors.configurationSource(webConfig.corsConfigurationSource()));

        security.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        security.addFilterBefore(new LoginFilter(authenticationManager(),objectMapper,
                jwtUtility,redisUserInfoService), UsernamePasswordAuthenticationFilter.class);

        security.addFilterAfter(new JwtAuthFilter(redisUserInfoService,jwtUtility,memberRepository,objectMapper)
        , UsernamePasswordAuthenticationFilter.class);


        security.logout(logout->logout.logoutUrl("/member/logout")
                .invalidateHttpSession(true)
                .logoutSuccessHandler(new CustomLogOutHandler(jwtUtility,redisUserInfoService)));

        security.oauth2Login(oauth2->oauth2.userInfoEndpoint(userinfo->userinfo.userService(
                new CustomOauth2Service(memberRepository)))
                .successHandler(new CustomOauth2LoginSuccesser(jwtUtility,redisUserInfoService,objectMapper))
                .failureHandler(new CustomOauth2LoginFailer())
        );

        security.authorizeHttpRequests(auth->
                auth.requestMatchers(freePath).permitAll().anyRequest().authenticated());


        return security.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider(
                new CustomUserDetailService(memberRepository)
        );
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }


}
