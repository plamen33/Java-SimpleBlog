package blog.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
               //  .antMatchers("/", "/home","/users/register").permitAll()
              //  .antMatchers("/posts/create").authenticated()
                .anyRequest().permitAll().and();
           //     .formLogin().loginPage("/users/login").failureUrl("/users/login")
          //      .usernameParameter("username").passwordParameter("password")
          //      .and()
            //    .logout().logoutSuccessUrl("/login?logout")
            //    .and()
             //   .exceptionHandling().accessDeniedPage("/403")
              //  .and()
               // .csrf();
    }
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }
    @Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
    return new BCryptPasswordEncoder();
}
}