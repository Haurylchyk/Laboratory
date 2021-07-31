package com.epam.esm.security.config;

import com.epam.esm.entity.field.Role;
import com.epam.esm.security.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public SecurityConfiguration(@Qualifier("userServiceImpl") UserDetailsService userDetailsService,
                                 JwtTokenFilter jwtTokenFilter) {

        this.userDetailsService = userDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/users").permitAll() //TODO temporarily
                .antMatchers(HttpMethod.GET, "/tags").permitAll() //TODO temporarily

                .antMatchers(HttpMethod.GET, "/certificates/**").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users/auth").permitAll()

                .antMatchers(HttpMethod.GET, "/tags/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/users/**").hasAnyRole(Role.USER.name(), Role.ADMIN.name())

                .antMatchers("/certificates/**").hasRole(Role.ADMIN.name())
                .antMatchers("/tags/**").hasRole(Role.ADMIN.name())



                .antMatchers("/**").denyAll()

                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
