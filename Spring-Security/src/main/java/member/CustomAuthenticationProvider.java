package member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        
		UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication; 
		
		UserDetails user = memberService.loadUserByUsername(authToken.getName());
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new BadCredentialsException("password error");
		}
		
		return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
