package br.com.trier.projetospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projetospring.domain.Championship;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Integer>{

	List<Championship> findByNameIgnoreCase(String name);
	List<Championship> findByNameContainsIgnoreCase(String name);
	List<Championship> findByNameStartsWithIgnoreCase(String name);
	List<Championship> findByYear(Integer year);
	List<Championship> findByYearAndName(Integer year, String name);
	List<Championship> findByYearBetween(Integer initialYear, Integer finalYear);
	List<Championship> findByYearGreaterThanEqual(Integer year);
	List<Championship> findByYearLessThanEqual(Integer year);
}
