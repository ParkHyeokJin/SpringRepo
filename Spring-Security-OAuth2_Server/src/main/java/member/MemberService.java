package member;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService implements UserDetailsService{

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String inputEmail) throws UsernameNotFoundException {
		
		//회원 이름으로 회원을 조회 한다.
		Member member = memberRepository.findByEmail(inputEmail);	

		//회원정보 권한에 따라서 권한을 부여한다.
		List<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
		if(("admin@email.com").equals(inputEmail)) {
			auth.add(new SimpleGrantedAuthority("ADMIN"));
		}else {
			auth.add(new SimpleGrantedAuthority("USER"));
		}
		
		return new User(member.getEmail(), member.getPassword(), auth);
	}
	
	//회원을 저장한다. 
	public Member save(Member member) {
		//비밀번호 암호화
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		return memberRepository.save(member);
	}
}
