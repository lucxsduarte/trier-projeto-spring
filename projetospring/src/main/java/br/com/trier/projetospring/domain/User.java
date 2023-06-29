package br.com.trier.projetospring.domain;

import br.com.trier.projetospring.domain.dto.UserDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (of = "id")
@Entity (name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "user_id")
	@Setter
	private Integer id;
	
	@Column (name = "user_name")
	private String name;
	
	@Column (name = "user_email", unique = true)
	private String email;
	
	@Column (name = "user_password")
	private String password;
	
	@Column (name = "user_roles")
	private String roles;
	
	public User(UserDTO dto) {
		this(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword(), dto.getRoles());
	}
	
	public UserDTO toDto() {
		return new UserDTO(this.id, this.name, this.email, this.password, this.roles);
	}
}
