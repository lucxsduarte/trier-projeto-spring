package br.com.trier.projetospring.services;

import java.util.List;

import br.com.trier.projetospring.domain.Championship;

public interface ChampionshipService {

	Championship save(Championship country);
	Championship update(Championship country);
	void delete (Integer id);
	Championship findById(Integer id);
	List<Championship> listAll();
	List<Championship> findByNameIgnoreCase(String name);
	List<Championship> findByNameContains(String name);
	List<Championship> findByNameStartsWith(String name);
	List<Championship> findByYear(Integer year);
	List<Championship> findByYearAndName(Integer year, String name);
	List<Championship> findByYearBetween(Integer initialYear, Integer finalYear);
	List<Championship> findByYearGreaterThanEqual(Integer year);
	List<Championship> findByYearLessThanEqual(Integer year);
}
