package member;

import java.security.Principal;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@RequestMapping("/user")
	@ResponseBody
	public Principal user(Principal principal) {
		return principal;
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		log.info("logout=============================================");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.setAuthenticated(false);
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        SecurityContextHolder.clearContext();
        request.logout();
        request.getSession().invalidate();
    }
}
