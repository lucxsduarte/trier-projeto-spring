package br.com.trier.projetospring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.projetospring.services.ChampionshipService;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.MatchService;
import br.com.trier.projetospring.services.NationalTeamService;
import br.com.trier.projetospring.services.impl.ChampionshipServiceImpl;
import br.com.trier.projetospring.services.impl.CountryServiceImpl;
import br.com.trier.projetospring.services.impl.MatchServiceImpl;
import br.com.trier.projetospring.services.impl.NationalTeamServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {

	@Bean
	public ChampionshipService championshipService() {
		return new ChampionshipServiceImpl();
	}
	
	@Bean
	public CountryService countryService() {
		return new CountryServiceImpl();
	}
	
	@Bean
	public NationalTeamService nationalTeamService() {
		return new NationalTeamServiceImpl();
	}
	
	@Bean
	public MatchService matchService() {
		return new MatchServiceImpl();
	}
}
