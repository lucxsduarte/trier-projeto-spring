package br.com.trier.projetospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projetospring.domain.MatchStatus;
import br.com.trier.projetospring.domain.dto.MatchStatusDTO;
import br.com.trier.projetospring.services.MatchService;
import br.com.trier.projetospring.services.MatchStatusService;
import br.com.trier.projetospring.services.NationalTeamService;
import br.com.trier.projetospring.services.RefereeService;

@RestController
@RequestMapping(value = "/matchstatus")
public class MatchStatusResource {
	
	@Autowired
	private MatchStatusService service;
	@Autowired
	private MatchService matchService;
	@Autowired
	private RefereeService refereeService;
	@Autowired
	private NationalTeamService nationalTeamService;
	
	@PostMapping
	public ResponseEntity<MatchStatusDTO> insert(@RequestBody MatchStatusDTO matchStatusDTO){
		return ResponseEntity.ok(service.save(new MatchStatus(
				matchStatusDTO,
				matchService.findById(matchStatusDTO.getMatch_id()),
				refereeService.findById(matchStatusDTO.getReferee_id()),
				nationalTeamService.findById(matchStatusDTO.getHomeTeam_id()),
				nationalTeamService.findById(matchStatusDTO.getAwayTeam_id()),
				nationalTeamService.findById(matchStatusDTO.getWinner_id())))
				.toDTO());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MatchStatusDTO> update(@PathVariable Integer id, @RequestBody MatchStatusDTO matchStatusDTO){
		MatchStatus matchStatus = new MatchStatus(
				matchStatusDTO,
				matchService.findById(matchStatusDTO.getMatch_id()),
				refereeService.findById(matchStatusDTO.getReferee_id()),
				nationalTeamService.findById(matchStatusDTO.getHomeTeam_id()),
				nationalTeamService.findById(matchStatusDTO.getAwayTeam_id()),
				nationalTeamService.findById(matchStatusDTO.getWinner_id()));
		matchStatus.setId(id);
		return ResponseEntity.ok(service.update(matchStatus).toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MatchStatusDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<MatchStatusDTO>> listAll(){
		return ResponseEntity.ok(service.listAll()
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/match/{match_id}")
	public ResponseEntity<List<MatchStatusDTO>> findByMatch(@PathVariable Integer match_id){
		return ResponseEntity.ok(service.findByMatch(matchService.findById(match_id))
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/referee/{referee_id}")
	public ResponseEntity<List<MatchStatusDTO>> findByReferee(@PathVariable Integer referee_id){
		return ResponseEntity.ok(service.findByReferee(refereeService.findById(referee_id))
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/match/referee/{match_id}/{referee_id}")
	public ResponseEntity<List<MatchStatusDTO>> findByMatchAndReferee(@PathVariable Integer match_id, @PathVariable Integer referee_id){
		return ResponseEntity.ok(service.findByMatchAndReferee(matchService.findById(match_id), refereeService.findById(referee_id))
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/homeTeam/{national_team_id}")
	public ResponseEntity<List<MatchStatusDTO>> findByHomeTeam(@PathVariable Integer national_team_id){
		return ResponseEntity.ok(service.findByHomeTeam(nationalTeamService.findById(national_team_id))
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/awayTeam/{national_team_id}")
	public ResponseEntity<List<MatchStatusDTO>> findByAwayTeam(@PathVariable Integer national_team_id){
		return ResponseEntity.ok(service.findByAwayTeam(nationalTeamService.findById(national_team_id))
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/winner/{national_team_id}")
	public ResponseEntity<List<MatchStatusDTO>> findByWinner(@PathVariable Integer national_team_id){
		return ResponseEntity.ok(service.findByWinner(nationalTeamService.findById(national_team_id))
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/homeScore/{homeScore}")
	public ResponseEntity<List<MatchStatusDTO>> findByHomeScore(@PathVariable Integer homeScore){
		return ResponseEntity.ok(service.findByHomeScore(homeScore)
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/awayScore/{awayScore}")
	public ResponseEntity<List<MatchStatusDTO>> findByAwayScore(@PathVariable Integer awayScore){
		return ResponseEntity.ok(service.findByAwayScore(awayScore)
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/homeScore/awayScore/{homeScore}/{awayScore}")
	public ResponseEntity<List<MatchStatusDTO>> findByHomeScoreAndAwayScore(@PathVariable Integer homeScore, @PathVariable Integer awayScore){
		return ResponseEntity.ok(service.findByHomeScoreAndAwayScore(homeScore, awayScore)
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/homeTeam/homeScore/{national_team_id}/{homeScore}")
	public ResponseEntity<List<MatchStatusDTO>> findByHomeTeamAndHomeScore(@PathVariable Integer national_team_id, @PathVariable Integer homeScore){
		return ResponseEntity.ok(service.findByHomeTeamAndHomeScore(nationalTeamService.findById(national_team_id), homeScore)
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
	
	@GetMapping("/homeTeam/homeScore/{national_team_id}/{awayScore}")
	public ResponseEntity<List<MatchStatusDTO>> findByAwayTeamAndAwayScore(@PathVariable Integer national_team_id, @PathVariable Integer awayScore){
		return ResponseEntity.ok(service.findByAwayTeamAndAwayScore(nationalTeamService.findById(national_team_id), awayScore)
				.stream().map(matchStatus -> matchStatus.toDTO()).toList());
	}
}
