package studio.dboo.api.infra.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import studio.dboo.api.infra.auth.AuthenticationFilter;
import studio.dboo.api.infra.auth.AuthenticationEntryPoint;
import studio.dboo.api.infra.auth.handler.OAuth2AuthenticationFailureHandler;
import studio.dboo.api.infra.auth.handler.OAuth2AuthenticationSuccessHandler;
import studio.dboo.api.infra.auth.handler.TokenAccessDeniedHandler;
import studio.dboo.api.infra.auth.repository.AuthenticationRepository;
import studio.dboo.api.infra.properties.CorsProperties;
import studio.dboo.api.module.v1.member.MemberService;
import studio.dboo.api.module.v1.member.enums.RoleType;

import java.util.Arrays;

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService userService;
    private final CorsProperties corsProperties;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationFilter authenticationFilter;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;

    private final AuthenticationRepository authRepository;

    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final OAuth2AuthenticationFailureHandler failureHandler;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Auth Configure
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }
    // Web Configure
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/v2/api-docs","/webjars/**","/swagger-ui/*","/swagger**/","/swagger-resources/**");
    }
    // HTTP Configure
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 기본 HTTP 전략 설정 : cors enable && csrf, formLogin, httpBasic, session disable
        http.cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 유효하지 않은 토큰, 비인가된 요청에 대한 에러 핸들러 설정
        http
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler);

        // OAuth 인가 관련 설정
        http
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")
                .authorizationRequestRepository(authRepository)
                .and()
                .redirectionEndpoint().baseUri("/*/oauth2/code/*")
                .and()
                .successHandler(successHandler)
                .failureHandler(failureHandler);
        // 접근 권한 설정
        http.authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/api/**").hasAnyAuthority(RoleType.USER.getCode())
                .antMatchers("/api/**/admin/**").hasAnyAuthority(RoleType.ADMIN.getCode())
                .antMatchers("/api/member/sign-up", "api/member/login").permitAll()
                .anyRequest().authenticated();
        // AuthFilter 적용
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*
     * Cors 설정
     * */
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(corsConfig.getMaxAge());

        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
        return corsConfigSource;
    }
}
