package net.velvetduck.quotesapp.configuration;

import net.velvetduck.quotesapp.user.SpringUserDetailsService;
import net.velvetduck.quotesapp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigure ->
                        httpSecurityHeadersConfigure.frameOptions(
                                frameOptionsConfig -> {
                                    frameOptionsConfig.disable();
                                    frameOptionsConfig.sameOrigin();
                                }))
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .antMatchers("/").permitAll()
                                .antMatchers("/homePage").hasRole("USER")
                                .antMatchers("/admin").hasRole("ADMIN"))
                .httpBasic(Customizer.withDefaults())
                .formLogin(httpSecurityFormLoginConfigure -> {
                    httpSecurityFormLoginConfigure.defaultSuccessUrl("/homePage");
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer.logoutUrl("/logout");
                    httpSecurityLogoutConfigurer.logoutSuccessUrl("/");
                })
                .authenticationManager(getAuthenticationManager())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SpringUserDetailsService();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        DelegatingPasswordEncoder delPasswordEncoder=  (DelegatingPasswordEncoder) PasswordEncoderFactories.createDelegatingPasswordEncoder();
        BCryptPasswordEncoder bcryptPasswordEncoder =new BCryptPasswordEncoder();
        delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
        return delPasswordEncoder;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(getPasswordEncoder());

        return new ProviderManager(authenticationProvider);
    }


}