package com.esmc.mcnp.config;

import com.esmc.mcnp.components.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] IGNORED_RESOURCE_LIST = new String[]{"/resources/**", "/webjars/**",
            "/dandelion-assets/**", "/dandelion/**", "/favicon.ico"};

    @Autowired
    CustomUserDetailService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    protected SessionRegistry sessionRegistryImpl() {
        return new SessionRegistryImpl();
    }

    @Override
    public void configure(WebSecurity security) {
        security.ignoring().antMatchers(IGNORED_RESOURCE_LIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @// @formatter:off
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .csrf()
                .ignoringAntMatchers("/releve/valider", "/bc/cmfh", "/payercmfh", "/bc/interne", "/rembourser", "/ech/**", "/ot/postuler",
                        "/budget/**", "/centre/add", "/agence/add", "/centrale/add", "/admin/groupeRoles")
                .and()
                .authorizeRequests()
                .antMatchers(IGNORED_RESOURCE_LIST).permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/", "/public/**", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .failureUrl("/login?error=1").defaultSuccessUrl("/home").usernameParameter("username")
                .passwordParameter("password").permitAll()
                .and().logout().logoutUrl("/logout")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/login").permitAll().and()
                .sessionManagement().sessionFixation().changeSessionId().maximumSessions(1)
                .maxSessionsPreventsLogin(true).sessionRegistry(this.sessionRegistryImpl());
        // @formatter:on

    }

}
