package br.com.trier.projetospring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.NationalTeam;

@Repository
public interface NationalTeamRepository extends JpaRepository<NationalTeam, Integer>{
	List<NationalTeam> findByNameIgnoreCase(String name);
	List<NationalTeam> findByNameContainsIgnoreCase(String name);
	List<NationalTeam> findByNameStartsWithIgnoreCase(String name);
	List<NationalTeam> findByCountry(Country country);
	NationalTeam findByName(String name);
}
