package br.com.trier.projetospring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.repositories.CountryRepository;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@Service
public class CountryServiceImpl implements CountryService{

	@Autowired
	CountryRepository repository;

	@Override
	public Country save(Country country) {
		findByName(country);
		return repository.save(country);
	}

	@Override
	public Country update(Country country) {
		findById(country.getId());
		findByName(country);
		return repository.save(country);
	}

	@Override
	public void delete(Integer id) {
		Country country = findById(id);
		repository.delete(country);
	}

	@Override
	public List<Country> listAll() {
		List<Country> list = repository.findAll();
		if ( list.size() == 0) {
			throw new ObjectNotFound("Nenhum país cadastrado");
		}
		return list;
	}

	@Override
	public Country findById(Integer id) {
		Optional<Country> country = repository.findById(id);
		return country.orElseThrow(() -> new ObjectNotFound("País %s não encontrado".formatted(id)));
	}

	@Override
	public List<Country> findByNameIgnoreCase(String name) {
		List<Country> list = repository.findByNameIgnoreCase(name);
		if ( list.size() == 0) {
			throw new ObjectNotFound("Nenhum país cadastrado se chama: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<Country> findByNameContains(String name) {
		List<Country> list = repository.findByNameIgnoreCase(name);
		if ( list.size() == 0) {
			throw new ObjectNotFound("Nenhum país cadastrado contem: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<Country> findByNameStartsWith(String name) {
		List<Country> list = repository.findByNameIgnoreCase(name);
		if ( list.size() == 0) {
			throw new ObjectNotFound("Nenhum país cadastrado começa com: %s".formatted(name));
		}
		return list;
	}
	
	public void findByName(Country country) {
		Country newCountry = repository.findByName(country.getName());
		if (newCountry != null && newCountry.getId() != country.getId()) {
			throw new ObjectNotFound("País já cadastrado: %s".formatted(country.getName()));
		}
			
	}
}
