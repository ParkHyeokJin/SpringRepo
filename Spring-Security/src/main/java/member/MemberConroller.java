package member;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberConroller {
	
	@Autowired
	private MemberService member;
	
	@GetMapping("/")
	public String index() {
		return "/index";
	}
	
	@GetMapping("/hello")
	public String hello() {
		return "/hello";
	}
	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@GetMapping("/signUp")
	public String signUp() {
		return "/signUp";
	}
	
	@PostMapping("/create")
	public String create(Member member) {
		member.setDate(LocalDate.now());
		this.member.save(member);
		return "redirect:/login";
	}
}
