package com.esmc.mcnp.config;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;

import com.esmc.mcnp.infrastructure.security.CustomRememberMeServices;
import com.esmc.mcnp.infrastructure.security.google2fa.CustomAuthenticationProvider;
import com.esmc.mcnp.infrastructure.security.google2fa.CustomWebAuthenticationDetailsSource;
import com.esmc.mcnp.infrastructure.security.location.DifferentLocationChecker;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.util.ResourceUtils;

@ComponentScan(basePackages = { "com.esmc.mcnp.infrastructure.security" })
// @ImportResource({ "classpath:webSecurityConfig.xml" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] IGNORED_RESOURCE_LIST = new String[]{"/resources/**", "/webjars/**",
    "/favicon.ico"};
	
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private LogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private CustomWebAuthenticationDetailsSource authenticationDetailsSource;

    @Autowired
    private DifferentLocationChecker differentLocationChecker;

    public SecSecurityConfig() {
        super();
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers(IGNORED_RESOURCE_LIST);
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .csrf().ignoringAntMatchers("/releve/valider", "/bc/cmfh", "/payercmfh", "/bc/interne", "/rembourser", "/ech/**", "/ot/postuler",
                    "/budget/**", "/centre/add", "/agence/add", "/centrale/add", "/admin/groupeRoles")
            .and()
            .authorizeRequests()
            .antMatchers(IGNORED_RESOURCE_LIST).permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/", "/public/**", "/index").permitAll()
            .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .failureUrl("/login?error=true")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .authenticationDetailsSource(authenticationDetailsSource)
            .permitAll()
                .and()
            .sessionManagement()
                .invalidSessionUrl("/login")
                .maximumSessions(1).maxSessionsPreventsLogin(true).sessionRegistry(sessionRegistry()).and()
                .sessionFixation().changeSessionId()
            .and()
            .logout()
                .logoutSuccessHandler(myLogoutSuccessHandler)
                .invalidateHttpSession(false)
                .logoutSuccessUrl("/logout")
                .deleteCookies("JSESSIONID")
                .permitAll()
             .and()
                .rememberMe().rememberMeServices(rememberMeServices()).key("theKey");

    // @formatter:on
    }

    // beans

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final CustomAuthenticationProvider authProvider = new CustomAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder);
        authProvider.setPostAuthenticationChecks(differentLocationChecker);
        return authProvider;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        CustomRememberMeServices rememberMeServices = new CustomRememberMeServices("theKey", userDetailsService, new InMemoryTokenRepositoryImpl());
        return rememberMeServices;
    }

    @Bean(name="GeoIPCountry")
    public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
        final File resource = ResourceUtils
                .getFile("classpath:maxmind/GeoLite2-Country.mmdb");
        return new DatabaseReader.Builder(resource).build();
    }

}
