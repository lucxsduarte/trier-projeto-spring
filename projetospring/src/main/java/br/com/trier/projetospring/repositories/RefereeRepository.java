package br.com.trier.projetospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer>{

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
