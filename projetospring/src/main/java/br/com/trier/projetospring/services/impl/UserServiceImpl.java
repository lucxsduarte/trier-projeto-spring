package br.com.trier.projetospring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projetospring.domain.User;
import br.com.trier.projetospring.repositories.UserRepository;
import br.com.trier.projetospring.services.UserService;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;

	@Override
	public User save(User user) {
		validaEmail(user);
		return repository.save(user);
	}

	@Override
	public User update(User user) {
		findById(user.getId());
		validaEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public User findById(Integer id) {
		Optional<User> objeto = repository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFound("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public List<User> listAll() {
		List<User> lista = repository.findAll();
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhum usuário encontrado");
		}
		return lista;
	}

	@Override
	public List<User> findByNameIgnoreCase(String name) {
		List<User> lista = repository.findByNameIgnoreCase(name);
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhum usuário se chama %s".formatted(name));
		}
		return lista;
	}

	@Override
	public List<User> findByNameContainsIgnoreCase(String name) {
		List<User> list = repository.findByNameContainsIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum usuário cadastrado contem no nome: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<User> findByNameStartsWithIgnoreCase(String name) {
		List<User> list = repository.findByNameStartsWithIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum usuário cadastrado com o nome que começa com: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<User> findByEmailIgnoreCase(String name) {
		List<User> list = repository.findByEmailIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum usuário cadastrado com o email: %s".formatted(name));
		}
		return list;
	}

	public void validaEmail(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new IntegrityViolation("O email %s já está sendo usado por outro usuário".formatted(user.getEmail()));
        }
    }

}