package br.com.trier.projetospring.services;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.trier.projetospring.domain.Championship;
import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.Match;

public interface MatchService {
	
	Match save(Match match);
	Match update(Match match);
	void delete (Integer id);
	Match findById(Integer id);
	List<Match> listAll();
	List<Match> findByDate(ZonedDateTime date);
	List<Match> findByDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate);
	List<Match> findByCountry(Country country);
	List<Match> findByChampionship(Championship championship);
	List<Match> findByChampionshipAndCountry(Championship championship, Country country);
	List<Match> findByDateAndCountry(ZonedDateTime date, Country country);
	List<Match> findByDateAndChampionship(ZonedDateTime date, Championship championship);
}
