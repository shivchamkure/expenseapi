package in.cody.expensetrackerapi.entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name="tbl_users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="username")
	@NotNull(message="Username must not be 'null'")
	@Size(min=3, message="Username must be least 3 character")
	private String name;

	@Column(name="password")
	@NotNull(message="Password must not be 'null'")
	@JsonIgnore   //This will hide password in Json response while returning entity object at endpoint.
	private String Password;

	@Column(name="Email", unique = true)
	private String email;

	@Column
	private Long age;

	@Column(name="created_at", nullable=false, updatable=false)
	@CreationTimestamp
	private Timestamp createdAt;

	@Column(name="updated_at", nullable=false)
	@UpdateTimestamp
	private Timestamp updatedAt;


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", Password=" + Password + ", email=" + email + ", age=" + age
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}


}
