package banking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
                auth.
                jdbcAuthentication()
                .authoritiesByUsernameQuery(rolesQuery)
                .usersByUsernameQuery(usersQuery)
                .dataSource(dataSource) ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/customer-list").hasAnyAuthority("USER")
                .antMatchers("/", "/resources/**").permitAll()
                .antMatchers("/confirm").hasAuthority("ADMIN")

                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/customer-list")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();
        http
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and().csrf().disable();

        http
                .rememberMe()
                .key("myUniqueKey")
                .tokenValiditySeconds(10000000);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/img/**");
    }

}