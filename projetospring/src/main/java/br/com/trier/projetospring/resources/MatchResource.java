package br.com.trier.projetospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projetospring.domain.Match;
import br.com.trier.projetospring.domain.dto.MatchDTO;
import br.com.trier.projetospring.services.ChampionshipService;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.MatchService;
import br.com.trier.projetospring.utils.DateUtils;

@RestController
@RequestMapping(value = "/match")
public class MatchResource {

	@Autowired
	private MatchService service;
	@Autowired
	private CountryService countryService;
	@Autowired
	private ChampionshipService championshipService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<MatchDTO> insert(@RequestBody MatchDTO matchDTO){
		return ResponseEntity.ok(service.save(new Match(
				matchDTO,
				countryService.findById(matchDTO.getCountry_id()),
				championshipService.findById(matchDTO.getChampionship_id())))
				.toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<MatchDTO> update(@PathVariable Integer id, @RequestBody MatchDTO matchDTO){
		Match match = new Match(
				matchDTO,
				countryService.findById(matchDTO.getCountry_id()),
				championshipService.findById(matchDTO.getChampionship_id()));
		match.setId(id);
		return ResponseEntity.ok(service.update(match).toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<MatchDTO> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<MatchDTO>> listAll(){
		return ResponseEntity.ok(service.listAll()
				.stream().map(match -> match.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/date/{id}")
	public ResponseEntity<List<MatchDTO>> findByDate(@PathVariable String date){
		return ResponseEntity.ok(service.findByDate(DateUtils.strToZonedDateTime(date))
				.stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/date/between/{initialDate}/{finalDate}")
	public ResponseEntity<List<MatchDTO>> findByDateBetween(@PathVariable String initialDate, @PathVariable String finalDate){
		return ResponseEntity.ok(service.findByDateBetween(DateUtils.strToZonedDateTime(initialDate), DateUtils.strToZonedDateTime(finalDate))
				.stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/country/{country_id}")
	public ResponseEntity<List<MatchDTO>> findByCountry(@PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByCountry(countryService.findById(country_id))
				.stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/championship/{championship_id}")
	public ResponseEntity<List<MatchDTO>> findByChampionship(@PathVariable Integer championship_id){
		return ResponseEntity.ok(service.findByChampionship(championshipService.findById(championship_id))
				.stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/championship/country/{championship_id}/{country_id}")
	public ResponseEntity<List<MatchDTO>> findByChampionshipAndCountry(@PathVariable Integer championship_id, @PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByChampionshipAndCountry(championshipService.findById(championship_id), countryService.findById(country_id))
				.stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/date/country/{date}/{country_id}")
	public ResponseEntity<List<MatchDTO>> findByDateAndCountry(@PathVariable String date, @PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByDateAndCountry(DateUtils.strToZonedDateTime(date), countryService.findById(country_id))
				.stream().map(corrida -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/date/championship/{date}/{championship_id}")
	public ResponseEntity<List<MatchDTO>> findByDateAndChampionship(@PathVariable String date, @PathVariable Integer championship_id){
		return ResponseEntity.ok(service.findByDateAndChampionship(DateUtils.strToZonedDateTime(date), championshipService.findById(championship_id))
				.stream().map(corrida -> corrida.toDTO()).toList());
	}
}
