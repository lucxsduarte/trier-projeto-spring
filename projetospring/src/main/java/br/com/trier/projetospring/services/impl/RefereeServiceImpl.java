package br.com.trier.projetospring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.Referee;
import br.com.trier.projetospring.repositories.RefereeRepository;
import br.com.trier.projetospring.services.RefereeService;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@Service
public class RefereeServiceImpl implements RefereeService {

	@Autowired
	RefereeRepository repository;

	@Override
	public Referee save(Referee referee) {
		validateAge(referee);
		return repository.save(referee);
	}

	@Override
	public Referee update(Referee referee) {
		findById(referee.getId());
		validateAge(referee);
		return repository.save(referee);
	}

	@Override
	public void delete(Integer id) {
		Referee referee = findById(id);
		repository.delete(referee);

	}

	@Override
	public Referee findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Árbitro %s não encontrado".formatted(id)));
	}

	@Override
	public List<Referee> listAll() {
		List<Referee> list = repository.findAll();
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado");
		}
		return list;
	}

	@Override
	public List<Referee> findByNameIgnoreCase(String name) {
		List<Referee> list = repository.findByNameIgnoreCase(name);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com esse nome: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<Referee> findByNameContainsIgnoreCase(String name) {
		List<Referee> list = repository.findByNameContainsIgnoreCase(name);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado contem %s no nome".formatted(name));
		}
		return list;
	}

	@Override
	public List<Referee> findByNameStartsWithIgnoreCase(String name) {
		List<Referee> list = repository.findByNameStartsWithIgnoreCase(name);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado começa com %s no nome".formatted(name));
		}
		return list;
	}

	@Override
	public List<Referee> findByCountry(Country country) {
		List<Referee> list = repository.findByCountry(country);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado do país: %s".formatted(country.getId()));
		}
		return list;
	}

	@Override
	public List<Referee> findByAge(Integer age) {
		List<Referee> list = repository.findByAge(age);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com a idade: %s".formatted(age));
		}
		return list;
	}

	@Override
	public List<Referee> findByAgeAndName(Integer age, String name) {
		List<Referee> list = repository.findByAgeAndName(age, name);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com a idade %s e o nome %s".formatted(age, name));
		}
		return list;
	}

	@Override
	public List<Referee> findByAgeAndCountry(Integer age, Country country) {
		List<Referee> list = repository.findByAgeAndCountry(age, country);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com a idade %s e o país %s".formatted(age, country.getId()));
		}
		return list;
	}

	@Override
	public List<Referee> findByAgeBetween(Integer initialAge, Integer finalAge) {
		List<Referee> list = repository.findByAgeBetween(initialAge, finalAge);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com a idade entre %S e %s".formatted(initialAge, finalAge));
		}
		return list;
	}

	@Override
	public List<Referee> findByAgeGreaterThanEqual(Integer age) {
		List<Referee> list = repository.findByAgeGreaterThanEqual(age);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com a idade maior que %s".formatted(age));
		}
		return list;
	}

	@Override
	public List<Referee> findByAgeLessThanEqual(Integer age) {
		List<Referee> list = repository.findByAgeLessThanEqual(age);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com a idade menor que %s".formatted(age));
		}
		return list;
	}

	@Override
	public List<Referee> findByNameAndCountry(String name, Country country) {
		List<Referee> list = repository.findByNameAndCountry(name, country);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum árbitro cadastrado com o nome %s no país %s".formatted(name, country.getId()));
		}
		return list;
	}
	
	private void validateAge(Referee referee) {
		if (referee.getAge() < 35 || referee.getAge() > 50) {
			throw new IntegrityViolation("O árbitro não pode ter menos que 35 anos ou mais que 50 anos");
		}
	}

}
