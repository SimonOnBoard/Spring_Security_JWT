package ru.itis.servlets.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import ru.itis.servlets.security.authentification.JwtAuthentication;
import ru.itis.servlets.security.filter.JwtAuthenticationFilter;
import ru.itis.servlets.security.provider.JwtAuthenticationProvider;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtAuthenticationProvider authenticationProvider;

    public AppSecurityConfig(JwtAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().antMatchers("/login" );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.anonymous().principal("guest").authorities("GUEST_ROLE");
        http.csrf().disable();
        http.formLogin().disable();
        http.logout().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterAt(tokenProcessingFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(new AnonymousAuthenticationFilter("twrt454"), JwtAuthenticationFilter.class);
    }

    @Bean
    public JwtAuthenticationFilter tokenProcessingFilter() throws Exception {
        JwtAuthenticationFilter tokenProcessingFilter = new JwtAuthenticationFilter();
        tokenProcessingFilter.setAuthenticationManager(authenticationManager());
        return tokenProcessingFilter;
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider).authenticationProvider(new AnonymousAuthenticationProvider("twrt454"));
    }


}