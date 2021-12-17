package studio.dboo.api.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import studio.dboo.api.infra.auth.AuthFilter;
import studio.dboo.api.infra.auth.token.AuthTokenProvider;

@Configuration
@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthTokenProvider tokenProvider;

    /** Bean injection */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    }

    /*
     * 토큰 필터 설정
     * */
    @Bean
    public AuthFilter tokenAuthenticationFilter() {
        return new AuthFilter(tokenProvider);
    }

    // SWAGGER를 위해 webSecurity 설정
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/v2/api-docs","/webjars/**","/swagger-ui/*","/swagger**/","/swagger-resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Jwt 적용
        http.cors().disable();

    }
}
