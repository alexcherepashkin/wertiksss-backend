package ua.alexch.wertiksss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ua.alexch.wertiksss.security.AuthTokenFilter;
import ua.alexch.wertiksss.security.BasicUserDetailsService;
import ua.alexch.wertiksss.security.InsufficientAuthorityHandler;
import ua.alexch.wertiksss.security.UnauthorizedEntryPoint;

/**
 * Security configuration class.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService detailsService;
    private final AuthenticationEntryPoint unauthorizedHandler;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthTokenFilter authTokenFilter;

    @Autowired
    public WebSecurityConfig(BasicUserDetailsService userDetailsService, UnauthorizedEntryPoint unauthorizedHandler,
            InsufficientAuthorityHandler accessDeniedHandler, AuthTokenFilter authTokenFilter) {
        this.detailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authTokenFilter = authTokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .cors()
            .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(unauthorizedHandler)
                    .accessDeniedHandler(accessDeniedHandler)
            .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                    .antMatchers("/main/accounts/**", "/main/usrs/**").authenticated()
//                    .antMatchers("/main/usrs/**").hasAuthority(Role.ADMIN.getAuthority())
                    .antMatchers("/auth/**", "/**").permitAll()
                    .anyRequest().authenticated()
            .and()
                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // @formatter:on
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(detailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
