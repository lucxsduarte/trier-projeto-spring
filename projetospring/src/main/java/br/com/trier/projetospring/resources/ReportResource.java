package br.com.trier.projetospring.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projetospring.domain.Championship;
import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.Match;
import br.com.trier.projetospring.domain.dto.MatchByCountryAndYearDTO;
import br.com.trier.projetospring.domain.dto.MatchDTO;
import br.com.trier.projetospring.services.ChampionshipService;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.MatchService;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping (value = "/report")
public class ReportResource {

	@Autowired
	private CountryService countryService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private ChampionshipService championshipService;
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping ("/match/country/year/{country_id}/{year}")
	public ResponseEntity<MatchByCountryAndYearDTO>  findMatchbYCountryYear(@PathVariable Integer country_id, @PathVariable Integer ano){
		Country country = countryService.findById(country_id);
		
		
		
		return null;
	}
	
}
