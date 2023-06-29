package br.com.trier.projetospring.services;

import java.util.List;

import br.com.trier.projetospring.domain.User;

public interface UserService {
	
	User save(User user);
	User update(User user);
	void delete (Integer id);
	User findById(Integer id);
	List<User> listAll();
	List<User> findByNameIgnoreCase(String name);
	List<User> findByNameContainsIgnoreCase(String name);
	List<User> findByNameStartsWithIgnoreCase(String name);
	List<User> findByEmailIgnoreCase(String name);
	
}