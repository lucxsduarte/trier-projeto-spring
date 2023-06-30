package br.com.trier.projetospring.resources;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
import br.com.trier.projetospring.domain.MatchStatus;
import br.com.trier.projetospring.domain.NationalTeam;
import br.com.trier.projetospring.domain.Referee;
import br.com.trier.projetospring.domain.dto.AllGoalsByChampionshipDTO;
import br.com.trier.projetospring.domain.dto.AllRefereeSummaryDTO;
import br.com.trier.projetospring.domain.dto.AverageGoalMatchDTO;
import br.com.trier.projetospring.domain.dto.GoalsCounterDTO;
import br.com.trier.projetospring.domain.dto.MatchByCountryAndYearDTO;
import br.com.trier.projetospring.domain.dto.MatchDTO;
import br.com.trier.projetospring.domain.dto.MatchStatusDTO;
import br.com.trier.projetospring.domain.dto.RefereeSummaryDTO;
import br.com.trier.projetospring.domain.dto.AverageWinMatchDTO;
import br.com.trier.projetospring.domain.dto.ChampionshipByNationalTeamsDTO;
import br.com.trier.projetospring.services.ChampionshipService;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.MatchService;
import br.com.trier.projetospring.services.MatchStatusService;
import br.com.trier.projetospring.services.NationalTeamService;
import br.com.trier.projetospring.services.RefereeService;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping(value = "/report")
public class ReportResource {

	@Autowired
	private CountryService countryService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private ChampionshipService championshipService;
	@Autowired
	private MatchStatusService matchStatusService;
	@Autowired
	private NationalTeamService nationalTeamService;
	@Autowired
	private RefereeService refereeService;

