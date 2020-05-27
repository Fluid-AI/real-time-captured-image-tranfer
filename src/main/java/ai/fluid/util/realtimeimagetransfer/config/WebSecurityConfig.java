package ai.fluid.util.realtimeimagetransfer.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Map;

@Getter
@Setter
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties
//get the auth property from yaml
@ConfigurationProperties("auth")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //from auth.receiver
    private Map<String, String> receiver;

    //from auth.sender
    private Map<String, String> sender;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/sender/**").hasRole("SENDER")
                .antMatchers("/reciever/**").hasRole("RECEIVER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login") // to use inbuilt login page
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {

        userSetupWithRole(auth, receiver, "RECEIVER");
        userSetupWithRole(auth, sender, "SENDER");
    }

    private void userSetupWithRole(AuthenticationManagerBuilder auth, Map<String, String> userAndPass, String role) throws Exception {
        for (Map.Entry<String, String> user : userAndPass.entrySet()) {
            auth.inMemoryAuthentication()
                    .withUser(user.getKey())
                    .password(passwordEncoder().encode(user.getValue()))
                    .roles(role)
                    .and();
        }
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/img/**", "webjars/*");
    }

}