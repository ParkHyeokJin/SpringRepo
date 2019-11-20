package member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
	
	private MemberRepository memberRepository;
	
	public MemberController(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@RequestMapping("/GET")
	private String login(@RequestParam(value="email") String email, @RequestParam(value="password") String password) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<>();
		
		Member member = memberRepository.findMember(email, password);
		map.put("email", email);
		if(member == null) {
			map.put("result", "로그인 정보가 없습니다.");
		}else {
			map.put("result", member.getName() + " 님 반갑습니다.");
		}
		return mapper.writeValueAsString(map);
	}
	
	@RequestMapping("/PUT")
	private Member create(@RequestParam(value="email") String email, @RequestParam(value="password") String password, @RequestParam(value="name") String name) {
		Member member = new Member(email, password, name);
		member.setDate(LocalDate.now());
		memberRepository.save(member);
		return member;
	}
	
	@RequestMapping("/DELETE")
	private String delete(@RequestParam(value="email") String email, @RequestParam(value="password") String password) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = new HashMap<>();
		
		Member member = memberRepository.findMember(email, password);
		map.put("email", email);
		
		if(member == null) {
			map.put("result", "회원 정보가 없습니다.");
		}else {
			memberRepository.delete(member);
			map.put("result", "정상적으로 삭제되었습니다.");
		}
		return mapper.writeValueAsString(map);
	}
	
}
