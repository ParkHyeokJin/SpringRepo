package member;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberConroller {
	
	private MemberRepository member;
	
	public MemberConroller(MemberRepository member) {
		this.member = member;
	}

	@RequestMapping("/")
	public String login() {
		return "login";
	}
	
	@PostMapping("/signIn")
	public String signIn(String inputEmail, String inputPassword) {
		log.info("id : {} , pw : {}", inputEmail, inputPassword);
		Member member = this.member.findMember(inputEmail, inputPassword);
		if(member != null) {
			return "loginOK";
		}
		return "loginFail";
	}
	
	@RequestMapping("/signUp")
	public String signUp() {
		return "/signup";
	}
	
	@RequestMapping("/signUp/create")
	public String create(Member member) {
		member.setDate(LocalDate.now());
		this.member.save(member);
		return "/login";
	}
}
