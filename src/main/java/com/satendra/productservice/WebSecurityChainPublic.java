package com.satendra.productservice;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityChainPublic  extends WebSecurityConfigurerAdapter {

    final private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }


    /**
     * by default manager coming as inMemoryUserDetail and user are created in memory even though
     * the jdbc Authentication implemented; to override behaviour initialise  JdbcUserDetailsManager
     *
     * @param dataSource
     * @return
     */
    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        var mgr = new JdbcUserDetailsManager();
        mgr.setDataSource(dataSource); // (1)
        return mgr;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
