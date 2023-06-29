package br.com.trier.projetospring.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projetospring.domain.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findByNameIgnoreCase(String name);
	List<User> findByNameContainsIgnoreCase(String name);
	List<User> findByNameStartsWithIgnoreCase(String name);
	List<User> findByEmailIgnoreCase(String name);
	Optional<User> findByName(String name);
	Optional<User> findByEmail (String email);
	
}
