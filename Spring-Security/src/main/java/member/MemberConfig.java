package member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MemberConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	//.csrf().csrfTokenRepository(new CookieCsrfTokenRepository())
            .authorizeRequests()
	            .antMatchers("/admin/**").hasRole("ADMIN")	//Admin 권한이 있는 경우 접근 허용
	            .antMatchers("/user/**").hasRole("USER")	//User 권한이 있는 경우 접근 허용
                .antMatchers("/", "/home", "/signUp", "/signIn").permitAll() //해당 URL은 전체 접근 허용
                .anyRequest().authenticated()	//이외의 URL은 인증 절차를 수행하기 위해 login 페이지로 이동
                .and().csrf().disable()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}