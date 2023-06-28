package br.com.trier.projetospring.services;

import java.util.List;

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.Referee;

public interface RefereeService {

	Referee save(Referee referee);
	Referee update(Referee referee);
	void delete (Integer id);
	Referee findById(Integer id);
	List<Referee> listAll();
	List<Referee> findByNameIgnoreCase(String name);
	List<Referee> findByNameContainsIgnoreCase(String name);
	List<Referee> findByNameStartsWithIgnoreCase(String name);
	List<Referee> findByCountry(Country country);
	List<Referee> findByAge(Integer age);
	List<Referee> findByAgeAndName(Integer age, String name);
	List<Referee> findByAgeAndCountry(Integer age, Country country);
	List<Referee> findByNameAndCountry(String name, Country country);
	List<Referee> findByAgeBetween(Integer initialAge, Integer finalAge);
	List<Referee> findByAgeGreaterThanEqual(Integer age);
	List<Referee> findByAgeLessThanEqual(Integer age);
}
