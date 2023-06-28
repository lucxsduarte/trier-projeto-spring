package br.com.trier.projetospring.services;

import java.util.List;

import br.com.trier.projetospring.domain.Country;

public interface CountryService {
	
	Country save(Country country);
	Country update(Country country);
	void delete (Integer id);
	Country findById(Integer id);
	List<Country> listAll();
	List<Country> findByNameIgnoreCase(String name);
	List<Country> findByNameContainsIgnoreCase(String name);
	List<Country> findByNameStartsWithIgnoreCase(String name);
}
