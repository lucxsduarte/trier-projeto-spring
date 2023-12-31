package br.com.trier.projetospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projetospring.domain.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{
	
	List<Country> findByNameIgnoreCase(String name);
	List<Country> findByNameContainsIgnoreCase(String name);
	List<Country> findByNameStartsWithIgnoreCase(String name);
	Country findByName(String name);
}