	@Secured({ "ROLE_USER" })
	@GetMapping("/match/country/year/{country_id}/{year}")
	public ResponseEntity<MatchByCountryAndYearDTO> findMatchByCountryYear(@PathVariable Integer country_id,
			@PathVariable Integer year) {
		Country country = countryService.findById(country_id);
		List<MatchDTO> matchesDTO = matchService.findByCountry(country).stream()
				.filter(match -> match.getDate().getYear() == year).map(match -> match.toDTO()).toList();
		if (matchesDTO.isEmpty()) {
			throw new ObjectNotFound("Não foi encontrado patidas para o ano: %s".formatted(year));
		}
		return ResponseEntity.ok(new MatchByCountryAndYearDTO(year, country.getName(), matchesDTO));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/average/win/match/{nationalTeam_id}")
	public ResponseEntity<AverageWinMatchDTO> averageWinMatch(@PathVariable Integer nationalTeam_id) {
		NationalTeam nationalTeam = nationalTeamService.findById(nationalTeam_id);
		Integer winCounter = 0;
		Integer lossCounter = 0;
		Integer totalMatches = 0;
		Integer homeMatches = 0;
		Integer awayMatches = 0;
		Double average = 0.0;
		try {
			List<MatchStatus> list = matchStatusService.findByWinner(nationalTeam);
			winCounter = list.size();
		} catch (ObjectNotFound e) {
			winCounter = 0;
		}

		try {
			List<MatchStatusDTO> homeList = matchStatusService.findByHomeTeam(nationalTeam).stream()
					.map(MatchStatus::toDTO).toList();
			homeMatches = homeList.size();
		} catch (ObjectNotFound e) {
			homeMatches = 0;
		}

		try {
			List<MatchStatusDTO> awayList = matchStatusService.findByAwayTeam(nationalTeam).stream()
					.map(MatchStatus::toDTO).toList();
			awayMatches = awayList.size();
		} catch (ObjectNotFound e) {
			awayMatches = 0;
		}

		totalMatches = homeMatches + awayMatches;
		lossCounter = totalMatches - winCounter;
		average = ((double) (winCounter / (double) totalMatches)) * 100;
		return ResponseEntity.ok(new AverageWinMatchDTO(nationalTeam.getName(), totalMatches, winCounter, lossCounter,
				new DecimalFormat("0.0").format(average) + "%"));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/goal/counter/{nationalTeam_id}")
	public ResponseEntity<GoalsCounterDTO> goalsCounter(@PathVariable Integer nationalTeam_id) {
		Integer totalGoals = 0;
		Integer homeGoals = 0;
		Integer awayGoals = 0;
		NationalTeam nationalTeam = nationalTeamService.findById(nationalTeam_id);
		try {
			List<MatchStatusDTO> homeList = matchStatusService.findByHomeTeam(nationalTeam).stream()
					.filter(matchStatus -> matchStatus.getHomeScore() > 0).map(MatchStatus::toDTO).toList();
			homeGoals = homeList.stream().mapToInt(MatchStatusDTO::getHomeScore).sum();
		} catch (ObjectNotFound e) {
			homeGoals = 0;
		}

		try {
			List<MatchStatusDTO> awayList = matchStatusService.findByAwayTeam(nationalTeam).stream()
					.filter(matchStatus -> matchStatus.getAwayScore() > 0).map(MatchStatus::toDTO).toList();
			awayGoals = awayList.stream().mapToInt(MatchStatusDTO::getAwayScore).sum();
		} catch (ObjectNotFound e) {
			awayGoals = 0;
		}

		totalGoals = homeGoals + awayGoals;
		return ResponseEntity.ok(new GoalsCounterDTO(nationalTeam.getName(), totalGoals, homeGoals, awayGoals));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/average/goal/match/{nationalTeam_id}")
	public ResponseEntity<AverageGoalMatchDTO> averageGoalMatch(@PathVariable Integer nationalTeam_id) {
		Integer totalMatches = 0;
		Integer homeMatches = 0;
		Integer awayMatches = 0;
		Integer totalGoals = 0;
		Integer homeGoals = 0;
		Integer awayGoals = 0;
		Double average = 0.0;
		NationalTeam nationalTeam = nationalTeamService.findById(nationalTeam_id);

		try {
			List<MatchStatusDTO> homeList = matchStatusService.findByHomeTeam(nationalTeam).stream()
					.map(MatchStatus::toDTO).toList();
			homeMatches = homeList.size();
		} catch (ObjectNotFound e) {
			homeMatches = 0;
		}

		try {
			List<MatchStatusDTO> awayList = matchStatusService.findByAwayTeam(nationalTeam).stream()
					.map(MatchStatus::toDTO).toList();
			awayMatches = awayList.size();
		} catch (ObjectNotFound e) {
			awayMatches = 0;
		}

		totalMatches = homeMatches + awayMatches;

		try {
			List<MatchStatusDTO> homeList = matchStatusService.findByHomeTeam(nationalTeam).stream()
					.filter(matchStatus -> matchStatus.getHomeScore() > 0).map(MatchStatus::toDTO).toList();
			homeGoals = homeList.stream().mapToInt(MatchStatusDTO::getHomeScore).sum();
		} catch (ObjectNotFound e) {
			homeGoals = 0;
		}

		try {
			List<MatchStatusDTO> awayList = matchStatusService.findByAwayTeam(nationalTeam).stream()
					.filter(matchStatus -> matchStatus.getAwayScore() > 0).map(MatchStatus::toDTO).toList();
			awayGoals = awayList.stream().mapToInt(MatchStatusDTO::getAwayScore).sum();
		} catch (ObjectNotFound e) {
			awayGoals = 0;
		}

		totalGoals = homeGoals + awayGoals;
		average = (double) (totalGoals / (double) totalMatches);
		return ResponseEntity.ok(new AverageGoalMatchDTO(nationalTeam.getName(), totalMatches, totalGoals,
				new DecimalFormat("0.0").format(average)));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/referee/summary/{referee_id}")
	public ResponseEntity<RefereeSummaryDTO> refereeSummary(@PathVariable Integer referee_id) {
		Integer refereeMatches = 0;
		Referee referee = refereeService.findById(referee_id);
		try {
			List<MatchStatusDTO> refereeList = matchStatusService.findByReferee(referee).stream()
					.map(MatchStatus::toDTO).toList();
			refereeMatches = refereeList.size();
		} catch (ObjectNotFound e) {
			refereeMatches = 0;
		}

		return ResponseEntity.ok(new RefereeSummaryDTO(referee.getName(), referee.getAge(),
				referee.getCountry().getName(), refereeMatches));
	}

	@Secured({ "ROLE_USER" })
	@GetMapping("/referee/summary/all")
	public ResponseEntity<AllRefereeSummaryDTO> allRefereeSummary() {
		List<Referee> allReferees = refereeService.listAll();

		List<RefereeSummaryDTO> refereeSumaries = allReferees.stream().map(referee -> {
			int totalMatches = 0;
			try {
				List<MatchStatus> matchesByReferee = matchStatusService.findByReferee(referee);
				totalMatches = matchesByReferee.size();
			} catch (ObjectNotFound e) {
				totalMatches = 0;
			}
			return new RefereeSummaryDTO(referee.getName(), referee.getAge(), referee.getCountry().getName(),
					totalMatches);
		}).collect(Collectors.toList());

		return ResponseEntity.ok(new AllRefereeSummaryDTO(refereeSumaries));
	}
	
	@Secured({ "ROLE_USER" })
	@GetMapping("/goal/championship/{championship_id}")
	public ResponseEntity<AllGoalsByChampionshipDTO> allGoalsByChampionship(@PathVariable Integer championship_id) {
		Championship championship = championshipService.findById(championship_id);
		
		List<Match> matches = matchService.findByChampionship(championship);
	    int totalMatches = matches.size();

	    int totalGoals = matches.stream()
	            .flatMap(match -> matchStatusService.findByMatch(match).stream())
	            .mapToInt(matchStatus -> matchStatus.getHomeScore() + matchStatus.getAwayScore())
	            .sum();

	    return ResponseEntity.ok(new AllGoalsByChampionshipDTO(championship.getName(), totalMatches, totalGoals));
	}
	
	@Secured({ "ROLE_USER" })
	@GetMapping("/championship/nationalteam/{nationalTeam_id}")
	public ResponseEntity<ChampionshipByNationalTeamsDTO> championshipsByNationalTeams(@PathVariable Integer nationalTeam_id) {
		NationalTeam nationalTeam = nationalTeamService.findById(nationalTeam_id);
		List<MatchStatus> matchStatusList = new ArrayList<>();
		
		try {
			List<MatchStatus> homeList = matchStatusService.findByHomeTeam(nationalTeam);
		    matchStatusList.addAll(homeList);
		} catch (ObjectNotFound e) {
			
		}
		
		try {
			List<MatchStatus> awayList = matchStatusService.findByAwayTeam(nationalTeam);
		    matchStatusList.addAll(awayList);
		} catch (ObjectNotFound e) {
			
		}
		
	    List<Championship> championshipsList = matchStatusList.stream()
	        .map(matchStatus -> matchStatus.getMatch().getChampionship())
	        .distinct()
	        .collect(Collectors.toList());
	    
	    return ResponseEntity.ok(new ChampionshipByNationalTeamsDTO(nationalTeam.getName(), championshipsList));
	}
}
