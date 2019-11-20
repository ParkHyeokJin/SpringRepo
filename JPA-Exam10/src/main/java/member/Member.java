package member;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Member {
	
	@Id @GeneratedValue
	private long seq;
	
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	
	private String name;
	
	@Column(name = "RegDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	public Member(@NotEmpty String email, @NotEmpty String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}
}
