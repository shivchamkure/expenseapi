package in.cody.expensetrackerapi.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class UserModel {
	
	@NotNull(message = "Please enter name")
	private String name;
	
	@NotNull(message = "Please enter email")
	@Email(message = "Please enter valid email")
	private String email;
	
	@NotNull(message = "Please enter password")
	@Size(min = 5, message = "Password should be at least 5 characters long")
	private String Password;
	
	private Long age;

	
	
	@Override
	public String toString() {
		return "UserModel [name=" + name + ", email=" + email + ", Password=" + Password + ", age=" + age + "]";
	}
	


}
