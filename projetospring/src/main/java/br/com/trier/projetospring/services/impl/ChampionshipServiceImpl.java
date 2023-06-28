package br.com.trier.projetospring.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projetospring.domain.Championship;
import br.com.trier.projetospring.repositories.ChampionshipRepository;
import br.com.trier.projetospring.services.ChampionshipService;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@Service
public class ChampionshipServiceImpl implements ChampionshipService{

	@Autowired
	ChampionshipRepository repository;
	
	@Override
	public Championship save(Championship country) {
		validateYear(country);
		return repository.save(country);
	}

	@Override
	public Championship update(Championship country) {
		findById(country.getId());
		validateYear(country);
		return repository.save(country);
	}

	@Override
	public void delete(Integer id) {
		Championship championship = findById(id);
		repository.delete(championship);
		
	}

	@Override
	public Championship findById(Integer id) {
		Optional<Championship> championship = repository.findById(id);
		return championship.orElseThrow(() -> new ObjectNotFound("Campeonato %s não encontrado".formatted(id)));
	}

	@Override
	public List<Championship> listAll() {
		List<Championship> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado!");
		}
		return list;
	}

	@Override
	public List<Championship> findByNameIgnoreCase(String name) {
		List<Championship> list = repository.findByNameIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado com o nome: %s!".formatted(name));
		}
		return list;
	}

	@Override
	public List<Championship> findByNameContainsIgnoreCase(String name) {
		List<Championship> list = repository.findByNameContainsIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado contem o nome: %s!".formatted(name));
		}
		return list;
	}

	@Override
	public List<Championship> findByNameStartsWithIgnoreCase(String name) {
		List<Championship> list = repository.findByNameStartsWithIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado começa com o nome: %s!".formatted(name));
		}
		return list;
	}

	@Override
	public List<Championship> findByYear(Integer year) {
		List<Championship> list = repository.findByYear(year);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado no ano de: %s!".formatted(year));
		}
		return list;
	}

	@Override
	public List<Championship> findByYearAndName(Integer year, String name) {
		List<Championship> list = repository.findByYearAndName(year, name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado no ano de: %s com o nome %s!".formatted(year, name));
		}
		return list;
	}

	@Override
	public List<Championship> findByYearBetween(Integer initialYear, Integer finalYear) {
		List<Championship> list = repository.findByYearBetween(initialYear, finalYear);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado entre os anos de: %s e %s!".formatted(initialYear, finalYear));
		}
		return list;
	}

	@Override
	public List<Championship> findByYearGreaterThanEqual(Integer year) {
		List<Championship> list = repository.findByYearGreaterThanEqual(year);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado após o ano de: %s!".formatted(year));
		}
		return list;
	}

	@Override
	public List<Championship> findByYearLessThanEqual(Integer year) {
		List<Championship> list = repository.findByYearLessThanEqual(year);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato cadastrado antes do ano de: %s!".formatted(year));
		}
		return list;
	}
	
	public void validateYear(Championship championship) {
		int currentYear = LocalDate.now().getYear();
		int maxYear = currentYear + 4;
		if (championship.getYear() < 2000 || championship.getYear() > maxYear) {
			throw new IntegrityViolation("Ano %s inválido para cadastro".formatted(championship.getYear()));
		}
	}

}
