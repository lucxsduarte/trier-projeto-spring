package br.com.trier.projetospring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.projetospring.services.ChampionshipService;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.MatchService;
import br.com.trier.projetospring.services.MatchStatusService;
import br.com.trier.projetospring.services.NationalTeamService;
import br.com.trier.projetospring.services.RefereeService;
import br.com.trier.projetospring.services.UserService;
import br.com.trier.projetospring.services.impl.ChampionshipServiceImpl;
import br.com.trier.projetospring.services.impl.CountryServiceImpl;
import br.com.trier.projetospring.services.impl.MatchServiceImpl;
import br.com.trier.projetospring.services.impl.MatchStatusServiceImpl;
import br.com.trier.projetospring.services.impl.NationalTeamServiceImpl;
import br.com.trier.projetospring.services.impl.RefereeServiceImpl;
import br.com.trier.projetospring.services.impl.UserServiceImpl;

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
	
	@Bean
	public RefereeService refereeService() {
		return new RefereeServiceImpl();
	}
	
	@Bean
	public MatchStatusService matchStatusService() {
		return new MatchStatusServiceImpl();
	}
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}
}
