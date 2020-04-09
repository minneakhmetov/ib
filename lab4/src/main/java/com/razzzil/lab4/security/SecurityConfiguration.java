package com.razzzil.lab4.security;

import com.razzzil.lab4.models.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.List;

import static com.razzzil.lab4.controllers.MainController.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        users.add(User.withDefaultPasswordEncoder()
                .username("katya")
                .password("katya")
                .roles(Role.GUEST.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("sasha")
                .password("sasha")
                .roles(Role.STUDENT.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("lesha")
                .password("lesha")
                .roles(Role.STUDENT.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("dasha")
                .password("dasha")
                .roles(Role.STUDENT.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("serega")
                .password("serega")
                .roles(Role.STUDENT.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("dmitry")
                .password("dmitry")
                .roles(Role.TEACHER.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("vladislava")
                .password("vladislava")
                .roles(Role.TEACHER.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles(Role.ADMIN.name())
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("manager")
                .password("manager")
                .roles(Role.MANAGER.name())
                .build());
        return new InMemoryUserDetailsManager(users);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers(RESOURCES_URL_PREFIX + "/{\\d+}")
                        .hasAnyRole(this.transformRole(Role.TEACHER), this.transformRole(Role.MANAGER), this.transformRole(Role.ADMIN), this.transformRole(Role.GUEST), this.transformRole(Role.STUDENT))
                    .antMatchers(HttpMethod.GET, DELETE_URL_PREFIX + "/{\\d+}")
                        .hasAnyRole(this.transformRole(Role.ADMIN))
                    .antMatchers(HttpMethod.GET, INIT + MODIFY_URL_PREFIX + "/{\\d+}")
                        .hasAnyRole(this.transformRole(Role.ADMIN), this.transformRole(Role.MANAGER))
                    .antMatchers(HttpMethod.GET,MODIFY_URL_PREFIX + "/{\\d+}")
                        .hasAnyRole(this.transformRole(Role.ADMIN), this.transformRole(Role.MANAGER))
                    .antMatchers(HttpMethod.GET, INIT + ADD_URL_PREFIX + "/{\\d+}")
                        .hasAnyRole(this.transformRole(Role.ADMIN), this.transformRole(Role.MANAGER), this.transformRole(Role.TEACHER))
                    .antMatchers(HttpMethod.GET, ADD_URL_PREFIX + "/{\\d+}")
                        .hasAnyRole(this.transformRole(Role.ADMIN), this.transformRole(Role.MANAGER), this.transformRole(Role.TEACHER))
                    .antMatchers("/")
                        .authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/login")
                .permitAll();
        ;
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    private String transformRole(Role role){
        return role.name();
    }





}
