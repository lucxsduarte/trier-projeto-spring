package br.com.trier.projetospring.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projetospring.domain.Championship;
import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer>{

	List<Match> findByDate(ZonedDateTime date);
	List<Match> findByDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate);
	List<Match> findByCountry(Country country);
	List<Match> findByChampionship(Championship championship);
	List<Match> findByChampionshipAndCountry(Championship championship, Country country);
	List<Match> findByDateAndCountry(ZonedDateTime date, Country country);
	List<Match> findByDateAndChampionship(ZonedDateTime date, Championship championship);
}
