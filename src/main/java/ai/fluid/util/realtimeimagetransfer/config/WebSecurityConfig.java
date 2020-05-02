package ai.fluid.util.realtimeimagetransfer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
        Set<String> users = Stream.of(
                "imran", "jayesh", "parag",
                "shubham", "noman", "ashwin",
                "azhar", "yuvaraj", "shahanawaz")
                .collect(Collectors.toSet());

        for (String user : users) {
            auth.inMemoryAuthentication()
                    .withUser(user)
                    .password(passwordEncoder().encode(user + "@123"))
                    .roles("RECEIVER")
                    .and();
        }

        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("password")).roles("RECEIVER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("SENDER");
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