package br.com.trier.projetospring.services;

import java.util.List;

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.NationalTeam;

public interface NationalTeamService {

	NationalTeam save(NationalTeam country);
	NationalTeam update(NationalTeam country);
	void delete (Integer id);
	NationalTeam findById(Integer id);
	List<NationalTeam> listAll();
	List<NationalTeam> findByNameIgnoreCase(String name);
	List<NationalTeam> findByNameContainsIgnoreCase(String name);
	List<NationalTeam> findByNameStartsWithIgnoreCase(String name);
	List<NationalTeam> findByCountry(Country country);
}
