package br.com.trier.projetospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projetospring.domain.User;
import br.com.trier.projetospring.domain.dto.UserDTO;
import br.com.trier.projetospring.services.UserService;

@RestController
@RequestMapping (value = "/user")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO user) {
		return  ResponseEntity.ok(service.save(new User(user)).toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		User user = new User(userDTO);
		user.setId(id);
		user = service.update(user);
		return ResponseEntity.ok(user.toDto());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
		User user = service.findById(id);
		return ResponseEntity.ok(user.toDto());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<UserDTO>> listAll() {
		return ResponseEntity.ok(service.listAll().stream().map(user -> user.toDto()).toList());
	}
	
	
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> findByNameIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name).stream().map(user -> user.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/starts/{name}")
	public ResponseEntity<List<UserDTO>> findByNameStartsWithIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name).stream().map(user -> user.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/contains/{name}")
	public ResponseEntity<List<UserDTO>> findByNameContainsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name).stream().map(user -> user.toDto()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/email/{email}")
	public ResponseEntity<List<UserDTO>> findByEmailIgnoreCase(@PathVariable String email){
		return ResponseEntity.ok(service.findByEmailIgnoreCase(email).stream().map(user -> user.toDto()).toList());
	}
}
