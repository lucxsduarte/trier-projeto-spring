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

import br.com.trier.projetospring.domain.Match;
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
	
	@PostMapping
	public ResponseEntity<Match> insert(@RequestBody Match match){
		return ResponseEntity.ok(service.save(match));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Match> update(@PathVariable Integer id, @RequestBody Match match){
		match.setId(id);
		return ResponseEntity.ok(service.update(match));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Match> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Match>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/date/{id}")
	public ResponseEntity<List<Match>> findByDate(@PathVariable String date){
		return ResponseEntity.ok(service.findByDate(DateUtils.strToZonedDateTime(date)));
	}
	
	@GetMapping("/date/between/{initialDate}/{finalDate}")
	public ResponseEntity<List<Match>> findByDateBetween(@PathVariable String initialDate, @PathVariable String finalDate){
		return ResponseEntity.ok(service.findByDateBetween(DateUtils.strToZonedDateTime(initialDate), DateUtils.strToZonedDateTime(finalDate)));
	}
	
	@GetMapping("/country/{country_id}")
	public ResponseEntity<List<Match>> findByCountry(@PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByCountry(countryService.findById(country_id)));
	}
	
	@GetMapping("/championship/{championship_id}")
	public ResponseEntity<List<Match>> findByChampionship(@PathVariable Integer championship_id){
		return ResponseEntity.ok(service.findByChampionship(championshipService.findById(championship_id)));
	}
	
	@GetMapping("/championship/country/{championship_id}/{country_id}")
	public ResponseEntity<List<Match>> findByChampionshipAndCountry(@PathVariable Integer championship_id, @PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByChampionshipAndCountry(championshipService.findById(championship_id), countryService.findById(country_id)));
	}
	
	@GetMapping("/date/country/{date}/{country_id}")
	public ResponseEntity<List<Match>> findByDateAndCountry(@PathVariable String date, @PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByDateAndCountry(DateUtils.strToZonedDateTime(date), countryService.findById(country_id)));
	}
	
	@GetMapping("/date/championship/{date}/{championship_id}")
	public ResponseEntity<List<Match>> findByDateAndChampionship(@PathVariable String date, @PathVariable Integer championship_id){
		return ResponseEntity.ok(service.findByDateAndChampionship(DateUtils.strToZonedDateTime(date), championshipService.findById(championship_id)));
	}
}
