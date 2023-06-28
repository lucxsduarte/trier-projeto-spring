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

import br.com.trier.projetospring.domain.Referee;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.RefereeService;

@RestController
@RequestMapping (value = "/referee")
public class RefereeResource {
	
	@Autowired
	private RefereeService service;
	@Autowired
	private CountryService countryService;

	@PostMapping
	public ResponseEntity<Referee> insert(@RequestBody Referee referee){
		return ResponseEntity.ok(service.save(referee));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Referee> update(@PathVariable Integer id, @RequestBody Referee referee){
		referee.setId(id);
		return ResponseEntity.ok(service.update(referee));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Referee> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Referee>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Referee>> findByNameIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@GetMapping("/name/contains/{name}")
	public ResponseEntity<List<Referee>> findByNameContainsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
	
	@GetMapping("/name/starts/{name}")
	public ResponseEntity<List<Referee>> findByNameStartsWithIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
	}
	
	@GetMapping("/country/{id}")
	public ResponseEntity<List<Referee>> findByCountry(@PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByCountry(countryService.findById(country_id)));
	}
	
	@GetMapping("/age/{age}")
	public ResponseEntity<List<Referee>> findByAge(@PathVariable Integer age){
		return ResponseEntity.ok(service.findByAge(age));
	}
	
	@GetMapping("/age/name/{age}/{name}")
	public ResponseEntity<List<Referee>> findByAgeAndName(@PathVariable Integer age, @PathVariable String name){
		return ResponseEntity.ok(service.findByAgeAndName(age, name));
	}
	
	@GetMapping("/age/country/{age}/{country_id}")
	public ResponseEntity<List<Referee>> findByAgeAndCountry(@PathVariable Integer age, @PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByAgeAndCountry(age, countryService.findById(country_id)));
	}
	
	@GetMapping("/age/between/{initialAge}/{finalAge}")
	public ResponseEntity<List<Referee>> findByAgeBetween(@PathVariable Integer initialAge, @PathVariable Integer finalAge){
		return ResponseEntity.ok(service.findByAgeBetween(initialAge, finalAge));
	}
	
	@GetMapping("/age/greater/{age}")
	public ResponseEntity<List<Referee>> findByAgeGreaterThanEqual(@PathVariable Integer age){
		return ResponseEntity.ok(service.findByAgeGreaterThanEqual(age));
	}
	
	@GetMapping("/age/less/{age}")
	public ResponseEntity<List<Referee>> findByAgeLessThanEqual(@PathVariable Integer age){
		return ResponseEntity.ok(service.findByAgeLessThanEqual(age));
	}
}
