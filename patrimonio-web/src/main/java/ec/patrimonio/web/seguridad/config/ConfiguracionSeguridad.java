package ec.patrimonio.web.seguridad.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;

/**
 * Clase para manejar la seguridad haciendo uso de <b>spring-security</b>.
 * Invalida los valores por defecto que provee <b>spring-boot</b>
 * @author jadex
 *
 */
@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ConfiguracionSeguridad extends WebSecurityConfigurerAdapter{

    @Autowired
    UserDetailsService detallesUsuario;

    @Autowired
    RememberMeServices rememberMeServices;

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
        .userDetailsService(detallesUsuario)
        .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/css/**").permitAll()
            .antMatchers("/lib/**").permitAll()
            .antMatchers("/templates/**").permitAll()
            .antMatchers("/js/**").permitAll();
//            .antMatchers("/usuarios/**").hasAuthority("ADMIN")
//            .anyRequest().authenticated()
//            .and()
//        .formLogin()
//            .loginPage("/login")
//            .failureUrl("/login?error")
//            .usernameParameter("email")
//            .permitAll()
//            .and()
//        .logout()
//            .logoutUrl("/logout")
//            .deleteCookies("remember-me")
//            .logoutSuccessUrl("/")
//            .permitAll()
//            .and()
//        .rememberMe()
//        .rememberMeServices(rememberMeServices);
    }


}
